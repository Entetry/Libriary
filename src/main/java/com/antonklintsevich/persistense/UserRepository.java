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
    public List<UserDto> getAllUserAsUserDTO(Session session) {
        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (User user : getAllUsers(session)) {
            userDtos.add(DtoConverter.constructUserDto(user));
        }

        return userDtos;
    }
    
    public User getCurrentUser(Long id, Session session) {
        List<User> users = new ArrayList<User>();

        User userResult = new User();
        Query query = session.createQuery("from User where id=:id");
        query.setParameter("id", id);
        List<User> userList = query.list();
        for (User user : userList) {
            userResult = user;
        }
        return userResult;

    }

    public  List<User> getAllUsers(Session session) {
        List<User> users = new ArrayList<User>();
            Query query = session.createQuery("from User");
            List<User> fd = query.list();
            for (User b : fd) {
                users.add(b);
            }
            return users;

    }

    public Set<Book> getAllBooks(Long id, Session session) {
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

    public void update(Long userId,String firstname,String lastname,Session session) {
     
            System.out.println("Updating author...");
            User user =getCurrentUser(userId, session);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            session.saveOrUpdate(user);
        
       
    }

    public void create(UserDto dto, String password,Session session) {
      
            System.out.println("Creating record...");
            User user = DtoConverter.constructUserFromDto(dto, password);
            user.setPassword(password);
            session.save(user);
      
    }

}
