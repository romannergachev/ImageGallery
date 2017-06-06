package com.rnergachev.imagegallery.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rnergachev.imagegallery.ImageGalleryApplication;
import com.rnergachev.imagegallery.R;
import com.rnergachev.imagegallery.data.model.FlickrImageData;
import com.rnergachev.imagegallery.data.network.FlickrApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class flickr images
 *
 * Created by roman on 06.06.2017.
 */

public class ImagesOverviewAdapter extends RecyclerView.Adapter<ImagesOverviewAdapter.ImagesOverviewAdapterViewHolder> {
    private List<FlickrImageData> flickrImageDataList;
    private Context context;
    private final ImagesOverviewAdapterHandler handler;
    @Inject
    FlickrApi flickrApi;

    public ImagesOverviewAdapter(Activity activity, ImagesOverviewAdapterHandler handler) {
        this.context = activity;
        this.handler = handler;
        flickrImageDataList = new ArrayList<>();
        ImageGalleryApplication application = (ImageGalleryApplication) activity.getApplication();
        application.appComponent.inject(this);
    }

    public interface ImagesOverviewAdapterHandler {
        /**
         * Performs image selection
         *
         * @param flickrImageData that has been selected
         * @param view
         */
        void onClick(FlickrImageData flickrImageData, View view);
        /**
         * Returns the error
         *
         * @param  exception error
         */
        void onError(Throwable exception);
        /**
         * Shows progress bar
         */
        void onFetchingStarted();
        /**
         * Hides progress bar
         */
        void onFetchingEnded();
        /**
         * Dismisses error
         */
        void onDismissError();
    }

    /**
     * ViewHolder class in order to hold and reuse previously inflated views
     */
    class ImagesOverviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.flickr_image_thumbnail_image_view) ImageView imageThumbnail;

        ImagesOverviewAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            FlickrImageData flickrImageData = flickrImageDataList.get(adapterPosition);
            handler.onClick(flickrImageData, v.findViewById(R.id.flickr_image_thumbnail_image_view));
        }
    }

    @Override
    public ImagesOverviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_list_item, parent, false);
        return new ImagesOverviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesOverviewAdapterViewHolder holder, int position) {
        String tvShowUrl = flickrImageDataList.get(position).getMedia().getImageLink();
        Picasso.with(context).load(context.getString(R.string.image_base_url) + tvShowUrl).into(holder.imageThumbnail);
    }

    @Override
    public int getItemCount() {
        if (null == flickrImageDataList) return 0;
        return flickrImageDataList.size();
    }

    /**
     * Fetches new portion of images from flickr
     *
     */
    public void fetchTVShows() {
        handler.onDismissError();
        handler.onFetchingStarted();

        flickrApi.getPublicPhotos().subscribe(
            response -> {
                flickrImageDataList.addAll(response.getPhotos());
                this.notifyDataSetChanged();
                handler.onFetchingEnded();
            },
            handler::onError
        );
    }
}
