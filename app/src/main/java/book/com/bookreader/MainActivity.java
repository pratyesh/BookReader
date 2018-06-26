package book.com.bookreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements MainActivityView, OnBottomReachedListener, SearchView.OnQueryTextListener {

    protected String query = "";
    static final String QUERY = "QUERY";

    protected RecyclerView mRecyclerView;

    protected MyAdapter mMyAdapter;
    protected ProgressBar progressBar;
    protected SearchView simpleSearchView;

    protected int currentPage;
    protected int totalPages;
    protected boolean reloadOnTextSearch;
    protected int scrolledPosition;

    protected MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityPresenter = new MainActivityPresenter(this);
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY);
        }

        simpleSearchView = findViewById(R.id.search);
        simpleSearchView.setOnQueryTextListener(this);

        mRecyclerView = findViewById(R.id.list_item);
        progressBar = findViewById(R.id.progressBar);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public boolean onQueryTextSubmit(String typedQuery) {
        query = typedQuery;
        currentPage = 0;
        reloadOnTextSearch = true;
        mMainActivityPresenter.fetchAndLoadData(query, currentPage);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(QUERY, query);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        simpleSearchView = null;
        if (mMainActivityPresenter != null) {
            mMainActivityPresenter.onDestroy();
        }
        mMainActivityPresenter = null;
        mRecyclerView = null;
        mMyAdapter = null;
        if (progressBar != null) {
            hideProgressBar();
        }
        progressBar = null;
        currentPage = 0;
        super.onDestroy();
    }

    @Override
    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUiOnTaskCompleted(ResponseDetails dataModel) {

        if (mMyAdapter == null || reloadOnTextSearch) {
            mMyAdapter = new MyAdapter(dataModel.getPhotos().getPhoto());
            mMyAdapter.setOnBottomReachedListener(this);
        } else {
            mMyAdapter.updateList(dataModel.getPhotos().getPhoto());
            mRecyclerView.scrollToPosition(scrolledPosition);
        }

        reloadOnTextSearch = false;
        currentPage = dataModel.getPhotos().getPage();
        totalPages = dataModel.getPhotos().getPages();
        mRecyclerView.setAdapter(mMyAdapter);
    }

    @Override
    public void onBottomReached(int position) {
        if (currentPage == totalPages) {
            return;
        }
        scrolledPosition = position;
        mMainActivityPresenter.fetchAndLoadData(query, currentPage);
    }
}
