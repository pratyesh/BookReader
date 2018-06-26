package book.com.bookreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class, ImageUtil.class})
public class MyAdapterTest {
    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&";

    private MyAdapter mMyAdapter;

    @Before
    public void setUp() {
        final List<ImageModel> dataModelList = new ArrayList<>();
        final ImageModel imageModel = new ImageModel();
        imageModel.setImageUrl(URL);
        dataModelList.add(imageModel);

        mMyAdapter = new MyAdapter(dataModelList);
        assertNotNull(mMyAdapter);
    }

    @Test
    public void updateList() {

        MyAdapter spyMyAdapter = PowerMockito.spy(mMyAdapter);

        PowerMockito.doNothing().when(spyMyAdapter).refreshOnItemUI(Mockito.anyInt(), Mockito.anyInt());

        final List<ImageModel> dataModelList = new ArrayList<>();
        final ImageModel imageModel = new ImageModel();
        imageModel.setImageUrl(URL);
        dataModelList.add(imageModel);

        spyMyAdapter.updateList(dataModelList);
        assertEquals(spyMyAdapter.getItemCount(), 2);
    }

    @Test
    public void onCreateViewHolder() throws Exception {
        PowerMockito.mockStatic(LayoutInflater.class);

        Context context = PowerMockito.mock(Context.class);
        ViewGroup parent = PowerMockito.mock(ViewGroup.class);
        PowerMockito.doReturn(context).when(parent).getContext();

        LayoutInflater mockLayoutInflater = PowerMockito.mock(LayoutInflater.class);
        PowerMockito.when(LayoutInflater.from(context)).thenReturn(mockLayoutInflater);

        View mockView = PowerMockito.mock(View.class);
        PowerMockito.doReturn(mockView).when(mockLayoutInflater).inflate(R.layout.item, parent, false);

        mMyAdapter.onCreateViewHolder(parent, R.layout.item);

        PowerMockito.verifyStatic(Mockito.times(1));
        LayoutInflater.from(context);

        Mockito.verify(mockLayoutInflater, Mockito.times(1)).inflate(R.layout.item, parent, false);
    }

    @Test
    public void getItemViewType() {
        assertEquals(mMyAdapter.getItemViewType(1), R.layout.item);
    }

    @Test
    public void onBindViewHolder() {
        PowerMockito.mockStatic(ImageUtil.class);
        ImageUtil mockImageUtil = PowerMockito.mock(ImageUtil.class);
        PowerMockito.when(ImageUtil.getInstance()).thenReturn(mockImageUtil);
        PowerMockito.doNothing().when(mockImageUtil).loadImage(Mockito.anyString(), Mockito.any(ImageView.class));

        MyAdapter.BaseViewHolder mockBaseViewHolder = PowerMockito.mock(MyAdapter.BaseViewHolder.class);
        mMyAdapter.onBindViewHolder(mockBaseViewHolder, 0);

        PowerMockito.verifyStatic(Mockito.times(1));
        ImageUtil.getInstance();

        Mockito.verify(mockImageUtil, Mockito.times(1)).loadImage(Mockito.anyString(), Mockito.any(ImageView.class));
    }
}