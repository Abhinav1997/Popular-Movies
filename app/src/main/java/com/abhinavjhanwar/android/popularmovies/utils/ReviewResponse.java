package com.abhinavjhanwar.android.popularmovies.utils;

public class ReviewResponse {
    private ReviewDetail[] results;

    public ReviewResponse(ReviewDetail[] results) {
        this.results = results;
    }

    public ReviewDetail[] getResults() {
        return results;
    }
}