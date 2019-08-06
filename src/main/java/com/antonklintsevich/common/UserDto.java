package com.antonklintsevich.common;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto implements Serializable {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private Date dob;
    @JsonProperty
    private String email;
    @JsonProperty
    private Set<Book> books;
    @JsonProperty
    private String password;
    @JsonProperty
    private Set<Role> roles;

    public UserDto(Long id, String firstname, String lastname, Date dob, String email, String password,
            Set<Book> books,Set<Role>roles) {
        this.id = id;
        this.dob = dob;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.books = books;
        this.password = password;
        this.roles=roles;
    }

    public UserDto() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
