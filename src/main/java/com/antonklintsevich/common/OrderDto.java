package com.antonklintsevich.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -7870350184689366168L;
    @JsonProperty
    private Long id;
    @JsonProperty
    private User user;
    @JsonProperty
    private Date orderdate;
    @JsonProperty
    private Set<Book> books;
    @JsonProperty
    private BigDecimal price;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Date getOrderdate() {
        return orderdate;
    }
    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }
    public Set<Book> getBooks() {
        return books;
    }
    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void addBook(Book book) {
        this.books.add(book);
    }
}
