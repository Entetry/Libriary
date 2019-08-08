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
        ;
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
        try {
            Order order = orderRepository.findOne(orderId, session);
            order.setPrice(orderDto.getPrice());
            order.setBooks(DtoConverter.constructBookSet(orderDto.getBookDtos()));
            order.setPrice(orderDto.getPrice());
            order.setOrderdate(orderDto.getOrderdate());
            orderRepository.update(order, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void create(Long userId,Long ...bookId ) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        
        
        try {
            Order order =new Order();
            var totalPrice = new BigDecimal(0);
            for(int i=0;i<bookId.length;i++) {
                Book book=bookRepository.findOne(bookId[i], session);
            order.addBook(book);
            totalPrice = totalPrice.add(book.getPrice());
            }
            order.setUser(userRepository.findOne(userId, session));
            var date = new Date();
            order.setOrderdate(date);
            order.setPrice(totalPrice);
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
        try {
            orderDto = DtoConverter.constructOrderDTO(orderRepository.findOne(id, session));
            orderDto.setBookDtos(DtoConverter.constructBookDtoSet(getAllOrderBooks(id)));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
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
        ;
        try {
            System.out.println("Adding a book...");
            Order order = orderRepository.findOne(orderId, session);
            order.addBook(bookRepository.findOne(bookId, session));
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
        ;
        try {
            System.out.println("Adding a book...");
            Order order = orderRepository.findOne(orderId, session);
            Set<Book> books=orderRepository.getAllOrderBooks(orderId, session);
            books.remove(bookRepository.findOne(bookId, session));
            order.setBooks(books);
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
