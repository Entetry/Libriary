package services;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Book;
import entity.User;
import persistense.BookRepository;
import persistense.DbUnit;
import persistense.UserRepository;

public class UserService {
    private UserRepository userRepository;
    private BookRepository bookRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void addBooktoUser(Long userId, Long bookId) {
        Session session = DbUnit.getSessionFactory().openSession();
        
        System.out.println("Adding a book...");
      Transaction transaction=  session.beginTransaction();
        User user = userRepository.getCurrentUser(userId, session);
        user.addBook(bookRepository.getBookById(bookId, session));
        session.saveOrUpdate(user);
        transaction.commit();

    }
}
