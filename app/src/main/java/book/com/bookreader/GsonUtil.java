package book.com.bookreader;

import android.util.Log;

import com.google.gson.Gson;

public class GsonUtil {

    private static final String TAG = GsonUtil.class.getName();

    private GsonUtil() {
        // hide constructor
    }

    public static <T> T fromJson(String json, Class<T> classOfT, String requestString) {
        return fromJson(json, true, classOfT, requestString);
    }

    public static <T> T fromJson(String json, boolean logException, Class<T> classOfT, String requestString) {
        T retObj;
        final Gson gson = new Gson();

        try {
            retObj = gson.fromJson(json, classOfT);
        } catch (Exception e) {
            if (logException) {
                Log.e(TAG + " : ERR", e.getMessage());
                Log.e(TAG + " : REQ", requestString);
                Log.e(TAG + " : CLA", classOfT.getName());
                Log.e(TAG + " : JSON", json);
            }
            retObj = null;
        }
        return retObj;
    }
}
