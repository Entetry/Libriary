package com.antonklintsevich;

import java.util.Date;
import java.util.UUID;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
//    public static void main(String[] args) throws JsonProcessingException {
//        User user = new User();
//        user.setDob(new Date());
//        user.setEmail("kek@gmail.com");
//        user.setFirstname("dima");
//        user.setLastname("pidor");
//        user.setPassword("1233");
//        user.setUsername("Username");
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writeValueAsString(user));
//        System.out.println(UUID.randomUUID().toString());
//        Book book=new Book();
//        book.setAuthor("Vova");
//        book.setBookname("Haduken");
//        book.setDateAdd(new Date());
//        book.setNumberOfPages(333);
//        System.out.println(mapper.writeValueAsString(book));
//        Order order=new Order();
//        order.setUser(user);
//        order.setBooks(user.getBooks());
//        System.out.println(mapper.writeValueAsString(order));
//    }
}
