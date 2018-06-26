package book.com.bookreader;

import android.app.Application;

public class FlickrApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageUtil.init(this);
    }
}
