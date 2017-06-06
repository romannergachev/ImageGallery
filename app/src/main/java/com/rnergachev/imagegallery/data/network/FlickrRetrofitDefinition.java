package com.rnergachev.imagegallery.data.network;

import com.rnergachev.imagegallery.data.model.FlickrResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Retrofit definition for Flickr
 * <p>
 * Created by rnergachev on 06/06/2017.
 */

public interface FlickrRetrofitDefinition {

    /**
     * Returns public photos as a Single object
     *
     * @return        {@link Single<FlickrResponse>}
     */
    @GET("photos_public.gne?format=json&nojsoncallback=1")
    Single<FlickrResponse> getPublicPhotos();

}
