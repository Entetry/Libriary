package persistense;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import common.BookDto;
import common.DtoConverter;
import common.UserDto;
import entity.Book;
import entity.User;

public class UserRepository {
    public List<UserDto> getAllUserAsUserDTO() {
        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (User user :UserRepository.getAllUsers()) {
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

    public static List<User> getAllUsers() {
        Session session = DbUnit.getSessionFactory().getCurrentSession();
        List<User> users = new ArrayList<User>();
        try {

            Query query = session.createQuery("from User");
            List<User> fd = query.list();
            for (User b : fd) {
                users.add(b);
            }
            return users;
        } finally {
            session.close();
        }
        
    }
    public Set<Book> getAllBooks(Long id, Session session){
        
        try {
            
            Query query = session.createQuery("from User where id=:id");
            query.setParameter("id", id);
            List<User> fd = query.list();
            Set<Book> userBooks = new HashSet<Book>();
            for (User b : fd) {
                userBooks.addAll(b.getBooks());
            }
            
            return userBooks;
        } finally {
            session.close();
        }
        }
    public void delete(Long id) {
        Session session = DbUnit.getSessionFactory().getCurrentSession();
        System.out.println("Deleting  record...");
        session.beginTransaction();
        try {
            String hql = "delete from User where id = :id";
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void update(UserDto dto,String password) {
        Session session = DbUnit.getSessionFactory().getCurrentSession();
        try {

            System.out.println("Updating author...");
            User user =DtoConverter.constructUserFromDto(dto,password);
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
    
    public void create(UserDto dto,String password) {
        Session session = DbUnit.getSessionFactory().getCurrentSession();
        try {
            System.out.println("Creating record...");
            User user =DtoConverter.constructUserFromDto(dto, password);
            user.setPassword(password);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
 
}
