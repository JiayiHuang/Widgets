package com.jy.widgets.official;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jy.widgets.BaseActivity;
import com.jy.widgets.R;
import com.jy.widgets.utils.ToastUtils;


/**
 * @author HJY
 * <pre>
 *     1. 主题配置，必须 NoActionBar 主题。
 * </pre>
 */
public class ToolbarActivity extends BaseActivity {
    private Toolbar mToolbar, mToolbar2, mToolbar3, mToolbar4;
    private TextView mToolbar2Title, mToolbar3Title, mToolbar4Title;
    private Spinner mSpinner;
    private boolean mShowRedTip;


    @Override
    protected int getLayoutId() {
        return R.layout.toolbar_activity;
    }

    @Override
    protected void initViews() {

        mToolbar = findViewById(R.id.toolbar);
        mToolbar2 = findViewById(R.id.toolbar2);
        mToolbar3 = findViewById(R.id.toolbar3);
        mToolbar4 = findViewById(R.id.toolbar4);
        mToolbar2Title = findViewById(R.id.toolbar2_title);
        mToolbar3Title = findViewById(R.id.toolbar3_title);
        mToolbar4Title = findViewById(R.id.toolbar4_title);
        initToolbarMain();
        initToolbar2();
        initToolbar3();
        initToolbar4();

    }

    private void initToolbar4() {

    }

    private void initToolbar3() {
    }

    private void initToolbar2() {
        mToolbar2.inflateMenu(R.menu.menu_toolbar2);
        mToolbar2Title.setVisibility(View.VISIBLE);
        mToolbar2Title.setText("居中标题");
        mToolbar2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastUtils.showShort("Toolbar2 clicked");
                return true;
            }
        });
    }

    private void initToolbarMain() {
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getToolbar().inflateMenu(R.menu.menu_toolbar);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_toolbar_message:
                        item.setIcon(mShowRedTip ? R.drawable.toolbar_msg_with_red : R.drawable.ic_message_black_24dp);
                        mShowRedTip = !mShowRedTip;
                        ToastUtils.showShort("menu Message");
                        break;
                    case R.id.menu_toolbar_close:
                        ToastUtils.showShort("menu close");
                        break;
                    case R.id.menu_toolbar_refresh:
                        ToastUtils.showShort("menu refresh");
                        break;
                    case R.id.menu_toolbar_more:
                        ToastUtils.showShort("menu more");
                        break;
                    default:
                }
                return false;
            }
        });
        // 设置自定义的 Menu 图标 及 动作。
        MenuItem menuItem = getToolbar().getMenu().findItem(R.id.menu_toolbar_message);
/*
        View messageRoot = getLayoutInflater().inflate(R.layout.toolbar_menu_msg, getToolbar(), false);
        final View msgRedTip = messageRoot.findViewById(R.id.menu_msg_red_tip);
        menuItem.setActionView(messageRoot);
        messageRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //业务逻辑
                msgRedTip.setVisibility(msgRedTip.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
*/

        addSpinnerIntoToolbar();
    }


    /**
     * 添加 Spinner 至 toolbar 中，当然也可以用 setActionView 的方式添加至 MenuItem 中，不过就没有了点击效果
     */
    private void addSpinnerIntoToolbar() {
        mSpinner = (Spinner) getLayoutInflater().inflate(R.layout.toolbar_menu_spinner, getToolbar(), false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT | Gravity.END;
        getToolbar().addView(mSpinner, lp);
        mSpinner.post(new Runnable() {
            @Override
            public void run() {
                mSpinner.setDropDownVerticalOffset((int) ((mToolbar.getHeight() - mSpinner.getHeight()) / 2.0f + mSpinner.getHeight()));
            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] stringArray = mActivity.getResources().getStringArray(R.array.planets_array);
                ToastUtils.showShort(stringArray[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ToastUtils.showShort("NothingSelected");
            }
        });

    }

    private String getNavigationToastString() {
        Object spinnerSelectedItem = mSpinner.getSelectedItem();
        if (spinnerSelectedItem instanceof String) {
            return (String) spinnerSelectedItem;
        }
        return "Just Navigation";
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ToolbarActivity.class));
    }
}
