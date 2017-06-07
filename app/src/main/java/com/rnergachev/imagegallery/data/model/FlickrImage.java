package com.rnergachev.imagegallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Flickr image model object
 *
 * Created by rnergachev on 06/06/2017.
 */

public class FlickrImage implements Parcelable {
    private String m;
    public String getImageLink() {
        return m;
    }

    public FlickrImage() {}

    public FlickrImage(String m) {
        this.m = m;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m);
    }

    public static final Parcelable.Creator<FlickrImage> CREATOR
        = new Parcelable.Creator<FlickrImage>() {
        public FlickrImage createFromParcel(Parcel in) {
            return new FlickrImage(in);
        }

        public FlickrImage[] newArray(int size) {
            return new FlickrImage[size];
        }
    };

    private FlickrImage(Parcel in) {
        this.m = in.readString();
    }
}
