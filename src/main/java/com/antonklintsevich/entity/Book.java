package com.antonklintsevich.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "Book")
@Table(name = "book")
public class Book implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Book() {
    };

    public Book(String name, String author, Date dateAdd, Genres genre, int numberOfPages) {
        this.bookname = name;
        this.author = author;
        this.dateAdd = dateAdd;
        this.numberOfPages = numberOfPages;
    };

    private Long id;
    private String bookname;
    private String author;
    private Date dateAdd;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_subgenre", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "subgenre_id"))
    private Set<Subgenre> subgenres;
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

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Set<Subgenre> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(Set<Subgenre> subgenres) {
        this.subgenres = subgenres;
    }
    public void addBook(Subgenre subgenre) {
        this.subgenres.add(subgenre);
    }


}