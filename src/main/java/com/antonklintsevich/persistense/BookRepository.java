package com.antonklintsevich.persistense;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Subgenre;

@Repository
public class BookRepository extends AbstractHibernateDao<Book> {
    public Set<Subgenre> getAllBookSubgenres(Long id, Session session) {
        Book book = findOne(id, session);
        Hibernate.initialize(book.getSubgenres());
        return book.getSubgenres();
    }

}
