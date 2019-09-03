package book.com.bookreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.lang.ref.WeakReference;

public class ImageUtil {

    private static final String TAG = ImageUtil.class.getSimpleName();
    private static ImageUtil instance;
    private final WeakReference<Context> context;
    private boolean singletonBuilt;
    private static final int PICASSO_DISK_CACHE_SIZE = 1024 * 1024 * 30; // Size in bytes (30 MB)

    public ImageUtil(final Context context) {
        this.context = new WeakReference<>(context);
        singletonBuilt = false;
    }

    public static void init(final Context context) {
        if (context != null) {
            instance = new ImageUtil(context);
        }
    }

    public static ImageUtil getInstance() {
        if (instance == null) {
            Log.e(TAG, "ImageUtil instance is null, Please initialize 'instance' first");
        }
        return instance;
    }


    private Picasso getPicasso() {
        final Context ctx = context.get();
        if (!singletonBuilt) {
            if (ctx != null) {
                final Picasso.Builder builder = new Picasso.Builder(ctx);
                setUpDownloader(builder, ctx);
                try {
                    Picasso.setSingletonInstance(builder.build());
                } catch (IllegalStateException e) {
                    Log.e(TAG, "Singleton instance already exists.");
                }
                singletonBuilt = true;
            }
        }
        return Picasso.get();
    }

    private void setUpDownloader(@NonNull final Picasso.Builder builder, @NonNull final Context context) {
        // Use OkHttp as downloader
        final Downloader downloader = new OkHttp3Downloader(context, PICASSO_DISK_CACHE_SIZE);
        // Create memory cache
        final Cache memoryCache = new LruCache(PICASSO_DISK_CACHE_SIZE);
        builder.downloader(downloader).memoryCache(memoryCache);
    }

    public void loadImage(String url, ImageView imageView) {
        if (context.get() != null && imageView != null && !TextUtils.isEmpty(url)) {

            final RequestCreator urlRequest = getPicasso().load(url);
            try {
                urlRequest.into(imageView);
            } catch (OutOfMemoryError e) {
                Log.e(TAG, "OutOfMemoryError", e);
            }
        }
    }
}