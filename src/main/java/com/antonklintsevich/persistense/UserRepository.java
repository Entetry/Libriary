package com.antonklintsevich.persistense;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Role;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.exception.UserNotFoundException;

@Repository
public class UserRepository extends AbstractHibernateDao<User> implements IUserRepository {

    @Autowired
    private EntityManager entityManager;
    
    public Set<Book> getAllUserBooks(Long id, Session session) throws UserNotFoundException {
        User user = findOne(id, session).orElseThrow(UserNotFoundException::new);
        Hibernate.initialize(user.getBooks());
        return user.getBooks();
    }

    public Set<Role> getAllUserRoles(Long id, Session session) throws UserNotFoundException {
        User user = findOne(id, session).orElseThrow(UserNotFoundException::new);
        Hibernate.initialize(user.getRoles());
        return user.getRoles();
    }
    @Override
    public User findByUsername(String username,Session session) {
        Query query =  entityManager.createNativeQuery("SELECT * FROM users WHERE username = ?", User.class);
        query.setParameter(1, username);
        return (User) query.getSingleResult();
        //return (User)session.createQuery("from User where User.username= :username").setParameter("username", username).uniqueResult();
         
    }
}
