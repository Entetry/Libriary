package com.antonklintsevich.persistense;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public Set<Book> findBooksByUsersRequest(String data, Session session) {
        Set<Book> books = new HashSet<>();
        books.addAll((List<Book>) (session
                .createQuery("from Book b where b.bookname like '" + data + "%' or b.author like '" + data + "%'")
                .list()));
        return books;
    }

}
