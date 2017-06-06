package com.rnergachev.imagegallery;

import android.app.Application;

import com.rnergachev.imagegallery.di.ApplicationComponent;
import com.rnergachev.imagegallery.di.ApplicationModule;
import com.rnergachev.imagegallery.di.DaggerApplicationComponent;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Application class for dagger init and picasso config
 *
 * Created by rnergachev on 06/06/2017.
 */

public class ImageGalleryApplication extends Application {

    public ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);
    }

}
