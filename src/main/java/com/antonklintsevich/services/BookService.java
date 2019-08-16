package com.antonklintsevich.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Subgenre;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.MyResourceNotFoundException;
import com.antonklintsevich.exception.SubgenreNotFoundException;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.SubgenreRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private SubgenreRepository subgenreRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void delete(Long bookId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        bookRepository.findOne(bookId, entityManager).orElseThrow(BookNotFoundException::new);
        try {
            bookRepository.deleteById(bookId, entityManager);
            transaction.commit();
        } catch (Exception e) {

            transaction.rollback();
            ;
        } finally {
            entityManager.close();
        }
    }

    public void update(Long bookId, BookDto bookDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Book book = bookRepository.findOne(bookId, entityManager).orElseThrow(BookNotFoundException::new);
        book.setAuthor(bookDto.getAuthor());
        book.setBookname(bookDto.getBookname());
        book.setDateAdd(bookDto.getDateAdd());
        try {
            bookRepository.update(book, entityManager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<BookDto> getBooksByUsersData(String data) {
        List<BookDto> bookDtos = new ArrayList<BookDto>();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        bookDtos.addAll(DtoConverter.constructBookDtoSet(bookRepository.findBooksByUsersRequest(data, entityManager)));
        return bookDtos;
    }

    public void create(BookDto bookDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Book book = DtoConverter.constructBookFromDto(bookDto);
        try {
            bookRepository.create(book, entityManager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<BookDto> getAllBooksAsBookDTO() {
        List<BookDto> bookDtos = new ArrayList<BookDto>();
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger("com.antonklintsevich.debug");
        logger.debug("DIMA POSHEL NAHUI");
        for (Book book : getAllBooks()) {
            bookDtos.add(DtoConverter.constructBookDTO(book));
        }
        return bookDtos;
    }

    public List<Book> getAllBooks() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Book> books;
        try {
            books = bookRepository.findAll(entityManager);
        } finally {
            entityManager.close();
        }
        return books;
    }

    public BookDto getBookById(Long id) throws MyResourceNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        BookDto bookDto = null;
        bookDto = DtoConverter
                .constructBookDTO(bookRepository.findOne(id, entityManager).orElseThrow(BookNotFoundException::new));
        entityManager.close();

        return bookDto;
    }

    public void addSubgenretoBook(Long bookId, Long subgenreId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Adding a book...");
        Book book = bookRepository.findOne(bookId, entityManager).orElseThrow(BookNotFoundException::new);
        Subgenre subgenre = subgenreRepository.findOne(subgenreId, entityManager)
                .orElseThrow(SubgenreNotFoundException::new);
        book.addSubgenre(subgenre);
        try {
            bookRepository.update(book, entityManager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

}
