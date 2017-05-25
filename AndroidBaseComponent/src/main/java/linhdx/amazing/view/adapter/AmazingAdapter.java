package linhdx.amazing.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class AmazingAdapter<T,
        H extends AmazingAdapter.BaseHeaderViewHolder,
        F extends AmazingAdapter.BaseFooterViewHolder,
        I extends AmazingAdapter.BaseItemViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<T> mListItems;

    public AmazingAdapter(List<T> listItems) {
        this.mListItems = listItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(getHeaderLayoutId(), parent, false);
            return new BaseHeaderViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(getFooterLayoutId(), parent, false);
            return new BaseFooterViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false);
            return new BaseItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass().isAssignableFrom(BaseHeaderViewHolder.class)) {
            H headerViewHolder = (H) holder;
            setContentHeader(headerViewHolder);

        } else if (holder.getClass().isAssignableFrom(BaseFooterViewHolder.class)) {
            F footerViewHolder = (F) holder;
            setContentFooter(footerViewHolder);

        } else if (holder.getClass().isAssignableFrom(BaseItemViewHolder.class)) {
            if (position >= 0 && position < mListItems.size()) {
                T currentItem = mListItems.get(position);
                I itemViewHolder = (I) holder;
                setContentItem(itemViewHolder, currentItem);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getHeaderLayoutId() != 0 && isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (getFooterLayoutId() != 0 && isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == mListItems.size() + 1;
    }

    public class BaseFooterViewHolder extends RecyclerView.ViewHolder {
        public BaseFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BaseHeaderViewHolder extends RecyclerView.ViewHolder {
        public BaseHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BaseItemViewHolder extends RecyclerView.ViewHolder {
        public BaseItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected abstract int getHeaderLayoutId();

    protected abstract int getItemLayoutId();

    protected abstract int getFooterLayoutId();

    protected void setContentHeader(H headerViewHolder) {

    }

    protected void setContentFooter(F footerViewHolder) {

    }

    protected abstract void setContentItem(I itemViewHolder, T item);
}
