package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import common.BookDto;

@Entity
public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public Book() {};

	public Book(String name) {
		this.bookname = name;
	};
	public Book(BookDto dto) {
		this.author=dto.getAuthor();
		this.bookname=dto.getName();
		this.genre=dto.getGenre();
		
	
		this.numberOfPages=dto.getNumberOfPages();
		if(dto.getId()!=null) {
		this.id=dto.getId();}
	}
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

	public void setGenre(String genre) {
		switch(genre) {
		case "Horror":{
			this.genre=Genres.Horror;
			break;
		}
		case "Action":{
			this.genre=Genres.Action;
			break;
		}
		case "Thriller":{
			this.genre=Genres.Thriller;
			break;
		}
		case "Drama":{
			this.genre=Genres.Drama;
			break;
		}
		default:this.genre=Genres.Unknown;
			
		}
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}


}