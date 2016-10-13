package com.abhinavjhanwar.android.popularmovies.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewDetail implements Parcelable {

    private String author;
    private String content;

    protected ReviewDetail(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<ReviewDetail> CREATOR = new Creator<ReviewDetail>() {
        @Override
        public ReviewDetail createFromParcel(Parcel in) {
            return new ReviewDetail(in);
        }

        @Override
        public ReviewDetail[] newArray(int size) {
            return new ReviewDetail[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }
}