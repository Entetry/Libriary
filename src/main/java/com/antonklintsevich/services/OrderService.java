package com.antonklintsevich.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.common.GiftDto;
import com.antonklintsevich.common.OrderDto;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.NotEnoughMoneyException;
import com.antonklintsevich.exception.OrderNotFoundException;
import com.antonklintsevich.exception.UserAlreadyHasThisBookException;
import com.antonklintsevich.exception.UserNotFoundException;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.OrderRepository;
import com.antonklintsevich.persistense.UserRepository;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    UserServiceIml userServiceIml;

    public void delete(Long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        try {
            orderRepository.deleteBooksFromOrder(orderId, entityManager);
            orderRepository.deleteById(orderId, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(Long orderId, OrderDto orderDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        order.setPrice(orderDto.getPrice());
        order.setBooks(DtoConverter.constructBookSet(orderDto.getBookDtos()));
        order.setPrice(orderDto.getPrice());
        order.setOrderdate(orderDto.getOrderdate());
        try {
            orderRepository.update(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    final public void create(OrderDto orderDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = new Order();
        BigDecimal totalPrice = new BigDecimal(0);
        order.setUser(userServiceIml.getCurrentUser());
        for (BookDto bookDto : orderDto.getBookDtos()) {
            Book book = bookRepository.findOne(bookDto.getId(), entityManager).orElseThrow(BookNotFoundException::new);
            if (userRepository.getAllUserBooks(order.getUser().getId(), entityManager).contains(book)) {
                throw new UserAlreadyHasThisBookException();
            }
            if ("Invalid".equals(userRepository.findByUsername(userServiceIml.getCurrentUserUsername(), entityManager)
                    .getStatus())) {
                if (!(book.getPrice().compareTo(new BigDecimal(10.00)) == -1)) {
                    totalPrice = totalPrice.add(book.getPrice());
                }
            } else {
                totalPrice = totalPrice.add(book.getPrice());
            }
            order.addBook(book);
        }
        order.setOrderdate(orderDto.getOrderdate());
        order.setPrice(totalPrice);
        order.setOrderStatus("inprogress");
        if (order.getUser().getWallet().getBalance().compareTo(totalPrice) == -1)
            throw new NotEnoughMoneyException();

        try {
            orderRepository.create(order, entityManager);
            confirmOrder(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private void confirmOrder(Order order, EntityManager entityManager) {
        if ("inprogress".equals(order.getOrderStatus())) {
            User user = userRepository.findOne(order.getUser().getId(), entityManager)
                    .orElseThrow(UserNotFoundException::new);
            for (Book book : order.getBooks()) {
                user.addBook(book);
            }
            user.getWallet().setBalance(order.getUser().getWallet().getBalance().subtract(order.getPrice()));
            order.setOrderStatus("completed");
            userRepository.update(user, entityManager);
            orderRepository.update(order, entityManager);
        }
    }

    public void sendBookAsaGift(GiftDto giftDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = new Order();
        BigDecimal totalPrice = new BigDecimal(0);
        order.setUser(userRepository.findByUserUsername(userServiceIml.getCurrentUserUsername(), entityManager)
                .orElseThrow(UserNotFoundException::new));
        User recipient = userRepository.findByUsername(giftDto.getRecipientName(), entityManager);
        for (BookDto bookDto : giftDto.getBookDtos()) {
            Book book = bookRepository.findOne(bookDto.getId(), entityManager).orElseThrow(BookNotFoundException::new);
            if (recipient.getBooks().contains(book))
                throw new UserAlreadyHasThisBookException();
            recipient.addBook(book);
            totalPrice = totalPrice.add(book.getPrice());
        }

        order.setBooks(DtoConverter.constructBookSet(giftDto.getBookDtos()));
        order.setOrderdate(new Date());
        order.setPrice(totalPrice);
        order.setOrderStatus("gift");
        if (order.getUser().getWallet().getBalance().compareTo(totalPrice) == -1)
            throw new NotEnoughMoneyException();

        try {
            orderRepository.create(order, entityManager);
            if ("gift".equals(order.getOrderStatus())) {
                recipient.getWallet().setBalance(order.getUser().getWallet().getBalance().subtract(totalPrice));
                userRepository.update(order.getUser(), entityManager);
                userRepository.update(recipient, entityManager);
                order.setOrderStatus("completed");
                orderRepository.update(order, entityManager);
            }
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<OrderDto> getAllOrdersAsOrderDTO() {
        return getAllOrders().stream().map(order -> DtoConverter.constructOrderDTO(order)).collect(Collectors.toList());
    }

    private List<Order> getAllOrders() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return orderRepository.findAll(entityManager);
        } finally {
            entityManager.close();
        }
    }

    public OrderDto getOrderById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrderDto orderDto = DtoConverter
                .constructOrderDTO(orderRepository.findOne(id, entityManager).orElseThrow(OrderNotFoundException::new));
        orderDto.setBookDtos(DtoConverter.constructBookDtoSet(getAllOrderBooks(id)));
        return orderDto;
    }

    private Set<Book> getAllOrderBooks(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return orderRepository.getAllOrderBooks(id, entityManager);
        } finally {
            entityManager.close();
        }
    }

    public void addBookToOrder(Long orderId, Long bookId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Adding a book...");
        Order order = orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        order.addBook(bookRepository.findOne(bookId, entityManager).orElseThrow(OrderNotFoundException::new));
        try {
            orderRepository.update(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void deleteBookFromOrder(Long orderId, Long bookId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Adding a book...");
        Order order = orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        Set<Book> books = orderRepository.getAllOrderBooks(orderId, entityManager);
        books.remove(bookRepository.findOne(bookId, entityManager).orElseThrow(BookNotFoundException::new));
        order.setBooks(books);
        try {
            orderRepository.update(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
