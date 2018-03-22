package com.example.bsproperty.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.Merchant;
import com.example.bsproperty.bean.MerchantDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MerchantDetailActivity extends BaseActivity {

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

    private ArrayList<MerchantDetail> mData;
    private MyAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("店铺详情");
        btnRight.setText("在线DIY");
        btnRight.setVisibility(View.VISIBLE);

        slList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                slList.setRefreshing(false);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        adapter = new MyAdapter(mContext,R.layout.item_shop,mData);
        adapter.setmHeadView(R.layout.head_merchant_detail, new BaseAdapter.OnInitHead() {
            @Override
            public void onInitHeadData(View headView, Object o) {
                TextView tvName = (TextView) headView.findViewById(R.id.tv_name);
                String start = tvName.getText().toString().substring(0,1);
                TextView tvTop = (TextView) headView.findViewById(R.id.tv_top);
                tvTop.setText(start);

                headView.findViewById(R.id.rl_addr_click).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("详细地址")
                                .setMessage("蛋糕广场109号")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                });
            }
        });

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_merchant_detail;
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 5; i++) {
            mData.add(new MerchantDetail());
        }
        rvList.setAdapter(adapter);
    }


    @OnClick({R.id.btn_back, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_right:
                break;
        }
    }

    private class MyAdapter extends BaseAdapter<MerchantDetail> {

        public MyAdapter(Context context, int layoutId, ArrayList<MerchantDetail> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, MerchantDetail merchant, int position) {

        }
    }
}
