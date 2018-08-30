package com.jy.widgets.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.jy.widgets.R;

/**
 * @author HJY
 * @date 2018/08/10/10:20
 * <pre>
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class SlidableView extends HorizontalScrollView {


    public SlidableView(Context context) {
        this(context, null);
    }

    public SlidableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlidableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Context mContext;
    private TextView mTextView_Delete;

    private int mScrollWidth;

    private IonSlidingButtonListener mIonSlidingButtonListener;

    private Boolean isOpen = false;
    private Boolean once = false;
    private @IdRes
    int mBottomViewId, mTopViewId;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mContext = context;
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        this.setFillViewport(false);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.SlidableView, defStyleAttr, defStyleRes);
        mBottomViewId = typedArray.getResourceId(R.styleable.SlidableView_id_view_bottom, 0);
        mTopViewId = typedArray.getResourceId(R.styleable.SlidableView_id_view_top, 0);
        typedArray.recycle();

/*
        post(new Runnable() {
            @Override
            public void run() {
                mTextView_Delete = findViewById(mBottomViewId);
            }
        });
*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!once) {
            mTextView_Delete = (TextView) findViewById(mBottomViewId);
            once = true;
        }

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, 0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            if (mTextView_Delete != null) {
                mScrollWidth = mTextView_Delete.getWidth();
            }
            Log.i("asd", "mScrollWidth:" + mScrollWidth);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollX();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mTextView_Delete.setTranslationX(l - mScrollWidth);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollX() {
        if (getScrollX() >= (mScrollWidth / 2)) {
            this.smoothScrollTo(mScrollWidth, 0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
        } else {
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }


    public void setSlidingButtonListener(IonSlidingButtonListener listener) {
        mIonSlidingButtonListener = listener;
    }

    public interface IonSlidingButtonListener {
        void onMenuIsOpen(View view);

        void onDownOrMove(SlidableView slidingButtonView);
    }
}
