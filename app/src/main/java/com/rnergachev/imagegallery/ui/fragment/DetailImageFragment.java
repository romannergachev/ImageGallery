package com.rnergachev.imagegallery.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.rnergachev.imagegallery.ImageGalleryApplication;
import com.rnergachev.imagegallery.R;
import com.rnergachev.imagegallery.data.model.FlickrImageData;
import com.rnergachev.imagegallery.ui.presenter.ImageActivityPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Detail fragment for Image representation
 *
 * Created by rnergachev on 06/06/2017.
 */

public class DetailImageFragment extends Fragment {
    private final static String PAGER_POSITION = "PAGER_POSITION";

    public static DetailImageFragment getInstance(int position) {
        DetailImageFragment fragment = new DetailImageFragment();
        Bundle args = new Bundle();
        args.putInt(PAGER_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.meta_text_view) TextView title;
    @BindView(R.id.flickr_image_image_view) ImageView image;


    private FlickrImageData flickrImageData;

    @Inject
    ImageActivityPresenter imageActivityPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
            R.layout.fragment_image_detail, container, false);
        ButterKnife.bind(this, rootView);

        ImageGalleryApplication application = (ImageGalleryApplication) getActivity().getApplication();
        application.appComponent.inject(this);

        int position = getArguments().getInt(PAGER_POSITION);

        flickrImageData = imageActivityPresenter.getImage(position);

        //show data on screen
        Picasso.with(getContext()).load(flickrImageData.getMedia().getImageLink()).into(image);

        if(position == imageActivityPresenter.getCurrentPosition()) {
            image.setTransitionName(getString(R.string.transition_image));
            image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    image.getViewTreeObserver().removeOnPreDrawListener(this);
                    getActivity().startPostponedEnterTransition();
                    return true;
                }
            });
        }

        return rootView;
    }
}
