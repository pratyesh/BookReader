package book.com.bookreader;

import android.net.Uri;

import java.util.Set;

public class UrlUtils {

//<sizes canblog="1" canprint="1" candownload="1">
//  <size label="Square" width="75" height="75" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_s.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/sq/" media="photo" />
//  <size label="Large Square" width="150" height="150" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_q.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/q/" media="photo" />
//  <size label="Thumbnail" width="100" height="75" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_t.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/t/" media="photo" />
//  <size label="Small" width="240" height="180" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_m.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/s/" media="photo" />
//  <size label="Small 320" width="320" height="240" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_n.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/n/" media="photo" />
//  <size label="Medium" width="500" height="375" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/m/" media="photo" />
//  <size label="Medium 640" width="640" height="480" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_z.jpg?zz=1" url="http://www.flickr.com/photos/stewart/567229075/sizes/z/" media="photo" />
//  <size label="Medium 800" width="800" height="600" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_c.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/c/" media="photo" />
//  <size label="Large" width="1024" height="768" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_b.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/l/" media="photo" />
//  <size label="Original" width="2400" height="1800" source="http://farm2.staticflickr.com/1103/567229075_6dc09dc6da_o.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/o/" media="photo" />
//</sizes>

    protected static final String PAGE = "page";
    protected static final String SEARCH_KEY = "text";
    protected static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
            "api_key=641c87bd78e54920b01e9a5d8bb726d7&format=json&nojsoncallback=1&text=shirts&extras=url_q&page=1";

    public static String getUrl(String text, String page) {
        Uri uri = replaceUriParameter(Uri.parse(URL), SEARCH_KEY, text);
        uri = replaceUriParameter(uri, PAGE, page);
        return uri.toString();
    }

    protected static Uri replaceUriParameter(Uri uri, String key, String newValue) {
        final Set<String> params = uri.getQueryParameterNames();
        final Uri.Builder newUri = uri.buildUpon().clearQuery();
        for (String param : params) {
            newUri.appendQueryParameter(param,
                    param.equals(key) ? newValue : uri.getQueryParameter(param));
        }
        return newUri.build();
    }
}
