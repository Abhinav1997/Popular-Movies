package com.abhinavjhanwar.android.popularmovies.provider;

import android.net.Uri;

import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;

public class FavoriteProvider extends SQLiteContentProviderImpl {

    private static final String AUTHORITY = "content://com.abhinavjhanwar.android.popularmovies/";
    private static final String TABLE_FAVORITE = "favorite";
    public static final String COL_ID = "mov_id";
    public static final String COL_TITLE = "title";
    public static final String COL_OVERVIEW = "overview";
    public static final String COL_POSTER_ID = "poster_id";
    public static final String COL_GENRE = "genre";
    public static final String COL_RATING = "rating";
    public static final String COL_RELEASE_DATE = "release_date";
    public static final String COL_POSTER_IMAGE = "poster_image";
    public static final String COL_GRID_IMAGE = "grid_image";

    public static final Uri FAVORITES = Uri.parse(AUTHORITY).buildUpon().appendPath(TABLE_FAVORITE).build();
}