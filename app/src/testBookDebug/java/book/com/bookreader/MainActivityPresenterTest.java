package book.com.bookreader;

import android.net.Uri;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UrlUtils.class, Uri.class})
public class MainActivityPresenterTest {
    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&";
    private MainActivityPresenter mMainActivityPresenter;
    private MainActivityView mockMainActivityView;

    @Before
    public void setUp() {
        mockMainActivityView = PowerMockito.mock(MainActivityView.class);
        mMainActivityPresenter = new MainActivityPresenter(mockMainActivityView);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fetchAndLoadData() {
        PowerMockito.mockStatic(UrlUtils.class);
        PowerMockito.mockStatic(Uri.class);
        MainActivityPresenter spyMainActivityPresenter = PowerMockito.spy(mMainActivityPresenter);
        PowerMockito.doNothing().when(spyMainActivityPresenter).executeApiCall();

        spyMainActivityPresenter.fetchAndLoadData(Mockito.anyString(), Mockito.anyInt());
        Mockito.verify(mockMainActivityView, Mockito.times(1)).showProgressBar();
        PowerMockito.verifyStatic(Mockito.times(1));
        UrlUtils.getUrl(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(spyMainActivityPresenter, Mockito.times(1)).executeApiCall();
    }

    @Test
    public void onTaskCompleted() {
        ResponseDetails mResponseDetails = new ResponseDetails();
        Photo mPhoto = new Photo();
        final List<ImageModel> dataModelList = new ArrayList<>();
        final ImageModel imageModel = new ImageModel();
        imageModel.setImageUrl(URL);
        dataModelList.add(imageModel);
        mPhoto.setPhoto(dataModelList);
        mResponseDetails.setPhotos(mPhoto);

        mMainActivityPresenter.onTaskCompleted(mResponseDetails);
        Mockito.verify(mockMainActivityView, Mockito.times(1)).hideProgressBar();
        Mockito.verify(mockMainActivityView, Mockito.times(1)).setUiOnTaskCompleted(mResponseDetails);
    }

    @Test
    public void onDestroy() {
        mMainActivityPresenter.onDestroy();
        Assert.assertNull(mMainActivityPresenter.mMyAsyncTask);
        Assert.assertNull(mMainActivityPresenter.mMainActivityView);
    }
}