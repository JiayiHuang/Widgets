package com.jiun.widgets.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.anban.policemonitor.R;

/**
 * <pre>
 *     @author Jiun
 *     @date  :2018/12/14/21:36
 *     desc   : 可以指定 drawable compound 大小，并添加点击事件
 *     version: 当前版本号
 * </pre>
 */
public class TextImageView extends android.support.v7.widget.AppCompatTextView {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;


    private int mLeftWidth;
    private int mLeftHeight;
    private int mTopWidth;
    private int mTopHeight;
    private int mRightWidth;
    private int mRightHeight;
    private int mBottomWidth;
    private int mBottomHeight;

    private boolean mBooleanAutoClickable;
    private Drawable mDrawableLeft, mDrawableTop, mDrawableRight, mDrawableBottom;
    private Context mContext;

    public TextImageView(Context context) {
        super(context);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextImageView);
        mLeftWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableLeftWidth, 0);
        mLeftHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableLeftHeight, 0);
        mTopWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableTopWidth, 0);
        mTopHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableTopHeight, 0);
        mRightWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableRightWidth, 0);
        mRightHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableRightHeight, 0);
        mBottomWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableBottomWidth, 0);
        mBottomHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableBottomHeight, 0);
        mBooleanAutoClickable = typedArray.getBoolean(R.styleable.TextImageView_drawableAutoClickable, false);
        typedArray.recycle();

        if (mBooleanAutoClickable) {
            setFocusable(true);
            setClickable(true);
        }
        setDrawablesSize();
    }

    private void setDrawablesSize() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        for (int i = 0; i < compoundDrawables.length; i++) {
            switch (i) {
                case 0:
                    setDrawableBounds(mDrawableLeft = compoundDrawables[0], mLeftWidth, mLeftHeight);
                    break;
                case 1:
                    setDrawableBounds(mDrawableTop = compoundDrawables[1], mTopWidth, mTopHeight);
                    break;
                case 2:
                    setDrawableBounds(mDrawableRight = compoundDrawables[2], mRightWidth, mRightHeight);
                    break;
                case 3:
                    setDrawableBounds(mDrawableBottom = compoundDrawables[3], mBottomWidth, mBottomHeight);
                    break;
                default:
                    break;
            }

        }
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }

    private void setDrawableBounds(Drawable drawable, int width, int height) {
        if (drawable != null) {
            double scale = ((double) drawable.getIntrinsicHeight()) / ((double) drawable.getIntrinsicWidth());
            drawable.setBounds(0, 0, width, height);
            Rect bounds = drawable.getBounds();
            //高宽只给一个值时，自适应
            if (bounds.right != 0 || bounds.bottom != 0) {
                if (bounds.right == 0) {
                    bounds.right = (int) (bounds.bottom / scale);
                    drawable.setBounds(bounds);
                }
                if (bounds.bottom == 0) {
                    bounds.bottom = (int) (bounds.right * scale);
                    drawable.setBounds(bounds);
                }
            }
        }
    }

    public void setDrawableLeft(@DrawableRes int drawableLeftResId) {
        Drawable drawableLeft = mContext.getResources().getDrawable(drawableLeftResId);
        setCompoundDrawables(drawableLeft, mDrawableTop, mDrawableRight, mDrawableBottom);
    }

    public void setDrawableRight(@DrawableRes int drawableRightResId) {
        Drawable drawableRight = mContext.getResources().getDrawable(drawableRightResId);
        setCompoundDrawables(mDrawableLeft, mDrawableTop, drawableRight, mDrawableBottom);
    }

    public void setDrawableTop(@DrawableRes int drawableTopResId) {
        Drawable drawableTop = mContext.getResources().getDrawable(drawableTopResId);
        setCompoundDrawables(mDrawableLeft, drawableTop, mDrawableRight, mDrawableBottom);
    }

    public void setDrawableBottom(@DrawableRes int drawableBottomResId) {
        Drawable drawableBottom = mContext.getResources().getDrawable(drawableBottomResId);
        setCompoundDrawables(mDrawableLeft, mDrawableTop, mDrawableRight, drawableBottom);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (onDrawableBoundsClickListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null
                            && event.getX() <= (getWidth() - getPaddingRight())
                            && event.getX() >= (getWidth() - getPaddingRight() - drawableRight.getBounds().width())) {
                        onDrawableBoundsClickListener.onDrawableRightClick();
                    }
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null
                            && event.getX() >= getPaddingLeft()
                            && event.getX() <= getPaddingLeft() + drawableLeft.getBounds().width()) {
                        onDrawableBoundsClickListener.onDrawableLeftClick();
                    }
                    Drawable drawableTop = getCompoundDrawables()[DRAWABLE_TOP];
                    if (drawableTop != null
                            && event.getY() >= getPaddingTop()
                            && event.getY() <= getPaddingTop() + drawableTop.getBounds().height()) {
                        onDrawableBoundsClickListener.onDrawableTopClick();
                    }
                    Drawable drawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];
                    if (drawableBottom != null
                            && event.getY() >= getHeight() - getPaddingBottom() - drawableBottom.getBounds().height()
                            && event.getY() <= getHeight() - getPaddingBottom()) {
                        onDrawableBoundsClickListener.onDrawableBottomClick();
                    }
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    private OnDrawableBoundsClickListener onDrawableBoundsClickListener;

    public void setOnDrawableBoundsClickListener(OnDrawableBoundsClickListener listener) {
        mBooleanAutoClickable = true;
        setFocusable(true);
        setClickable(true);
        this.onDrawableBoundsClickListener = listener;
    }

    /**
     * TextView Compound Drawable 的点击事件
     */
    public static class OnDrawableBoundsClickListener {
        protected void onDrawableLeftClick() {
        }

        protected void onDrawableRightClick() {
        }

        protected void onDrawableTopClick() {
        }

        protected void onDrawableBottomClick() {
        }
    }
}