package com.emilandahtesam.moviehub.API;

import com.emilandahtesam.moviehub.Movie.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
