package com.rnergachev.imagegallery.ui.presenter;

import com.rnergachev.imagegallery.data.model.FlickrImage;
import com.rnergachev.imagegallery.data.model.FlickrImageData;
import com.rnergachev.imagegallery.data.model.FlickrResponse;
import com.rnergachev.imagegallery.data.network.FlickrApi;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Single;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link ImageActivityPresenter}
 *
 * Created by rnergachev on 07/06/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ImageActivityPresenterUnitTest {
    private ImageActivityPresenter presenter;
    @Mock
    private FlickrApi flickrApi;


    @Before
    public void setUp() {
        ArrayList<FlickrImageData> flickrImageDatas = new ArrayList<>();
        flickrImageDatas.add(
            new FlickrImageData("title1", new FlickrImage(), "date", "description", "publsihed", "author", "tags")
        );
        flickrImageDatas.add(
            new FlickrImageData("title2", new FlickrImage(), "date", "description", "publsihed", "author", "tags")
        );
        FlickrResponse flickrResponse = new FlickrResponse(flickrImageDatas);

        when(flickrApi.getPublicPhotos()).thenReturn(Single.just(flickrResponse));
        presenter = new ImageActivityPresenter(flickrApi);
        presenter.setView(() -> {});
    }

    @Test
    public void presenter_fetchImages() throws Exception {
        presenter.fetchImages();
        assertEquals(presenter.getImage(0).getTitle(), "title1");
        assertEquals(presenter.getImage(1).getTitle(), "title2");
    }

    @Test
    public void presenter_getCount() throws Exception {
        presenter.fetchImages();
        assertEquals(presenter.getCount(), 2);
    }
}
