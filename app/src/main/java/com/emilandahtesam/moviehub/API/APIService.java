package com.emilandahtesam.moviehub.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIService {
    @GET("movie/popular")
    Call<APIResponse> getPopularMovies(
            @Header("Authorization") String bearerToken,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/top_rated")
    Call<APIResponse> getTopRatedMovies(
            @Header("Authorization") String bearerToken,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/upcoming")
    Call<APIResponse> getUpcomingMovies(
            @Header("Authorization") String bearerToken,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("search/movie")
    Call<APIResponse> searchMovies(
            @Header("Authorization") String bearerToken,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page
    );
}
