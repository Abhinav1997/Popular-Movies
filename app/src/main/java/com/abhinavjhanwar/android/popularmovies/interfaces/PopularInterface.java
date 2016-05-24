package com.abhinavjhanwar.android.popularmovies.interfaces;

import com.abhinavjhanwar.android.popularmovies.BuildConfig;
import com.abhinavjhanwar.android.popularmovies.utils.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PopularInterface {

    //Part of JSON URL for most popular category
    @GET("3/movie/popular?api_key=" + BuildConfig.MOVIE_DB_API_KEY)
    Call<JSONResponse> getJSON();
}