package com.antonklintsevich.entity;

import java.util.Arrays;
import java.util.List;

public enum Genres {
    Horror, Drama, Action, Thriller, Unknown;
    private static final List<String> GENRE = Arrays.asList(
            Genres.Action.toString(),
            Genres.Drama.toString(),
            Genres.Horror.toString(),
            Genres.Thriller.toString(),
            Genres.Unknown.toString());

}