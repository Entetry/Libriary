package com.antonklintsevich.persistense;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;

@Repository
public class BookRepository {

    public List<Book> getAllBooks(Session session) {

        Query query = session.createQuery("from Book");
        return query.list();

    }

    public void delete(Long id, Session session) {

        String hql = "delete from Book where id = :id";
        Query q = session.createQuery(hql);
        q.setParameter("id", id);
        q.executeUpdate();

    }

    public void update(Long bookId, Book book, Session session) {
        System.out.println("Updating author...");
        Book currentbook = getBookById(bookId, session);
        currentbook.setAuthor(book.getAuthor());
        currentbook.setDateAdd(book.getDateAdd());
        currentbook.setGenre(book.getGenre());
        currentbook.setName(book.getName());
        currentbook.setNumberOfPages(book.getNumberOfPages());
        session.saveOrUpdate(currentbook);

    }

    public void create(Book book, Session session) {
        System.out.println("Creating record...");
        session.save(book);
    }

    public Book getBookById(Long id, Session session) {
        Query query = session.createQuery("from Book where id=:id");
        query.setParameter("id", id);
        return (Book) query.uniqueResult();
    }
}
