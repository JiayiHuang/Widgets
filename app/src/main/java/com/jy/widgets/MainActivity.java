package com.jy.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jy.widgets.utils.ToastUtils;


/**
 * @author HJY
 * <pre>
 *     1. 主题配置，必须 NoActionBar 主题。
 * </pre>
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private Context mContext;
    private Toolbar mToolbar;
    private TextView mToolBarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        initToolbar();

        getToolbar().inflateMenu(R.menu.menu_toolbar);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_message:
//                        item.setIcon(mShowRedTip ? R.drawable.toolbar_msg_with_red : R.drawable.ic_message_black_24dp);
//                        mShowRedTip = !mShowRedTip;
                        ToastUtils.showShort("menu Message");
                        break;
                    case R.id.menu_close:
                        ToastUtils.showShort("menu close");
                        break;
                    case R.id.menu_refresh:
                        ToastUtils.showShort("menu refresh");
                        break;
                    case R.id.menu_more:
                        ToastUtils.showShort("menu more");
                        break;
                    default:
                }
                return false;
            }
        });
    }

    /**
     * 不要调用 setSupportActionBar
     */
    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolBarTextView = findViewById(R.id.toolbar_title);
        mToolBarTextView.setVisibility(View.VISIBLE);
        mToolBarTextView.setText("常用控件集合");

        mToolbar.setNavigationIcon(null);
    }


    public Toolbar getToolbar() {
        return mToolbar;
    }
}
