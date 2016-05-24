package com.abhinavjhanwar.android.popularmovies.interfaces;

import com.abhinavjhanwar.android.popularmovies.BuildConfig;
import com.abhinavjhanwar.android.popularmovies.utils.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RatedInterface {

    //Part of JSON URL for top rated category
    @GET("3/movie/top_rated?api_key=" + BuildConfig.MOVIE_DB_API_KEY)
    Call<JSONResponse> getJSON();
}