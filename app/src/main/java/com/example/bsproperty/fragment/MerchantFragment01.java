package com.example.bsproperty.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.ProductBean;
import com.example.bsproperty.bean.ShopBean;
import com.example.bsproperty.bean.ShopObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.ui.AddProductActivity;
import com.example.bsproperty.ui.CommodityActivity;
import com.example.bsproperty.ui.ShopOpenActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdxc1 on 2018/3/21.
 */

public class MerchantFragment01 extends BaseFragment {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private ArrayList<ProductBean> mdata = new ArrayList<>();
    private MyAdapter adapter;
    private ShopBean shopBean;

    @Override
    public void onResume() {
        super.onResume();
        loadWebData();
    }

    private void loadWebData() {
        mdata.clear();
        OkHttpTools.sendGet(mContext, ApiManager.SHOP_INFO + MyApplication.getInstance().getUserBean().getId())
                .build()
                .execute(new BaseCallBack<ShopObjBean>(mContext, ShopObjBean.class) {
                    @Override
                    public void onResponse(ShopObjBean shopObjBean) {
                        if (shopObjBean.getData() == null) {
                            btnRight.setVisibility(View.VISIBLE);
                            btnRight.setText("开店");
                        } else {
                            shopBean = shopObjBean.getData();
                            btnRight.setVisibility(View.VISIBLE);
                            btnRight.setText("新增商品");
                            adapter.setmHeadView(R.layout.head_merchant_detail, new BaseAdapter.OnInitHead() {
                                @Override
                                public void onInitHeadData(View headView, Object o) {
                                    initHeadView(headView);
                                }
                            });
                            mdata = (ArrayList<ProductBean>) shopObjBean.getData().getProductBeans();
                        }

                        adapter.notifyDataSetChanged(mdata);
                    }
                });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("详细地址")
                        .setMessage(shopBean.getAddr())
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("店铺信息");
        btnBack.setVisibility(View.GONE);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter(mContext, R.layout.item_shop, mdata);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                Intent intent = new Intent(mContext, CommodityActivity.class);
                intent.putExtra("sname", shopBean.getName());
                intent.putExtra("saddr", shopBean.getAddr());
                intent.putExtra("suid", shopBean.getUid());
                intent.putExtra("data", mdata.get(position));
                startActivityForResult(intent, 521);
            }
        });
        adapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, Object item, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("选择操作")
                        .setItems(new String[]{"推荐", "删除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        OkHttpTools.sendPost(mContext, ApiManager.PRODUCT_TOP)
                                                .addParams("id", mdata.get(position).getId() + "")
                                                .addParams("sid", shopBean.getId() + "")
                                                .build()
                                                .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                                    @Override
                                                    public void onResponse(BaseResponse baseResponse) {
                                                        showToast("操作成功");
                                                        loadWebData();
                                                    }
                                                });
                                        break;
                                    case 1:
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                        builder1.setTitle("提示")
                                                .setMessage("是否确定删除该商品？")
                                                .setNegativeButton("取消", null)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        OkHttpTools.sendPost(mContext, ApiManager.PRODUCT_DEL)
                                                                .addParams("id", mdata.get(position).getId() + "")
                                                                .build()
                                                                .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                                                    @Override
                                                                    public void onResponse(BaseResponse baseResponse) {
                                                                        showToast("删除成功");
                                                                        mdata.remove(position);
                                                                        adapter.notifyDataSetChanged(mdata);
                                                                    }
                                                                });
                                                    }
                                                }).show();
                                        break;
                                }
                            }
                        })
                        .show();

            }
        });
        rvList.setAdapter(adapter);
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_merchant01;
    }


    @OnClick(R.id.btn_right)
    public void onViewClicked() {
        if (btnRight.getText().toString().equals("开店")) {
            startActivity(new Intent(mContext, ShopOpenActivity.class));
        } else {
            Intent intent = new Intent(mContext, AddProductActivity.class);
            intent.putExtra("sid", shopBean.getId());
            startActivity(intent);
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
}
