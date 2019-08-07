package com.antonklintsevich.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Subgenre;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.DbUnit;
import com.antonklintsevich.persistense.SubgenreRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private SubgenreRepository subgenreRepository;

    public void delete(Long bookId) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        ;
        try {
            bookRepository.deleteById(bookId, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void update(Long bookId, BookDto bookDto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        try {
            Book book = bookRepository.findOne(bookId, session);
            book.setAuthor(bookDto.getAuthor());
            book.setBookname(bookDto.getBookname());
            book.setDateAdd(bookDto.getDateAdd());
            bookRepository.update(book, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void create(BookDto bookDto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        Book book = DtoConverter.constructBookFromDto(bookDto);
        try {

            bookRepository.create(book, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public List<BookDto> getAllBooksAsBookDTO() {
        List<BookDto> bookDtos = new ArrayList<BookDto>();

        for (Book book : getAllBooks()) {
            bookDtos.add(DtoConverter.constructBookDTO(book));
        }

        return bookDtos;
    }

    public List<Book> getAllBooks() {
        Session session = DbUnit.getSessionFactory().openSession();

        List<Book> books;
        try {
            books = bookRepository.findAll(session);
        } finally {
            session.close();
        }
        return books;
    }

    public BookDto getBookById(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();

        BookDto bookDto = null;
        try {
            bookDto = DtoConverter.constructBookDTO(bookRepository.findOne(id, session));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        return bookDto;
    }

    public void addSubgenretoBook(Long bookId, Long subgenreId) {

        Session session = DbUnit.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        ;
        try {
            System.out.println("Adding a book...");
            Book book = bookRepository.findOne(bookId, session);
            book.addSubgenre(subgenreRepository.findOne(subgenreId, session));
            session.saveOrUpdate(book);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

}
