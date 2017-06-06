package com.rnergachev.imagegallery.data.network;

import com.rnergachev.imagegallery.data.model.FlickrResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * API for calling FlickrRetrofitDefinition
 *
 * Created by rnergachev on 06/06/2017.
 */

@Singleton
public class FlickrApi {
    private FlickrRetrofitDefinition FlickrRetrofitDefinition;

    @Inject
    public FlickrApi(FlickrRetrofitDefinition FlickrRetrofitDefinition) {
        this.FlickrRetrofitDefinition = FlickrRetrofitDefinition;
    }

    /**
     * Returns public photos as a Single object
     *
     * @return        {@link Single< FlickrResponse >}
     */
    public Single<FlickrResponse> getPublicPhotos() {
        return applySchedulers(FlickrRetrofitDefinition.getPublicPhotos());
    }

    /**
     * Adds schedulers to the call
     *
     * @param  single object to add schedulers
     * @return        {@link Single<T>}
     */
    private <T> Single<T> applySchedulers(Single<T> single) {
        return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
