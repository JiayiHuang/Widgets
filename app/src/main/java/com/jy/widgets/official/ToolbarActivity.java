package com.jy.widgets.official;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jy.widgets.R;
import com.jy.widgets.utils.ToastUtils;


/**
 * @author HJY
 * <pre>
 *     1. 主题配置，必须 NoActionBar 主题。
 * </pre>
 */
public class ToolbarActivity extends AppCompatActivity {
    Toolbar mToolbar;
    Context mContext;
    Spinner mSpinner;
    private boolean mShowRedTip = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.toolbar_activity);
        mToolbar = findViewById(R.id.toolbar);

        getToolbar().setTitle(" ");
//        getToolbar().setSubtitle("SubTitle");
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getNavigationToastString());
            }
        });

        getToolbar().inflateMenu(R.menu.menu_toolbar);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_message:
                        item.setIcon(mShowRedTip ? R.drawable.toolbar_msg_with_red : R.drawable.ic_message_black_24dp);
                        mShowRedTip = !mShowRedTip;
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
        // 设置自定义的 Menu 图标 及 动作。
        MenuItem menuItem = getToolbar().getMenu().findItem(R.id.menu_message);
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
                String[] stringArray = mContext.getResources().getStringArray(R.array.planets_array);
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
