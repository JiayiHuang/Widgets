package com.jy.widgets.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jy.widgets.BaseActivity;
import com.jy.widgets.R;
import com.jy.widgets.utils.ToastUtils;

/**
 * @author jiun
 */
public class SlidableActivity extends BaseActivity {
    ListView listView;

    @Override
    protected void initViews() {
        listView = findViewById(R.id.listView);
        listView.setAdapter(new SlidingAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.slidable_activity;
    }

    class SlidingAdapter extends BaseAdapter implements SlidableView.IonSlidingButtonListener {
        private SlidableView mMenu = null;

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.slidable_item, listView, false);
            }
            if (menuIsOpen()) {
                closeMenu();
            }
            convertView.findViewById(R.id.item_cartlist_ll_content).getLayoutParams().width = SlidableActivity.this.getWindowManager().getDefaultDisplay().getWidth();
            convertView.findViewById(R.id.item_cartlist_ll_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuIsOpen()) {
                        closeMenu();
                    }
                }
            });
            convertView.findViewById(R.id.item_cartlist_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("delete");
                }
            });
            ((SlidableView) convertView).setSlidingButtonListener(this);

            return convertView;
        }

        @Override
        public void onMenuIsOpen(View view) {
            mMenu = (SlidableView) view;
        }

        @Override
        public void onDownOrMove(SlidableView slidingButtonView) {
            if (menuIsOpen()) {
                if (mMenu != slidingButtonView) {
                    closeMenu();
                }
            }
        }

        private boolean menuIsOpen() {
            return mMenu != null;
        }

        private void closeMenu() {
            mMenu.closeMenu();
            mMenu = null;
        }
    }
}