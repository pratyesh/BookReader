package book.com.bookreader;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UrlUtils.class})
public class MainActivityTest {
    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&";
    private MainActivity spyMainActivity;

    @Before
    public void setUp() {
        spyMainActivity = PowerMockito.spy(new MainActivity());
        assertNotNull(spyMainActivity);
        spyMainActivity.mMainActivityPresenter = PowerMockito.mock(MainActivityPresenter.class);
        spyMainActivity.mRecyclerView = PowerMockito.mock(RecyclerView.class);
        spyMainActivity.mMyAdapter = PowerMockito.mock(MyAdapter.class);
        spyMainActivity.progressBar = PowerMockito.mock(ProgressBar.class);
        spyMainActivity.simpleSearchView = PowerMockito.mock(SearchView.class);
    }

    @Test
    public void setUiOnTaskCompleted() {
        ResponseDetails mResponseDetails = new ResponseDetails();
        Photo mPhoto = new Photo();
        final List<ImageModel> dataModelList = new ArrayList<>();
        final ImageModel imageModel = new ImageModel();
        imageModel.setImageUrl(URL);
        dataModelList.add(imageModel);
        mPhoto.setPhoto(dataModelList);
        mResponseDetails.setPhotos(mPhoto);

        spyMainActivity.mMyAdapter = PowerMockito.mock(MyAdapter.class);
        spyMainActivity.setUiOnTaskCompleted(mResponseDetails);
        Mockito.verify(spyMainActivity.mMyAdapter, Mockito.times(1)).updateList(dataModelList);
        Mockito.verify(spyMainActivity.mRecyclerView, Mockito.times(1)).scrollToPosition(Mockito.anyInt());
        Mockito.verify(spyMainActivity.mRecyclerView, Mockito.times(1)).setAdapter(spyMainActivity.mMyAdapter);
    }

    @Test
    public void onBottomReached() {
        spyMainActivity.currentPage = 2;
        spyMainActivity.totalPages = 10;
        PowerMockito.doNothing().when(spyMainActivity.mMainActivityPresenter).fetchAndLoadData(Mockito.anyString(), Mockito.anyInt());
        spyMainActivity.onBottomReached(10);
        Mockito.verify(spyMainActivity.mMainActivityPresenter, Mockito.times(1)).fetchAndLoadData(Mockito.anyString(), Mockito.anyInt());
    }
}