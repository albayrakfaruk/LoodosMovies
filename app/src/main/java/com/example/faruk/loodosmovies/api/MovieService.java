package com.example.faruk.loodosmovies.api;

import com.example.faruk.loodosmovies.models.Movie;
import com.example.faruk.loodosmovies.models.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by FARUK on 6.04.2018.
 */

public interface MovieService {
    @GET("/")
    Call<Movies> getSearchedMovies(@Query("s") String search, @Query("apikey") String apikey);

    @GET("/")
    Call<Movie> getMovieDetail(@Query("t") String byTitle, @Query("apikey") String apikey);
}
