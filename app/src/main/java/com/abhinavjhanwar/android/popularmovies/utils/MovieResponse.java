package com.abhinavjhanwar.android.popularmovies.utils;

public class MovieResponse {
    private com.abhinavjhanwar.android.popularmovies.utils.PosterDetail[] results;

    public MovieResponse(PosterDetail[] results) {
        this.results = results;
    }

    public com.abhinavjhanwar.android.popularmovies.utils.PosterDetail[] getResults() {
        return results;
    }
}