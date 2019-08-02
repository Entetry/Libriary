package com.antonklintsevich.persistense;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.common.UserDto;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.User;
@Repository
public class UserRepository {
    
    
    public User getCurrentUser(Long id, Session session) {
        User userResult ;
        Query query = session.createQuery("from User where id=:id");
        query.setParameter("id", id);
        userResult = (User)query.uniqueResult();
        return userResult;

    }

    public  List<User> getAllUsers(Session session) {
        List<User> users = new ArrayList<User>();
            Query query = session.createQuery("from User");
            users = query.list();
            return users;

    }

    public Set<Book> getAllUserBooks(Long id, Session session) {
        Query query = session.createQuery("from User where id=:id");
        query.setParameter("id", id);
        User user = (User) query.uniqueResult();
        return user.getBooks();
    }

    public void delete(Long id,Session session) {
        System.out.println("Deleting  record...");
            String hql = "delete from User where id = :id";
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            q.executeUpdate();
    }

    public void update(Long userId,User userFromDto,Session session) {
     
            System.out.println("Updating author...");
            User user =getCurrentUser(userId, session);
            user.setFirstname(userFromDto.getFirstname());
            user.setLastname(userFromDto.getLastname());
            user.setPassword(userFromDto.getPassword());
            user.setEmail(userFromDto.getEmail());
            user.setDob(userFromDto.getDob());
            session.saveOrUpdate(user);
        
       
    }

    public void create(UserDto dto,Session session) {
      
            System.out.println("Creating record...");
            User user = DtoConverter.constructUserFromDto(dto);
            session.save(user);
      
    }

}
