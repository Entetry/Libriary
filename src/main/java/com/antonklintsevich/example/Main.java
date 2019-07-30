package com.antonklintsevich.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import common.BookDto;
import common.DtoConverter;
import common.UserDto;
import entity.Book;
import entity.Genres;
import persistense.BookRepository;
import persistense.DbUnit;
import persistense.UserRepository;
import services.UserService;
import common.DtoConverter;

public class Main {
	public static void main(String [ ] args) {
	    
	    UserRepository userRepository = new UserRepository();
	    BookRepository bookRepositroy = new BookRepository();
	    UserService userServise=new UserService(userRepository, bookRepositroy);
	    userServise.addBooktoUser(1l, 1l);
	    
	   Set<Book>userBooks = userRepository.getAllBooks(1l);
	   for(Book book:userBooks) {
	       System.out.println(book.getName());
	   }
	}
	  

}
