package com.antonklintsevich.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenreDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String genrename;

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
