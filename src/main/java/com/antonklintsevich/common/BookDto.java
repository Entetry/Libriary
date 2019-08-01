package com.antonklintsevich.common;

import com.antonklintsevich.entity.Genres;
public class BookDto {
	
	public BookDto() {
	};
	 
	
	private Long id;
	private String name;
	private String author;
	private Genres genre;
	private int numberOfPages;
	
	
	
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

}
