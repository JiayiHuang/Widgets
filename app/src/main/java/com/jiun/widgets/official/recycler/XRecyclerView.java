package com.jiun.widgets.official.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author HJY
 * @date 2018/08/08/16:36
 * <pre>
 *     desc   : 仅当有少量的条目数量时，用来解决嵌套于可滚动布局内的条目展示不全问题，条目数量过多不宜使用，会影响性能。
 *     version: 当前版本号
 * </pre>
 */
public class XRecyclerView extends RecyclerView {
    private boolean isMaxItem;

    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMaxItem(boolean maxItem) {
        isMaxItem = maxItem;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isMaxItem) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
