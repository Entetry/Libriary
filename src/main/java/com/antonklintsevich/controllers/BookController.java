package com.antonklintsevich.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.antonklintsevich.persistense.BookRepository;
@RestController
public class BookController {
    @Autowired
   private BookRepository bookRepository;
    
}
