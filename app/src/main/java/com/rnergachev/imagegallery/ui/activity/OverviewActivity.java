package com.rnergachev.imagegallery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rnergachev.imagegallery.R;
import com.rnergachev.imagegallery.data.model.FlickrImageData;
import com.rnergachev.imagegallery.ui.adapter.ImagesOverviewAdapter;
import com.rnergachev.imagegallery.ui.listener.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity containing photos list
 *
 * Created by rnergachev on 06/06/2017.
 */

public class OverviewActivity
    extends AppCompatActivity
    implements ImagesOverviewAdapter.ImagesOverviewAdapterHandler
{
    @BindView(R.id.flickr_images_list) RecyclerView overviewRecyclerView;
    @BindView(R.id.flickr_image_error_message_display) TextView errorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar loadingIndicator;
    @BindView(R.id.my_toolbar) Toolbar toolbar;
    private EndlessRecyclerViewScrollListener scrollListener;
    private GridLayoutManager gridLayoutManager;
    private Integer currentPosition;
    private ImagesOverviewAdapter imagesOverviewAdapter;

    private static final int NUMBER_OF_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        ButterKnife.bind(this);

        currentPosition = null;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        setAdapter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = gridLayoutManager.findFirstVisibleItemPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gridLayoutManager!= null && currentPosition != null) {
            gridLayoutManager.scrollToPosition(currentPosition);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.extra_position), gridLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt(getString(R.string.extra_position));
    }

    @Override
    public void onClick(ArrayList<FlickrImageData> flickrImageData, int index, View view) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putParcelableArrayListExtra(getString(R.string.activity_image_detail), flickrImageData);
        intent.putExtra(getString(R.string.image_position), index);

        ActivityOptionsCompat options = ActivityOptionsCompat.
            makeSceneTransitionAnimation(this, view, getString(R.string.transition_image));
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onError(Throwable exception) {
        onFetchingEnded();
        Log.d(getClass().getName(), exception.getMessage());
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismissError() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFetchingStarted() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchingEnded() {
        loadingIndicator.setVisibility(View.INVISIBLE);
    }

    public void setAdapter() {
        imagesOverviewAdapter = new ImagesOverviewAdapter(this);
        gridLayoutManager   = new GridLayoutManager(this, NUMBER_OF_COLUMNS);

        overviewRecyclerView.setLayoutManager(gridLayoutManager);
        overviewRecyclerView.setAdapter(imagesOverviewAdapter);

        //add view scroll listener to check the end of the list and fetch new data
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                imagesOverviewAdapter.fetchImages();
            }
        };
        overviewRecyclerView.addOnScrollListener(scrollListener);


        if (currentPosition != null) {
            gridLayoutManager.scrollToPosition(currentPosition);
        } else {
            imagesOverviewAdapter.fetchImages();
        }
    }
}
