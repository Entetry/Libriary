package com.antonklintsevich;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.security.SpringSecurityConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
  ///public static void main(String[] args) throws JsonProcessingException {
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
//        BookDto dto=new BookDto();
//        dto.setAuthor("author");
//        dto.setBookname("name");
//        dto.setDateAdd(new Date());
//        dto.setBookname("bookname");
//        dto.setPrice(new BigDecimal("0.05"));
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writeValueAsString(dto));
//       BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
 //      System.out.println(passwordEncoder.encode("1233"));
  // }
   }
