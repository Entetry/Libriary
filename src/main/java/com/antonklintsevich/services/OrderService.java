package com.antonklintsevich.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.common.OrderDto;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.OrderNotFoundException;
import com.antonklintsevich.exception.RoleNotFoundException;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.DbUnit;
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

    public void create(Long userId, Long... bookId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = new Order();
        BigDecimal totalPrice = new BigDecimal(0);
        for (int i = 0; i < bookId.length; i++) {
            Book book = bookRepository.findOne(bookId[i], entityManager).orElseThrow(BookNotFoundException::new);
            order.addBook(book);
            totalPrice = totalPrice.add(book.getPrice());
        }
        order.setUser(userRepository.findOne(userId, entityManager).orElseThrow(RoleNotFoundException::new));
        order.setOrderdate(new Date());
        order.setPrice(totalPrice);
        try {
            orderRepository.create(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<OrderDto> getAllOrdersAsOrderDTO() {
        return getAllOrders().stream().map(order->DtoConverter.constructOrderDTO(order)).collect(Collectors.toList());
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
