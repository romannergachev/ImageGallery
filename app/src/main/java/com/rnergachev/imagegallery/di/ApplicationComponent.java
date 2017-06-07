package com.rnergachev.imagegallery.di;


import com.rnergachev.imagegallery.ui.activity.ImageActivity;
import com.rnergachev.imagegallery.ui.adapter.ImagesOverviewAdapter;
import com.rnergachev.imagegallery.ui.fragment.DetailImageFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application component for dagger
 *
 * Created by rnergachev on 06/06/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    // inject context and retrofit service
    void inject(ImagesOverviewAdapter imagesOverviewAdapter);
    void inject(ImageActivity imageActivity);
    void inject(DetailImageFragment detailImageFragment);
}
