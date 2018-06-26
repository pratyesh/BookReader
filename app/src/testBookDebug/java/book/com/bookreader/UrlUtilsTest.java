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

@RunWith(PowerMockRunner.class)
@PrepareForTest({UrlUtils.class, Uri.class})
public class UrlUtilsTest {

    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
            "api_key=641c87bd78e54920b01e9a5d8bb726d7&format=json&nojsoncallback=1&text=shirts&extras=url_q&page=1";

    private Uri mockUri;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Uri.class);
        mockUri = PowerMockito.mock(Uri.class);
    }

    @Test
    public void getUrl() {
        PowerMockito.mockStatic(UrlUtils.class);
        Mockito.when(Uri.parse(URL)).thenReturn(mockUri);
        Mockito.when(UrlUtils.replaceUriParameter(mockUri, UrlUtils.SEARCH_KEY, "shirts")).thenReturn(mockUri);
        final String url = UrlUtils.getUrl("shirts", "1");
        Assert.assertEquals(url, null);
    }

    @Test
    public void replaceUriParameter() {

    }

}