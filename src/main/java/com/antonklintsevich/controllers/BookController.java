package com.antonklintsevich.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.exception.MyResourceNotFoundException;
import com.antonklintsevich.services.BookService;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @PutMapping("/books/addsubgenre")
    public void addSubgenre(@RequestParam("bookId") String bookId, @RequestParam("subgenreId") String subgenreId) {
        try {
        bookService.addSubgenretoBook(Long.parseLong(bookId), Long.parseLong(subgenreId));
        }
        catch(MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(),exc);
        }
    }

    @GetMapping("/books")
    @ResponseBody
    public List<BookDto> getAllBooks() { 
        return bookService.getAllBooksAsBookDTO();}
        
    

    @DeleteMapping("/books/{bookId}")
    public void delete(@PathVariable("bookId") Long bookId) {
        bookService.delete(bookId);
    }

    @PostMapping("/books")
    public void create(@RequestBody BookDto bookDto) {
     
        bookService.create(bookDto);

    }

    @GetMapping("/books/{bookId}")
    @ResponseBody
    public BookDto getBookbyId(@PathVariable("bookId") Long bookId) {
        try {
        return bookService.getBookById(bookId);}
        catch(MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found",exc);
        }
    }

    @PutMapping("/books/{bookId}")
    public void update(@PathVariable("bookId") Long bookId, @RequestBody BookDto bookDto) {
        bookService.update(bookId, bookDto);
    }

}
