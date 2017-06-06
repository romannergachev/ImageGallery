package com.rnergachev.imagegallery.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Flickr image data model
 *
 * Created by rnergachev on 06/06/2017.
 */

public class FlickrImageData {
    private String title;
    private FlickrImage media;
    @SerializedName("date_taken")
    private String date;
    private String description;
    private String published;
    private String author;
    @SerializedName("author_id")
    private String authorId;
    private String tags;

    public FlickrImageData() {

    }

    public FlickrImageData(String title, FlickrImage media, String date, String description, String published, String author, String authorId, String tags) {
        this.title = title;
        this.media = media;
        this.date = date;
        this.description = description;
        this.published =  published;
        this.author = author;
        this.authorId = authorId;
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

    public String getAuthorId() {
        return authorId;
    }

    public String getTags() {
        return tags;
    }
}
