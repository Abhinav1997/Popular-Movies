package com.abhinavjhanwar.android.popularmovies.utils;

public class TrailerResponse {
    private com.abhinavjhanwar.android.popularmovies.utils.TrailerDetail[] results;

    public TrailerResponse(TrailerDetail[] results) {
        this.results = results;
    }

    public com.abhinavjhanwar.android.popularmovies.utils.TrailerDetail[] getResults() {
        return results;
    }
}