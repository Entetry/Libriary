package com.antonklintsevich.example;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;

import entity.Book;
import entity.Genres;
import entity.User;
import persistense.BookRepository;
import persistense.DbUnit;
import persistense.UserRepository;
import services.UserService;

public class Main {
	public static void main(String [ ] args) {
	    
	    UserRepository userRepository = new UserRepository();
	    BookRepository bookRepositroy = new BookRepository();
	    UserService userServise=new UserService(userRepository, bookRepositroy);
	    userServise.addBooktoUser(69l, 70l);
	   
	    
	}
	  

}
