package com.antonklintsevich.persistense;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Role;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.exception.UserNotFoundException;

@Repository
public class UserRepository extends AbstractHibernateDao<User> {

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
}
