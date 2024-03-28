package com.emilandahtesam.moviehub.API;

import androidx.annotation.NonNull;

import com.emilandahtesam.moviehub.Movie.Movie;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String[] REQUEST_TYPES = {"popular", "top_rated", "upcoming"};
    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static String getBearerToken() {
        String API_KEY = "YOUR_API_KEY";
        return "Bearer " + API_KEY;
    }

    public static void getMoviesFromAPI(APICallback callback, String requestType, String language) {
        APIService service = getRetrofitInstance().create(APIService.class);
        Call<APIResponse> call;

        if (requestType.equals(REQUEST_TYPES[1])) {
            call = service.getTopRatedMovies(getBearerToken(), language, 1);
        } else if (requestType.equals(REQUEST_TYPES[2])) {
            call = service.getUpcomingMovies(getBearerToken(), language, 1);
        } else {
            call = service.getPopularMovies(getBearerToken(), language, 1);
        }

        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    APIResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<Movie> movies = responseBody.getMovies();
                        callback.onSuccess(movies);
                    } else {
                        callback.onFailure(new Throwable("Error: Response body is null."));
                    }
                } else {
                    try {
                        callback.onFailure(new Throwable(String.format("Error: %s", Objects.requireNonNull(response.errorBody()).string())));
                    } catch (IOException e) {
                        callback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public static void getMoviesFromAPIUsingQuery(APICallback callback, String query, String language) {
        APIService service = getRetrofitInstance().create(APIService.class);
        Call<APIResponse> call;

        call = service.searchMovies(getBearerToken(), language, query, 1);

        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    APIResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<Movie> movies = responseBody.getMovies();
                        callback.onSuccess(movies);
                    } else {
                        callback.onFailure(new Throwable("Error: Response body is null."));
                    }
                } else {
                    try {
                        callback.onFailure(new Throwable(String.format("Error: %s", Objects.requireNonNull(response.errorBody()).string())));
                    } catch (IOException e) {
                        callback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
