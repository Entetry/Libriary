package com.antonklintsevich.persistense;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.User;

@Repository
public class UserRepository {

    public User getCurrentUser(Long id, Session session) {

        Query query = session.createQuery("from User where id=:id");
        query.setParameter("id", id);
        return (User) query.uniqueResult();

    }

    public List<User> getAllUsers(Session session) {
        Query query = session.createQuery("from User");
        return query.list();
    }

    public Set<Book> getAllUserBooks(Long id, Session session) {
        Query query = session.createQuery("from User where id=:id");
        query.setParameter("id", id);
        User user = (User) query.uniqueResult();
        Hibernate.initialize(user.getBooks());
        return user.getBooks();
    }
    public void delete(Long id, Session session) {
        System.out.println("Deleting  record...");
        String hql = "delete from User where id = :id";
        Query q = session.createQuery(hql);
        q.setParameter("id", id);
        q.executeUpdate();
    }

    public void update(Long userId, User user, Session session) {

        System.out.println("Updating author...");
        User currentUser = getCurrentUser(userId, session);
        currentUser.setFirstname(user.getFirstname());
        currentUser.setLastname(user.getLastname());
        currentUser.setPassword(user.getPassword());
        currentUser.setEmail(user.getEmail());
        currentUser.setDob(user.getDob());
        session.saveOrUpdate(currentUser);

    }

    public void create(User user, Session session) {

        System.out.println("Creating record...");
        session.save(user);

    }

}
