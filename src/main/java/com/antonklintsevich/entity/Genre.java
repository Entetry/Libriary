package com.antonklintsevich.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Genre")
@Table(name = "genre")
public class Genre extends AbstractEntity {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue
    private Long id;
    @Column(name = "genre_name")
    private String genrename;
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }
}
