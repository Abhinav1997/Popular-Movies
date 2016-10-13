package com.abhinavjhanwar.android.popularmovies.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailerDetail implements Parcelable {

    private String key;

    protected TrailerDetail(Parcel in) {
        key = in.readString();
    }

    public static final Creator<TrailerDetail> CREATOR = new Creator<TrailerDetail>() {
        @Override
        public TrailerDetail createFromParcel(Parcel in) {
            return new TrailerDetail(in);
        }

        @Override
        public TrailerDetail[] newArray(int size) {
            return new TrailerDetail[size];
        }
    };

    public String getKey() {
        return key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
    }
}