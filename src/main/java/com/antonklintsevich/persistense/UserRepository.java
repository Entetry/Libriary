package com.antonklintsevich.persistense;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Role;
import com.antonklintsevich.entity.User;

@Repository
public class UserRepository<T>
extends AbstractHibernateDao<T> implements IGenericDao<T> {

    public Set<Book> getAllUserBooks(Long id,Session session){
       User user= (User) findOne(id, session);
       Hibernate.initialize(user.getBooks());
       return user.getBooks();
    }
    public Set<Role> getAllUserRoles(Long id,Session session){
        User user= (User) findOne(id, session);
        Hibernate.initialize(user.getRoles());
        return user.getRoles();
    }
}
