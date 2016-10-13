package com.abhinavjhanwar.android.popularmovies.api;

import com.abhinavjhanwar.android.popularmovies.utils.MovieResponse;
import com.abhinavjhanwar.android.popularmovies.utils.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    //Get movie lists according to category chosen
    @GET("/3/movie/{sort}")
    Call<MovieResponse> getMovies(@Path("sort") String sort, @Query("api_key") String api_key);

    //Get Trailers
    @GET("/3/movie/{id}/videos")
    Call<TrailerResponse> getTrailers(@Path("id") String sort, @Query("api_key") String api_key);
}