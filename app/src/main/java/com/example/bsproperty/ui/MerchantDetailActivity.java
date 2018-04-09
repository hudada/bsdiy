package com.example.bsproperty.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.ProductBean;
import com.example.bsproperty.bean.ShopBean;
import com.example.bsproperty.fragment.MerchantFragment01;
import com.example.bsproperty.net.ApiManager;

import java.util.ArrayList;

import butterknife.BindView;
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

    private ArrayList<ProductBean> mdata = new ArrayList<>();
    private MyAdapter adapter;
    private ShopBean shopBean;
    private int mPosition;

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
        shopBean = (ShopBean) getIntent().getSerializableExtra("data");
        mdata = (ArrayList<ProductBean>) shopBean.getProductBeans();
        adapter = new MyAdapter(mContext, R.layout.item_shop, mdata);
        adapter.setmHeadView(R.layout.head_merchant_detail, new BaseAdapter.OnInitHead() {
            @Override
            public void onInitHeadData(View headView, Object o) {
                initHeadView(headView);
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                mPosition = position;
                Intent intent = new Intent(mContext, CommodityActivity.class);
                intent.putExtra("sname", shopBean.getName());
                intent.putExtra("saddr", shopBean.getAddr());
                intent.putExtra("suid", shopBean.getUid());
                intent.putExtra("data", mdata.get(position));
                startActivityForResult(intent, 521);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setAdapter(adapter);
    }

    private void initHeadView(View headView) {
        TextView tv_top = (TextView) headView.findViewById(R.id.tv_top);
        TextView tv_name = (TextView) headView.findViewById(R.id.tv_name);
        TextView tv_total = (TextView) headView.findViewById(R.id.tv_total);
        TextView tv_tel = (TextView) headView.findViewById(R.id.tv_tel);
        TextView tv_msg = (TextView) headView.findViewById(R.id.tv_msg);
        TextView tv_addr = (TextView) headView.findViewById(R.id.tv_addr);
        tv_top.setText(shopBean.getName().substring(0, 1));
        tv_name.setText(shopBean.getName());
        tv_total.setText("月售：" + shopBean.getSum());
        tv_tel.setText("tel：" + shopBean.getTel());
        tv_msg.setText(shopBean.getInfo());
        tv_addr.setText("地址：" + shopBean.getAddr());
        headView.findViewById(R.id.rl_addr_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                builder.setTitle("详细地址")
                        .setMessage(shopBean.getAddr())
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_merchant_detail;
    }

    @Override
    protected void loadData() {
    }


    @OnClick({R.id.btn_back, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_right:
                Intent intent = new Intent(mContext, DiyActivity.class);
                intent.putExtra("sname", shopBean.getName());
                intent.putExtra("saddr", shopBean.getAddr());
                intent.putExtra("suid", shopBean.getUid());
                startActivity(intent);
                break;
        }
    }

    private class MyAdapter extends BaseAdapter<ProductBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<ProductBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, ProductBean productBean, int position) {
            Glide.with(mContext).load(ApiManager.IMAGE + productBean.getImg()).into((ImageView) holder.getView(R.id.iv_img));
            holder.setText(R.id.tv_name, productBean.getName());
            boolean isAct = productBean.isActivity();
            if (isAct) {
                holder.getView(R.id.tv_sale).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_old_price).setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_old_price, "原价：￥" + productBean.getPrice());
                holder.setText(R.id.tv_price, "￥" + productBean.getActProce());
            } else {
                holder.setText(R.id.tv_price, "￥" + productBean.getPrice());
                holder.getView(R.id.tv_old_price).setVisibility(View.GONE);
                holder.getView(R.id.tv_sale).setVisibility(View.GONE);
            }
            if (productBean.getIsTop() == 1) {
                holder.getView(R.id.tv_top).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.tv_top).setVisibility(View.GONE);
            }
            holder.setText(R.id.tv_total, "月售：" + productBean.getSum());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            shopBean.setSum(shopBean.getSum() + 1);
            mdata.get(mPosition).setSum(mdata.get(mPosition).getSum() + 1);
            adapter.notifyDataSetChanged(mdata);
        }
    }
}
