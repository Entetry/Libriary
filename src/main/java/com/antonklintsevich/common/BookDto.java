package com.antonklintsevich.common;

import java.io.Serializable;
import java.util.Date;

import com.antonklintsevich.entity.Genres;
import com.fasterxml.jackson.annotation.JsonProperty;
public class BookDto implements Serializable{
	
	public BookDto() {
	};
	 
	@JsonProperty
	private Long id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String author;
	@JsonProperty
	private Genres genre;
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Genres getGenre() {
		return genre;
	}

	public void setGenre(Genres genre) {
		this.genre=genre;
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

}
