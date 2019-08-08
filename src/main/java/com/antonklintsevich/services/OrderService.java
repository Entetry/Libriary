package com.antonklintsevich.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void delete(Long orderId) {
        Session session = DbUnit.getSessionFactory().openSession();
        
        Transaction transaction = session.beginTransaction();
        orderRepository.findOne(orderId, session).orElseThrow(OrderNotFoundException::new);
        try {
            orderRepository.deleteBooksFromOrder(orderId, session);
            orderRepository.deleteById(orderId, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void update(Long orderId, OrderDto orderDto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        Order order = orderRepository.findOne(orderId, session).orElseThrow(OrderNotFoundException::new);
        order.setPrice(orderDto.getPrice());
        order.setBooks(DtoConverter.constructBookSet(orderDto.getBookDtos()));
        order.setPrice(orderDto.getPrice());
        order.setOrderdate(orderDto.getOrderdate());
        try {
            orderRepository.update(order, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void create(Long userId, Long... bookId) {
        Session session = DbUnit.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Order order = new Order();
        BigDecimal totalPrice = new BigDecimal(0);
        for (int i = 0; i < bookId.length; i++) {
            Book book = bookRepository.findOne(bookId[i], session).orElseThrow(BookNotFoundException::new);
            order.addBook(book);
            totalPrice = totalPrice.add(book.getPrice());
        }
        order.setUser(userRepository.findOne(userId, session).orElseThrow(RoleNotFoundException::new));
        order.setOrderdate(new Date());
        order.setPrice(totalPrice);
        try {
            orderRepository.create(order, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public List<OrderDto> getAllOrdersAsOrderDTO() {
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : getAllOrders()) {
            orderDtos.add(DtoConverter.constructOrderDTO(order));
        }

        return orderDtos;
    }

    public List<Order> getAllOrders() {
        Session session = DbUnit.getSessionFactory().openSession();

        List<Order> orders;
        try {
            orders = orderRepository.findAll(session);
        } finally {
            session.close();
        }
        return orders;
    }

    public OrderDto getOrderById(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();
        OrderDto orderDto = null;
        orderDto = DtoConverter
                .constructOrderDTO(orderRepository.findOne(id, session).orElseThrow(OrderNotFoundException::new));
        orderDto.setBookDtos(DtoConverter.constructBookDtoSet(getAllOrderBooks(id)));
        return orderDto;
    }

    public Set<Book> getAllOrderBooks(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();

        Set<Book> books;
        try {
            books = orderRepository.getAllOrderBooks(id, session);
        } finally {
            session.close();
        }
        return books;
    }

    public void addBookToOrder(Long orderId, Long bookId) {

        Session session = DbUnit.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("Adding a book...");
        Order order = orderRepository.findOne(orderId, session).orElseThrow(OrderNotFoundException::new);
        order.addBook(bookRepository.findOne(bookId, session).orElseThrow(OrderNotFoundException::new));
        try {
            orderRepository.update(order, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void deleteBookFromOrder(Long orderId, Long bookId) {

        Session session = DbUnit.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("Adding a book...");
        Order order = orderRepository.findOne(orderId, session).orElseThrow(OrderNotFoundException::new);
        Set<Book> books = orderRepository.getAllOrderBooks(orderId, session);
        books.remove(bookRepository.findOne(bookId, session).orElseThrow(BookNotFoundException::new));
        order.setBooks(books);
        try {
            orderRepository.update(order, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
