package com.antonklintsevich.persistense;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Subgenre;
import com.antonklintsevich.exception.BookNotFoundException;

@Repository
public class BookRepository extends AbstractHibernateDao<Book> {
    public Set<Subgenre> getAllBookSubgenres(Long id, Session session) {
        Book book = findOne(id, session).orElseThrow(BookNotFoundException::new);
        Hibernate.initialize(book.getSubgenres());
        return book.getSubgenres();
    }

}
