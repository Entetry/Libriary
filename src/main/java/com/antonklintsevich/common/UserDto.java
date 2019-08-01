package com.antonklintsevich.common;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.antonklintsevich.entity.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto implements Serializable {
    
    @JsonProperty
    private Long id;
    private String firstname;
    private String lastname;
    private Date dob;
    private String email;
    private Set<Book> books;
    public UserDto(Long id, String firstname,String lastname,Date dob,String email) {
        this.id=id;
        this.dob=dob;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.books=books;
    }
    public UserDto() {}
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
}
