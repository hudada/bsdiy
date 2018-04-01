package com.example.bsproperty.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.OrderBean;
import com.example.bsproperty.bean.OrderListBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.ui.OrderInfoActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by wdxc1 on 2018/3/21.
 */

public class MerchantFragment02 extends BaseFragment {
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
    private ArrayList<OrderBean> mData;
    private MyAdapter adapter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mContext != null) {
            OkHttpTools.sendGet(mContext, ApiManager.ORDER_LIST)
                    .addParams("type", "2")
                    .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                    .build()
                    .execute(new BaseCallBack<OrderListBean>(mContext, OrderListBean.class) {
                        @Override
                        public void onResponse(OrderListBean orderListBean) {
                            mData = orderListBean.getData();
                            adapter.notifyDataSetChanged(mData);
                        }
                    });
        }
    }

    @Override
    protected void loadData() {
        OkHttpTools.sendGet(mContext, ApiManager.ORDER_LIST)
                .addParams("type", "2")
                .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                .build()
                .execute(new BaseCallBack<OrderListBean>(mContext, OrderListBean.class) {
                    @Override
                    public void onResponse(OrderListBean orderListBean) {
                        mData = orderListBean.getData();
                        adapter.notifyDataSetChanged(mData);
                    }
                });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("订单列表");
        btnBack.setVisibility(View.GONE);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        adapter = new MyAdapter(mContext, R.layout.item_order, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                Intent intent = new Intent(mContext, OrderInfoActivity.class);
                intent.putExtra("data", mData.get(position));
                startActivity(intent);
            }
        });
        rvList.setAdapter(adapter);
        slList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                slList.setRefreshing(false);
            }
        });
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_merchant02;
    }

    private class MyAdapter extends BaseAdapter<OrderBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<OrderBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, OrderBean orderBean, int position) {
            holder.setText(R.id.tv_name, orderBean.getUname() + "用户的订单");
            holder.setText(R.id.tv_time, MyApplication.format.format(orderBean.getTime()));
            holder.getView(R.id.tv_store).setVisibility(View.GONE);
            holder.getView(R.id.tv_addr).setVisibility(View.GONE);
            if (orderBean.getActPrice() > 0) {
                holder.setText(R.id.tv_money, "总价：￥" + orderBean.getActPrice() + "元");
            } else {
                holder.setText(R.id.tv_money, "总价：￥" + orderBean.getPrice() + "元");
            }
        }
    }
}
