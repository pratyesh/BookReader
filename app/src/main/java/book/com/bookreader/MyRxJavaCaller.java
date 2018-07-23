package book.com.bookreader;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyRxJavaCaller {
    private static final String TAG = MyRxJavaCaller.class.getSimpleName();
    private MyAsyncTask.MyAsyncTaskListener mMyAsyncTaskListener;

    private Call call;

    public MyRxJavaCaller(@NonNull MyAsyncTask.MyAsyncTaskListener listener) {
        this.mMyAsyncTaskListener = listener;
    }

    public void execute(final String apiUrl) {
        Observable.just(apiUrl)
                .map(new Function<String, ResponseDetails>() {
                    @Override
                    public ResponseDetails apply(String url) {

                        ResponseDetails responseDetails = null;

                        final OkHttpClient client = new OkHttpClient();
                        Log.d(TAG + " : url", url + "");
                        final Request httpRequest = new Request.Builder().url(url).build();

                        call = client.newCall(httpRequest);
                        try {
                            final Response httpResponse = call.execute();

                            final int status = httpResponse.code();
                            final String responseJson = httpResponse.body().string();
                            Log.d(TAG + " : status", status + "");
                            Log.d(TAG + " : responseJson", responseJson);

                            responseDetails = GsonUtil.fromJson(responseJson, ResponseDetails.class, url);
                        } catch (IOException e) {
                            Log.e(TAG + " : ERR", e.getMessage());
                        }

                        return responseDetails;
                    }
                })

                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<ResponseDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG + " : Observer", "onSubscribe");
                    }

                    @Override
                    public void onNext(ResponseDetails responseDetails) {
                        mMyAsyncTaskListener.onTaskCompleted(responseDetails);
                        Log.d(TAG + " : Observer", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG + " : Observer", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG + " : Observer", "onComplete");
                    }
                })
        ;
    }

    public void cancelActive() {
        if (call != null) {
            call.cancel();
        }
    }
}
