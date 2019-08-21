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
import com.antonklintsevich.common.FilterData;
import com.antonklintsevich.common.SortData;
import com.antonklintsevich.common.SearchParameters;
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
    public List<BookDto> getAllBooks(@RequestBody SearchParameters searchPatameters) {
        List<SortData> searchData = searchPatameters.getSearchData();
        List<FilterData> filterData=searchPatameters.getFilterData();
        List<BookDto> bookDtos = null;
        if (searchData.isEmpty()&& filterData.isEmpty()) {
            bookDtos = bookService.getAllBooksAsBookDTO();
        } 
        else {
            if(!filterData.isEmpty()) {
            filterData=filterData.stream()
                    .filter(filter->filter.getField()!=null)
                    .filter(filter->filter.getFilterType()!=null)
                    .filter(filter->filter.getValue()!=null)
                    .collect(Collectors.toList());
            searchPatameters.setFilterData(filterData);
            }
            if(!searchData.isEmpty()) {
                searchData = searchData.stream().filter(search -> search.getName() != null).collect(Collectors.toList());
            for (SortData data : searchData) {
                if (!isSortDataValid(data)) {
                    return bookDtos = bookService.getAllBooksAsBookDTO();
                }
                isSortOrderValid(data);
            }
            searchPatameters.setSearchData(searchData);
            }
            bookDtos = bookService.getAllBookDtosSorted(searchPatameters);}
        return bookDtos ;
        }

    private boolean isSortDataValid(SortData data) {
        if (((data.getName().equals("price")) || (data.getName().equals("bookname"))
                || (data.getName().equals("author")) || (data.getName().equals("numberofpages"))))
            return true;
        return false;
    }

    private boolean isSortOrderValid(SortData data) {
        if (data.getSortOrder() == null
                || !((data.getSortOrder().equals("ASC") || (data.getSortOrder().equals("DESC"))))) {
            data.setSortOrder("ASC");
            return false;
        }
        return true;
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
}
