package com.tosmart.tmdb.content;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/10/29
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacingLeft;
    private int spacingTop;

    public SpacingItemDecoration(int spanCount, int spacingLeft, int spacingTop) {
        this.spanCount = spanCount;
        this.spacingLeft = spacingLeft;
        this.spacingTop = spacingTop;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (spanCount != 0) {
            int column = position % spanCount;
            outRect.left = column * spacingLeft / spanCount;
            if (position >= spanCount) {
                outRect.top = spacingTop;
            }
        } else {
            if (position >= 1) {
                outRect.left = spacingLeft;
            }
        }
    }
}
