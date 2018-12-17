package com.jiun.widgets.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.anban.policemonitor.R;

/**
 * <pre>
 *     @author Jiun
 *     @date  :2018/12/15/16:20
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class ClearableEditText extends AppCompatEditText {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;
    private boolean mIsDrawableRightAlwaysShow;

    public ClearableEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearableEditText);
        mClearDrawable = typedArray.getDrawable(R.styleable.ClearableEditText_cet_drawableRight);
        mIsDrawableRightAlwaysShow = typedArray.getBoolean(R.styleable.ClearableEditText_cet_showAlways, false);
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.icon_close_gray);
        }
        typedArray.recycle();
        if (mIsDrawableRightAlwaysShow) {
            setClearIconVisible(true);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    if (onCompoundDrawableClickListener != null) {
                        onCompoundDrawableClickListener.onDrawableRightClick();
                    } else {
                        setText("");
                    }
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(
                getCompoundDrawables()[DRAWABLE_LEFT],
                getCompoundDrawables()[DRAWABLE_TOP],
                mIsDrawableRightAlwaysShow ? mClearDrawable : (visible ? mClearDrawable : null),
                getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }

    public void setDrawableRight(@DrawableRes int drawableRightResId) {
        mClearDrawable = getResources().getDrawable(drawableRightResId);
        setClearIconVisible(true);
    }

    private OnCompoundDrawableClickListener onCompoundDrawableClickListener;

    public void setOnCompoundDrawableClickListener(OnCompoundDrawableClickListener listener) {
        this.onCompoundDrawableClickListener = listener;
    }

    public abstract static class OnCompoundDrawableClickListener {
        protected abstract void onDrawableRightClick();
    }

}