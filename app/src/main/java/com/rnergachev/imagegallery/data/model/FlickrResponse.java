package com.rnergachev.imagegallery.data.model;

import java.util.List;

/**
 * Model class for Flickr response
 *
 * Created by roman on 06.06.2017.
 */

public class FlickrResponse {
    private List<FlickrImageData> items;

    public FlickrResponse() {}

    public FlickrResponse(List<FlickrImageData> items) {
        this.items = items;
    }

    public List<FlickrImageData> getPhotos() {
        return items;
    }
}
