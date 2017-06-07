package com.rnergachev.imagegallery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rnergachev.imagegallery.ImageGalleryApplication;
import com.rnergachev.imagegallery.R;
import com.rnergachev.imagegallery.data.model.FlickrImageData;
import com.rnergachev.imagegallery.ui.fragment.DetailImageFragment;
import com.rnergachev.imagegallery.ui.presenter.ImageActivityPresenter;
import com.rnergachev.imagegallery.ui.view.ImageActivityView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Detail Activity for the selected flickr Image
 *
 * Created by rnergachev on 06/06/2017.
 */

public class ImageActivity extends AppCompatActivity implements ImageActivityView {
    private PagerAdapter pagerAdapter;


    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject
    ImageActivityPresenter imageActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        ButterKnife.bind(this);

        ImageGalleryApplication application = (ImageGalleryApplication) getApplication();
        application.appComponent.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        ArrayList<FlickrImageData> flickrImageDataList = getIntent().getParcelableArrayListExtra(getString(R.string.activity_image_detail));
        int index = getIntent().getIntExtra(getString(R.string.image_position), 0);


        imageActivityPresenter.setImageDetails(flickrImageDataList, index);
        imageActivityPresenter.setView(this);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(index);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (imageActivityPresenter.getCount() - position < 4) {
                    imageActivityPresenter.fetchImages();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        postponeEnterTransition();

        imageActivityPresenter.fetchImages();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @Override
    public void notifyDataChanged() {
        pagerAdapter.notifyDataSetChanged();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailImageFragment.getInstance(position);

        }

        @Override
        public int getCount() {
            return imageActivityPresenter.getCount();
        }
    }

}
