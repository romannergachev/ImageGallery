package com.rnergachev.imagegallery.data.model;

/**
 * Flickr image model object
 *
 * Created by rnergachev on 06/06/2017.
 */

class FlickrImage {
    private String m;
    public String getImageLink() {
        return m;
    }

    public FlickrImage() {}

    public FlickrImage(String m) {
        this.m = m;
    }
}
