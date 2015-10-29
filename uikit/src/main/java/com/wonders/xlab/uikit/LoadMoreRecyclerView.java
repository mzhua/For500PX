package com.wonders.xlab.uikit;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by hua on 15/6/21.
 * 自定义的加入监听到达底部的StaggeredGridLayout
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {
        /**
         * @param itemsCount             The total number of items in this adapter.
         * @param maxLastVisiblePosition
         */
        void loadMore(int itemsCount, int maxLastVisiblePosition);
    }

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private int lastVisibleItemPosition;

    private boolean isLoadingMore = false;

    private boolean canLoadMore = true;

    private boolean isFirstScroll = true;

    private void init() {

        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LayoutManager layoutManager = recyclerView.getLayoutManager();

                int childCounts = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if ((childCounts > 0 && newState == SCROLL_STATE_IDLE &&
                        (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
                    isLoadingMore = true;
                    if (onLoadMoreListener != null && canLoadMore) {
                        isLoadingMore = false;
                        onLoadMoreListener.loadMore(getAdapter().getItemCount(), lastVisibleItemPosition);
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisibleItemPosition == -1) {
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                }

                if (isFirstScroll) {
                    isFirstScroll = false;
                    int total = linearLayoutManager.getItemCount();
                    if (lastVisibleItemPosition >= total) {
                        canLoadMore = false;
                    } else {
                        canLoadMore = true;
                    }
                }

            }
        });

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

}
