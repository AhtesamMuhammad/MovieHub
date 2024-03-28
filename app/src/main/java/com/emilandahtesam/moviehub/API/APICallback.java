package com.emilandahtesam.moviehub.API;

import com.emilandahtesam.moviehub.Movie.Movie;

import java.util.List;

public interface APICallback {
    void onSuccess(List<Movie> movies);

    void onFailure(Throwable t);
}
