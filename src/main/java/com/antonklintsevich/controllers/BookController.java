package com.antonklintsevich.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.antonklintsevich.common.SearchData;
import com.antonklintsevich.common.SearchPatameters;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.MyResourceNotFoundException;
import com.antonklintsevich.exception.SubgenreNotFoundException;
import com.antonklintsevich.services.BookService;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @PreAuthorize("hasAuthority('WRITE_AUTHORITY')")
    @PutMapping("/books/addsubgenre")
    public void addSubgenre(@RequestParam("bookId") String bookId, @RequestParam("subgenreId") String subgenreId) {
        try {
            bookService.addSubgenretoBook(Long.parseLong(bookId), Long.parseLong(subgenreId));
        } catch (BookNotFoundException | SubgenreNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @GetMapping("/books")
    @ResponseBody
    // @Secured("WRITE_AUTHORITY")
    @PreAuthorize("hasAuthority('READ_AUTHORITY')")
    public List<BookDto> getAllBooks(@RequestBody SearchPatameters searchPatameters ) {
        List<SearchData> searchData=searchPatameters.getSearchData();
        List<BookDto> bookDtos =null;
        if(searchData==null) {
            bookDtos = bookService.getAllBooksAsBookDTO();   
        }
        else if(searchData.size()==1&&searchData.get(0).sortOrder==null) {
            bookDtos= bookService.getBooksByUsersData(searchData.get(0).name);
        }
        else {
            searchData=searchData.stream().filter(search->search.name!=null).collect(Collectors.toList());
            for(SearchData data:searchData) {
                if(!((data.name.equals("price"))||(data.name.equals("bookname"))||(data.name.equals("author"))||(data.name.equals("numberofpages")))) {
                 return bookDtos = bookService.getAllBooksAsBookDTO();
                 
                }
                if(data.sortOrder==null || !((data.sortOrder.equals("ASC")||(data.sortOrder.equals("DESC"))))) {
                    data.sortOrder="ASC";
                }
            }
            bookDtos= bookService.getAllBookDtosSorted(searchData);
        }
        return bookDtos;
    }
//    @GetMapping("/books/search")
//    @ResponseBody
//    // @Secured("WRITE_AUTHORITY")
//    @PreAuthorize("hasAuthority('READ_AUTHORITY')")
//    public Set<BookDto> getBooksByUserData(@RequestParam(name="data") String data){
//        return bookService.getBooksByUsersData(data);
//    }
    @DeleteMapping("/books/{bookId}")
    public void delete(@PathVariable("bookId") Long bookId) {
        try {
            bookService.delete(bookId);
        } catch (BookNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PreAuthorize("hasAuthority('WRITE_AUTHORITY')")
    @PostMapping("/books")

    public void create(@RequestBody BookDto bookDto) {

        bookService.create(bookDto);

    }

    @PreAuthorize("hasAuthority('READ_AUTHORITY')")
    @GetMapping("/books/{bookId}")
    @ResponseBody
    public BookDto getBookbyId(@PathVariable("bookId") Long bookId) {
        try {
            return bookService.getBookById(bookId);
        } catch (BookNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PreAuthorize("hasAuthority('WRITE_AUTHORITY')")
    @PutMapping("/books/{bookId}")
    public void update(@PathVariable("bookId") Long bookId, @RequestBody BookDto bookDto) {
        try {
            bookService.update(bookId, bookDto);
        } catch (BookNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
//    @GetMapping("/books/sort")
//    @ResponseBody
//    // @Secured("WRITE_AUTHORITY")
//    @PreAuthorize("hasAuthority('READ_AUTHORITY')")
//    public List<BookDto> getSortedAscBooks(@RequestParam(name="field") String field,@RequestParam(name="type") String type){
//        if(!((type.equals("ASC")||(type.equals("DESC"))))) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid sort type,please use ASC or DESC");
//        }
//        
//        else if(!((field.equals("price"))||(field.equals("bookname"))||(field.equals("author"))||(field.equals("numberofpages")))) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid fieldname.You can only sort by bookname,author,price and numberofpages");
//        }
//        return bookService.getAllBookDtosSorted(field, type);
//    }
  
}
