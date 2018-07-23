package book.com.bookreader;

import android.support.annotation.NonNull;

public class MainActivityPresenter implements MyAsyncTask.MyAsyncTaskListener {

    protected MainActivityView mMainActivityView;
    protected MyRxJavaCaller mMyAsyncTask;

    public MainActivityPresenter(@NonNull MainActivityView mainActivityView) {
        mMainActivityView = mainActivityView;
    }

    public void fetchAndLoadData(String query, int currentPage) {
        mMainActivityView.showProgressBar();
        final String completeUrl = UrlUtils.getUrl(query, String.valueOf(1 + currentPage));
        mMyAsyncTask = new MyRxJavaCaller(this);
        executeApiCall(completeUrl);
    }


    protected void executeApiCall(String completeUrl) {
        if (mMyAsyncTask != null) {
            mMyAsyncTask.execute(completeUrl);
        }
    }

    @Override
    public void onTaskCompleted(ResponseDetails dataModel) {
        if (mMainActivityView != null) {
            mMainActivityView.hideProgressBar();
        }
        if (dataModel == null || dataModel.getPhotos() == null) {
            return;
        }
        if (mMainActivityView != null) {
            mMainActivityView.setUiOnTaskCompleted(dataModel);
        }
    }

    public void onDestroy() {
        if (mMyAsyncTask != null) {
            mMyAsyncTask.cancelActive();
        }
        mMyAsyncTask = null;
        mMainActivityView = null;
    }
}
