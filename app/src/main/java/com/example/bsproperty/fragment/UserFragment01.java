package com.example.bsproperty.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.Merchant;
import com.example.bsproperty.ui.MerchantDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wdxc1 on 2018/3/21.
 */

public class UserFragment01 extends BaseFragment {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;

    private ArrayList<Merchant> mData;
    private MyAdapter adapter;

    @Override
    protected void loadData() {

        for (int i = 0; i < 10; i++) {
            mData.add(new Merchant());
        }
        rvList.setAdapter(adapter);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("商家列表");
        btnBack.setVisibility(View.GONE);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        adapter = new MyAdapter(mContext,R.layout.item_merchant,mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                startActivity(new Intent(mContext, MerchantDetailActivity.class));
            }
        });
        slList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                slList.setRefreshing(false);
            }
        });
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_user01;
    }

    private class MyAdapter extends BaseAdapter<Merchant>{

        public MyAdapter(Context context, int layoutId, ArrayList<Merchant> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, Merchant merchant, int position) {

        }
    }
}
