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
    private UserRepository _userRepository;
    private BookRepository _bookRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        _userRepository = userRepository;
        _bookRepository = bookRepository;
    }

    public void addBooktoUser(Long userId, Long bookId) {
        Session session = DbUnit.getSessionFactory().openSession();
        
        System.out.println("Adding a book...");
      Transaction transaction=  session.beginTransaction();
        User user = _userRepository.getCurrentUser(userId, session);
        
        Set<Book> booksToAdd = _userRepository.getAllBooks(19l, session);
        
        booksToAdd.add(_bookRepository.getBookById(bookId, session));

        user.setBooks(booksToAdd);
        session.saveOrUpdate(user);
        transaction.commit();

    }
}
