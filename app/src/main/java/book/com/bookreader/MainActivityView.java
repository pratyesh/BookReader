package book.com.bookreader;

public interface MainActivityView {

    void setUiOnTaskCompleted(ResponseDetails dataModel);

    void showProgressBar();

    void hideProgressBar();
}
