package com.jiun.widgets.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jiun.widgets.R;

/**
 * <pre>
 *     @author Jiun
 *     @date  :2018/12/15/17:32
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class XViewPager extends ViewPager {

    private boolean isPagingEnabled = true;
    private float ratio = 0;

    public XViewPager(Context context) {
        this(context, null);
    }

    public XViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XViewPager);
        ratio = typedArray.getFloat(R.styleable.XViewPager_xvp_ratio, 0f);
        typedArray.recycle();
    }

    /**
     * 此方法和{@link XViewPager#onInterceptTouchEvent(MotionEvent)} 的try…catch 用于解决下面问题：
     * 图片缩放时java.lang.IllegalArgumentException: pointerIndex out of range解决方案
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return this.isPagingEnabled && super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return this.isPagingEnabled && super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

    public void setRatio(float ration) {
        this.ratio = ration;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 解决 ViewPager wrap_content 无效
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }

        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 解决 ViewPager wrap_content 无效

        if (ratio > 0) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = (int) (1 * 1.0f / ratio * widthSize);
            setMeasuredDimension(widthSize, heightSize);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}