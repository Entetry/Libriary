package com.antonklintsevich.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDto implements Serializable {

    public BookDto() {
    };

    public BookDto(Long id, String bookname, String author, Set<SubgenreDto> subgenres, int numberofpages,
            Date dateAdd) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.subgenres = subgenres;
        this.numberOfPages = numberofpages;
        this.dateAdd = dateAdd;
    }

    @JsonProperty
    private Long id;
    @JsonProperty
    private String bookname;
    @JsonProperty
    private String author;
    @JsonProperty
    private Set<SubgenreDto> subgenres;
    @JsonProperty
    private int numberOfPages;
    @JsonProperty
    private Date dateAdd;
    @JsonProperty
    private BigDecimal price;

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

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Set<SubgenreDto> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(Set<SubgenreDto> subgenres) {
        this.subgenres = subgenres;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
