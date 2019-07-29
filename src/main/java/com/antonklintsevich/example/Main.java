package com.antonklintsevich.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import common.BookDto;
import entity.Book;
import entity.Genres;
import persistense.DbUnit;
import persistense.Repository;

public class Main {
	public static void main(String [ ] args) {
		Session session=DbUnit.getSession();
        List <BookDto> bookdtolist = new ArrayList<BookDto>();
        Repository repos=new Repository();
 
     Book book=new Book();
     List <Book> knigi=new ArrayList<Book>();
     for(int i=0;i<10;i++) {
     book.setAuthor("Kog"+i);
     book.setDateAdd(new Date());
     book.setGenre("Horror");
     book.setName("KEK"+i);
     book.setNumberOfPages(123+i*5);
     knigi.add(book);
     
     }
     
     for(Book b : knigi) {
    	 bookdtolist.add(repos.constructBookDTO(b));
     }

     for(int i=0;i<knigi.size();i++) {
    	 repos.create(bookdtolist.get(i));
     }
	}
	

}
