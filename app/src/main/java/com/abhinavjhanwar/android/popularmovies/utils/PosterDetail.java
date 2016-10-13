package com.abhinavjhanwar.android.popularmovies.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class PosterDetail implements Parcelable {

    private String poster_path;
    private String title;
    private String overview;
    private String release_date;
    private int[] genre_ids;
    private float vote_average;
    private int id;

    protected PosterDetail(Parcel in) {
        poster_path = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        genre_ids = in.createIntArray();
        vote_average = in.readFloat();
        id = in.readInt();
    }

    public static final Creator<PosterDetail> CREATOR = new Creator<PosterDetail>() {
        @Override
        public PosterDetail createFromParcel(Parcel in) {
            return new PosterDetail(in);
        }

        @Override
        public PosterDetail[] newArray(int size) {
            return new PosterDetail[size];
        }
    };

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

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeIntArray(genre_ids);
        dest.writeFloat(vote_average);
        dest.writeInt(id);
    }
}