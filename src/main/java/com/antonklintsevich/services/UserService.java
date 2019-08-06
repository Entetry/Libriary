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
    private UserRepository<User> userRepository;
    @Autowired
    private BookRepository<Book> bookRepository;

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
            users.addAll(userRepository.findAll(session));
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
            User user = userRepository.findOne(userId, session);
            user.addBook(bookRepository.findOne(bookId, session));
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    public void delete(Long userId) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        ;
        try {
            userRepository.deleteById(userId, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void update(Long userId, UserDto userDto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        try {
            User user = DtoConverter.constructUserFromDto(userDto);
            userRepository.update(user, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void create(UserDto dto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        User user = DtoConverter.constructUserFromDto(dto);
        try {

            userRepository.create(user, session);
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

        UserDto userDto = null;
        try {
            userDto = DtoConverter.constructUserDto(userRepository.findOne(id, session));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        return userDto;
    }

    public Set<Book> getAllUserBooks(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();

        Set<Book> books;
        try {
            books = userRepository.getAllUserBooks(id, session);
        } finally {
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
