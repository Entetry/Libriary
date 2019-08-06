package com.antonklintsevich.persistense;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
@Repository
public class OrderRepository<T>
extends AbstractHibernateDao<T> implements IGenericDao<T> {

    public Set<Book> getAllOrderBooks(Long id,Session session){
       Order order= (Order) findOne(id, session);
       Hibernate.initialize(order.getBooks());
       return order.getBooks();
    }
}
