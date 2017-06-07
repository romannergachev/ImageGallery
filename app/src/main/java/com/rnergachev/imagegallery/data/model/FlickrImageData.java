package com.rnergachev.imagegallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Flickr image data model
 *
 * Created by rnergachev on 06/06/2017.
 */

public class FlickrImageData implements Parcelable{
    private FlickrImage media;
    private String title;
    @SerializedName("date_taken")
    private String date;
    private String description;
    private String published;
    private String author;
    @SerializedName("author_id")
    private String tags;

    public FlickrImageData() {

    }

    public FlickrImageData(String title, FlickrImage media, String date, String description, String published, String author, String tags) {
        this.title = title;
        this.media = media;
        this.date = date;
        this.description = description;
        this.published =  published;
        this.author = author;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public FlickrImage getMedia() {
        return media;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTags() {
        return tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(media, flags);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(published);
        dest.writeString(author);
        dest.writeString(tags);
    }

    private FlickrImageData(Parcel in) {
        this.media = in.readParcelable(FlickrImage.class.getClassLoader());
        this.title = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.published = in.readString();
        this.author = in.readString();
        this.tags = in.readString();
    }

    public static final Parcelable.Creator<FlickrImageData> CREATOR
        = new Parcelable.Creator<FlickrImageData>() {
        public FlickrImageData createFromParcel(Parcel in) {
            return new FlickrImageData(in);
        }

        public FlickrImageData[] newArray(int size) {
            return new FlickrImageData[size];
        }
    };


}
