package com.abhinavjhanwar.android.popularmovies.utils;

public class PosterDetail {

    private String poster_path;
    private String title;
    private String overview;
    private String release_date;
    private int[] genre_ids;
    private float vote_average;

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public float getVote_average() {
        return vote_average;
    }
}