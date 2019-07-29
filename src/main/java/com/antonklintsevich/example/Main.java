package com.antonklintsevich.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import common.BookDto;
import common.DtoConverter;
import common.UserDto;
import entity.Book;
import entity.Genres;
import persistense.BookRepository;
import persistense.DbUnit;
import persistense.UserRepository;
import common.DtoConverter;

public class Main {
	public static void main(String [ ] args) {
         UserDto userdto = new UserDto();
         userdto.setDob(new Date());
         userdto.setEmail("kdkdk@ggg.com");
         userdto.setFirstname("Anton");
         userdto.setLastname("Klintcevich");
	    
	    UserRepository userRepository = new UserRepository();
	    
	    userRepository.create(userdto, "123");
	   
     
	}
	  

}
