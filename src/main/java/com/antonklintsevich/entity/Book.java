package com.antonklintsevich.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.antonklintsevich.common.BookDto;

@Entity(name = "Book")
@Table(name = "book")
public class Book implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Book() {
    };

    public Book(String name,String author,Date dateAdd,Genres genre,int numberOfPages) {
        this.bookname = name;
        this.author=author;
        this.dateAdd=dateAdd;
        this.genre=genre;
        this.numberOfPages=numberOfPages;
    };
   
    private Long id;
    private String bookname;
    private String author;
    private Date dateAdd;
    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private Genres genre;
    private int numberOfPages;
    
    

    @Id
    @GeneratedValue
    @Column(name = "book_id")
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

        this.genre = genre;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

}