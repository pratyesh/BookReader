package book.com.bookreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.BaseViewHolder> {

    private List<ImageModel> mDataModelList;
    private OnBottomReachedListener onBottomReachedListener;

    public MyAdapter(List<ImageModel> dataModelList) {
        mDataModelList = dataModelList;
    }

    public void updateList(@NonNull List<ImageModel> dataModelList) {
        final int startPosition = getItemCount();
        final int count = dataModelList != null && !dataModelList.isEmpty() ? dataModelList.size() : 0;
        mDataModelList.addAll(dataModelList);
        refreshOnItemUI(startPosition, count);
    }

    protected void refreshOnItemUI(int startPosition, int count) {
        notifyItemRangeInserted(startPosition, count);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        final ImageModel dataModel = getItem(position);
        if (dataModel != null) {
            ImageUtil.getInstance().loadImage(dataModel.getImageUrl(), holder.image);
        }

        if (position == getItemCount() - 1 && onBottomReachedListener != null) {
            onBottomReachedListener.onBottomReached(position);
        }
    }

    public ImageModel getItem(int position) {
        return mDataModelList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataModelList != null && !mDataModelList.isEmpty() ? mDataModelList.size() : 0;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    protected class BaseViewHolder extends RecyclerView.ViewHolder {

        protected ImageView image;

        public BaseViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}