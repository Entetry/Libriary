package com.antonklintsevich.persistense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.User;

@Repository
public class BookRepository {


    public static List<Book> getAllBooks(Session session) {
        List<Book> books = new ArrayList<Book>();
            Query query = session.createQuery("from Book");
            books = query.list();
            
       
        return books;
    }

    public void delete(Long id,Session session) {
       
            String hql = "delete from Book where id = :id";
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            q.executeUpdate();
        
       
    }

    public void update(Long bookId,BookDto dto,Session session) {
        System.out.println("Updating author...");
        Book book =getBookById(bookId, session);
        book.setAuthor(dto.getAuthor());
        book.setDateAdd(dto.getDateAdd());
        book.setGenre(dto.getGenre());
        book.setName(dto.getName());
        book.setNumberOfPages(dto.getNumberOfPages());
        session.saveOrUpdate(book);
    
    }

    public void create(BookDto dto,Session session) {
        System.out.println("Creating record...");
        Book book = DtoConverter.constructBookFromDto(dto);
        session.save(book);
    }

    public Book getBookById(Long id, Session session) {
        Book bookResult ;
        Query query = session.createQuery("from Book where id=:id");
        query.setParameter("id", id);
        bookResult=(Book) query.uniqueResult();
        return bookResult;

    }
}
