package com.antonklintsevich.common;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.antonklintsevich.entity.Subgenre;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDto implements Serializable {

    public BookDto() {
    };
    public BookDto(Long id,String bookname,String author,Set<Subgenre> subgenres,int numberofpages,Date dateAdd) {
       this.id=id;
       this.bookname=bookname;
       this.author=author;
       this.subgenres=subgenres;
       this.numberOfPages=numberofpages;
       this.dateAdd=dateAdd;
    }
    @JsonProperty
    private Long id;
    @JsonProperty
    private String bookname;
    @JsonProperty
    private String author;
    private Set<Subgenre> subgenres;
    @JsonProperty
    private int numberOfPages;
    @JsonProperty
    private Date dateAdd;

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
    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }
    public Set<Subgenre> getSubgenres() {
        return subgenres;
    }
    public void setSubgenres(Set<Subgenre> subgenres) {
        this.subgenres = subgenres;
    }
    
   
}
