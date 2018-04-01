package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.OrderBean;
import com.example.bsproperty.net.ApiManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderInfoActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_store)
    TextView tvStore;
    @BindView(R.id.tv_addr)
    TextView tvAddr;
    @BindView(R.id.tv_diy)
    TextView tvDiy;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_img)
    ImageView ivImg;

    private OrderBean mData;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("订单详情");
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void loadData() {
        mData = (OrderBean) getIntent().getSerializableExtra("data");
        tvName.setText("商品名：" + mData.getTitle());
        tvStore.setText("店铺名：" + mData.getSname());
        tvAddr.setText("地址：" + mData.getSaddr());
        if (TextUtils.isEmpty(mData.getDiyInfo())) {
            tvDiy.setVisibility(View.GONE);
        } else {
            tvDiy.setText("DIY备注：" + mData.getDiyInfo());
        }
        tvTime.setText("下单时间：" + MyApplication.format.format(mData.getTime()));
        if (!mData.getImg().equals("diy")) {
            Glide.with(mContext).load(ApiManager.IMAGE + mData.getImg()).into(ivImg);
        }
        if (mData.getActPrice() > 0) {
            tvMoney.setText("总价：￥" + mData.getActPrice() + "元");
        } else {
            tvMoney.setText("总价：￥" + mData.getPrice() + "元");
        }
    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
