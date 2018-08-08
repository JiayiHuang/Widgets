package com.jy.widgets;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jy.widgets.model.ActivityBean;
import com.jy.widgets.official.ToolbarActivity;
import com.jy.widgets.official.recycler.decoration.HorizontalDividerItemDecoration;
import com.jy.widgets.official.spinner.SpinnerActivity;
import com.jy.widgets.utils.ToastUtils;

import java.util.ArrayList;


/**
 * @author HJY
 * <pre>
 *     1. 主题配置，必须 NoActionBar 主题。
 * </pre>
 */
public class MainActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private Toolbar mToolbar;
    private TextView mToolBarTextView;
    private ArrayList<ActivityBean> mDataList = new ArrayList<>();


    private ArrayList<ActivityBean> generateData() {
        mDataList.add(new ActivityBean("Toolbar", ToolbarActivity.class));
        mDataList.add(new ActivityBean("Spinner", SpinnerActivity.class));
        return mDataList;
    }

    private void setRecycler(final ArrayList<ActivityBean> dataList) {
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecycler.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(mActivity)
                        .sizeResId(R.dimen.divider_recycler)
                        .colorResId(R.color.divider)
                        .build());
        mRecycler.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View rootItem = LayoutInflater.from(mActivity).inflate(R.layout.item_main, parent, false);
                return new RecyclerHolder(rootItem);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                final ActivityBean item = dataList.get(position);
                ((RecyclerHolder) holder).textView.setText(item.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.startActivity(new Intent(mActivity, item.getClazz()));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        });

    }


    @Override
    protected void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mToolBarTextView = findViewById(R.id.toolbar_title);
        mRecycler = findViewById(R.id.main_recyclerView);


        initToolbar();

        setRecycler(generateData());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 不要调用 setSupportActionBar
     */
    private void initToolbar() {
        mToolBarTextView.setVisibility(View.VISIBLE);
        mToolBarTextView.setText("常用控件集合");

        mToolbar.setNavigationIcon(null);

        getToolbar().inflateMenu(R.menu.menu_main);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_main_custom:
                        ToastUtils.showShort("custom");
                        break;
                    case R.id.menu_main_official:
                        ToastUtils.showShort("official");
                        break;
                    default:
                }
                return false;
            }
        });
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        RecyclerHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView;
        }
    }
}
