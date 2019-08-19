package com.antonklintsevich.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
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
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<BookDto> getAllBookDtosSorted(String field, String sorttype) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return bookRepository.getAllBooksSorted(entityManager, field, sorttype).stream().map(book->DtoConverter.constructBookDTO(book)).collect(Collectors.toList());
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
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public Set<BookDto> getBooksByUsersData(String data) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return DtoConverter.constructBookDtoSet(bookRepository.findBooksByUsersRequest(data, entityManager));
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
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<BookDto> getAllBooksAsBookDTO() {
        return getAllBooks().stream().map(book -> DtoConverter.constructBookDTO(book)).collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return bookRepository.findAll(entityManager);
        } finally {
            entityManager.close();
        }
    }

    public BookDto getBookById(Long id) throws MyResourceNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return DtoConverter.constructBookDTO(
                    bookRepository.findOne(id, entityManager).orElseThrow(BookNotFoundException::new));
        } finally {
            entityManager.close();
        }
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
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

}
