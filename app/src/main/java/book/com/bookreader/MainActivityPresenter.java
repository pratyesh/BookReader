package book.com.bookreader;

import android.support.annotation.NonNull;

public class MainActivityPresenter implements MyAsyncTask.MyAsyncTaskListener {

    protected MainActivityView mMainActivityView;
    protected MyAsyncTask mMyAsyncTask;

    public MainActivityPresenter(@NonNull MainActivityView mainActivityView) {
        mMainActivityView = mainActivityView;
    }

    public void fetchAndLoadData(String query, int currentPage) {
        mMainActivityView.showProgressBar();
        final String completeUrl = UrlUtils.getUrl(query, String.valueOf(1 + currentPage));
        mMyAsyncTask = new MyAsyncTask(completeUrl, this);
        executeApiCall();
    }


    protected void executeApiCall() {
        if (mMyAsyncTask != null) {
            mMyAsyncTask.execute();
        }
    }

    @Override
    public void onTaskCompleted(ResponseDetails dataModel) {
        mMainActivityView.hideProgressBar();
        if (dataModel == null || dataModel.getPhotos() == null) {
            return;
        }
        mMainActivityView.setUiOnTaskCompleted(dataModel);
    }

    public void onDestroy() {
        if (mMyAsyncTask != null) {
            mMyAsyncTask.cancelActive();
        }
        mMyAsyncTask = null;
        mMainActivityView = null;
    }
}
