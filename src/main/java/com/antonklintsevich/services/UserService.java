package com.antonklintsevich.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.common.UserDto;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.DbUnit;
import com.antonklintsevich.persistense.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    public List<UserDto> getAllUserAsUserDTO() {
        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (User user : getAllUsers()) {
            userDtos.add(DtoConverter.constructUserDto(user));
        }

        return userDtos;
    }
    public List<User> getAllUsers() {
        Session session = DbUnit.getSessionFactory().openSession();

        List<User> users = new ArrayList<>();
        try {
            users.addAll(userRepository.getAllUsers(session));
        }

        finally {
            session.close();
        }
        return users;
    }

    public void addBooktoUser(Long userId, Long bookId) {

        Session session = DbUnit.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        ;
        try {
            System.out.println("Adding a book...");
            User user = userRepository.getCurrentUser(userId, session);
            user.addBook(bookRepository.getBookById(bookId, session));
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    public void Delete(Long userId) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        ;
        try {
            userRepository.delete(userId, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void Update(Long userId, UserDto userDto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        try {
            User user=DtoConverter.constructUserFromDto(userDto);
            session.beginTransaction();
            userRepository.update(userId, user, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void Create(UserDto dto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        try {

            userRepository.create(dto, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    public UserDto getUserById(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();
        
        Transaction transaction = session.beginTransaction();
        UserDto userDto=null;
        try {
            userDto=DtoConverter.constructUserDto(userRepository.getCurrentUser(id, session));
           
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return userDto;
    }
    public Set<Book> getAllUserBooks(Long id){
        Session session = DbUnit.getSessionFactory().openSession();

        Set<Book> books = new HashSet<>();
        try {
            books.addAll(userRepository.getAllUserBooks(id, session));
        }
        finally {
            session.close();
        }
        return books;
    }
    public Set<BookDto> getAllUserBooksAsBookDto(Long userId) {
        Set<BookDto> bookDtos = new HashSet<>();

        for (Book book : getAllUserBooks(userId)) {
            bookDtos.add(DtoConverter.constructBookDTO(book));
        }

        return bookDtos;
    }
}
