package com.antonklintsevich.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

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
import com.antonklintsevich.entity.Role;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.OrderNotFoundException;
import com.antonklintsevich.exception.RoleNotFoundException;
import com.antonklintsevich.exception.UserNotFoundException;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.DbUnit;
import com.antonklintsevich.persistense.RoleRepository;
import com.antonklintsevich.persistense.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<UserDto> getAllUserAsUserDTO() {
        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (User user : getAllUsers()) {
            userDtos.add(DtoConverter.constructUserDto(user));
        }

        return userDtos;
    }

    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users = new ArrayList<>();
        try {
            users.addAll(userRepository.findAll(entityManager));
        }

        finally {
            entityManager.close();
        }
        return users;
    }

    public void addBookToUser(Long userId, Long bookId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Adding a book...");
        User user = userRepository.findOne(userId, entityManager).orElseThrow(UserNotFoundException::new);
        user.addBook(bookRepository.findOne(bookId, entityManager).orElseThrow(BookNotFoundException::new));
        try {
            userRepository.update(user, entityManager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

    }

    public void addRoleToUser(Long userId, Long roleId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Adding a role...");
        User user = userRepository.findOne(userId, entityManager).orElseThrow(UserNotFoundException::new);
        user.addRole(roleRepository.findOne(roleId, entityManager).orElseThrow(RoleNotFoundException::new));
        try {
            userRepository.update(user, entityManager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

    }

    public void delete(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        userRepository.findOne(userId, entityManager).orElseThrow(UserNotFoundException::new);
        try {
            userRepository.deleteById(userId, entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(Long userId, UserDto userDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        User user = userRepository.findOne(userId, entityManager).orElseThrow(UserNotFoundException::new);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setDob(userDto.getDob());
        user.setEmail(userDto.getEmail());
        try {
            userRepository.update(user, entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void create(UserDto dto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        User user = DtoConverter.constructUserFromDto(dto);
        try {
            userRepository.create(user, entityManager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public UserDto getUserById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserDto userDto = null;
        userDto = DtoConverter
                .constructUserDto(userRepository.findOne(id, entityManager).orElseThrow(UserNotFoundException::new));
        try {
            userDto.setRoles(DtoConverter.constructRoleDtoSet(getAllUserRoles(id)));
            userDto.setBooks(DtoConverter.constructBookDtoSet(getAllUserBooks(id)));
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
        return userDto;
    }

    private Set<Book> getAllUserBooks(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Set<Book> books;
        books = userRepository.getAllUserBooks(id, entityManager);
        entityManager.close();

        return books;
    }

    public Set<BookDto> getAllUserBooksAsBookDto(Long userId) {
        Set<BookDto> bookDtos = new HashSet<>();
        for (Book book : getAllUserBooks(userId)) {
            bookDtos.add(DtoConverter.constructBookDTO(book));
        }

        return bookDtos;
    }

    public Set<Role> getAllUserRoles(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Set<Role> roles;
        try {
            roles = userRepository.getAllUserRoles(id, entityManager);
        } finally {
            entityManager.close();
        }
        return roles;
    }

}
