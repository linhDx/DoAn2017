package altplus.amazing.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import altplus.amazing.R;
import altplus.amazing.view.widget.AmazingRecyclerView;

/**
 * Created by Nguyen Tien Hoang on 22/02/2016.
 */
abstract public class AmazingSimpleListItemFragment<T> extends AmazingBaseFragment {
    public AmazingRecyclerView rvListItem;

    protected List<T> mListItem;
    protected RecyclerView.Adapter mAdapter;

    private boolean isPendingSetAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_item;
    }

    @Override
    protected void initViews(View view) {
        rvListItem = (AmazingRecyclerView) view.findViewById(R.id.rv_list_item);
        rvListItem.setLayoutManager(getLayoutManager());
        if (isPendingSetAdapter) {
            rvListItem.setAdapter(mAdapter);
            isPendingSetAdapter = false;
        }
    }

    @Override
    protected void initData() {
        mListItem = getListItem();
        mAdapter = getAdapter();
        rvListItem.setAdapter(mAdapter);
    }

    protected List getListItem() {
        return new ArrayList<>();
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    public void update(int updateTag, Object object) {
        if (updateTag == AmazingBaseFragment.UPDATE_LIST_ITEM) {
            if (mListItem != null) {
                mListItem.clear();
                if (object != null) {
                    mListItem.addAll((List) object);
                }
                rvListItem.refreshList();
            } else {
                mListItem = (List) object;
                mAdapter = getAdapter();
                try {
                    rvListItem.setAdapter(mAdapter);
                } catch (Exception e) {
                    isPendingSetAdapter = true;
                }
            }
        }
    }

    public void updateListItem(List list) {
        update(AmazingBaseFragment.UPDATE_LIST_ITEM, list);
    }
}