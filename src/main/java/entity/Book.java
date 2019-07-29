package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import common.BookDto;

@Entity(name="Book")
@Table(name="book")
public class Book implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Book() {
    };

    public Book(String name) {
        this.bookname = name;
    };

  

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookname;
    private String author;
    private Date dateAdd;
    private Genres genre;
    private int numberOfPages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return bookname;
    }

    public void setName(String name) {
        this.bookname = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
      
           this.genre=genre;
        }
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

}