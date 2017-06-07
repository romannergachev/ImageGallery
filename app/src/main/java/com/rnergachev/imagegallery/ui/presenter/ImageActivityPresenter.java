package com.rnergachev.imagegallery.ui.presenter;

import android.util.Log;

import com.rnergachev.imagegallery.data.model.FlickrImageData;
import com.rnergachev.imagegallery.data.network.FlickrApi;
import com.rnergachev.imagegallery.ui.view.ImageActivityView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * ImageActivity presenter
 *
 * Created by rnergachev on 06/06/2017.
 */

@Singleton
public class ImageActivityPresenter {

    private int currentPosition;
    ImageActivityView view;
    private List<FlickrImageData> flickrImageDataList;
    FlickrApi flickrApi;

    @Inject
    public ImageActivityPresenter(FlickrApi flickrApi) {
        this.flickrApi = flickrApi;
        flickrImageDataList = new ArrayList<>();
    }



    /**
     * Show error
     *
     * @param  exception  exception
     */
    private void showError(Throwable exception) {
        Log.d(this.getClass().getName(), exception.getMessage());
    }

    public void setView(ImageActivityView view) {
        this.view = view;
    }

    public void setImageDetails(ArrayList<FlickrImageData> flickrImageDataList, int index) {
        this.flickrImageDataList.clear();
        this.currentPosition = index;
        this.flickrImageDataList.addAll(flickrImageDataList);
    }

    public void fetchImages() {
        flickrApi.getPublicPhotos().subscribe(
            response -> {
                flickrImageDataList.addAll(response.getPhotos());
                view.notifyDataChanged();
            },
            this::showError
        );
    }

    public int getCount() {
        return flickrImageDataList.size();
    }

    public FlickrImageData getImage(int position) {
        return flickrImageDataList.get(position);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
