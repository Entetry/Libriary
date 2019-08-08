package com.antonklintsevich.persistense;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.exception.OrderNotFoundException;

@Repository
public class OrderRepository extends AbstractHibernateDao<Order> {

    public Set<Book> getAllOrderBooks(Long id, Session session) {
        Order order = findOne(id, session).orElseThrow(OrderNotFoundException::new);
        Hibernate.initialize(order.getBooks());
        return order.getBooks();
    }

    public void deleteBooksFromOrder(Long orderId, Session session) {
        Order order = findOne(orderId, session).orElseThrow(OrderNotFoundException::new);
        order.setBooks(null);
        session.saveOrUpdate(order);

    }
}
