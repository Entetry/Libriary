package com.antonklintsevich.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubgenreDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private GenreDto genreDto;
    @JsonProperty
    private String subgenrename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubgenrename() {
        return subgenrename;
    }

    public void setSubgenrename(String subgenrename) {
        this.subgenrename = subgenrename;
    }

    public GenreDto getGenreDto() {
        return genreDto;
    }

    public void setGenreDto(GenreDto genreDto) {
        this.genreDto = genreDto;
    }
}
