package com.example.bsproperty.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.ApplyBean;
import com.example.bsproperty.bean.ApplyListBean;
import com.example.bsproperty.bean.ProductBean;
import com.example.bsproperty.bean.ShopObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.ui.ApplyDetialActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wdxc1 on 2018/3/31.
 */

public class AdminFragment01 extends BaseFragment {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private ArrayList<ApplyBean> mdata = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        loadWebData();
    }

    private void loadWebData() {
        mdata.clear();
        OkHttpTools.sendGet(mContext, ApiManager.APPLY_LIST)
                .build()
                .execute(new BaseCallBack<ApplyListBean>(mContext, ApplyListBean.class) {
                    @Override
                    public void onResponse(ApplyListBean applyListBean) {
                        mdata = applyListBean.getData();
                        adapter.notifyDataSetChanged(mdata);
                    }
                });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("申请列表");
        btnBack.setVisibility(View.GONE);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter(mContext, R.layout.item_apply, mdata);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                if (mdata.get(position).getStatus() == 0) {
                    Intent intent = new Intent(mContext, ApplyDetialActivity.class);
                    intent.putExtra("data", mdata.get(position));
                    startActivity(intent);
                }
            }
        });
        rvList.setAdapter(adapter);
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_admin01;
    }

    private class MyAdapter extends BaseAdapter<ApplyBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<ApplyBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, ApplyBean applyBean, int position) {
            holder.setText(R.id.tv_name, applyBean.getUname() + "商家的申请");
            TextView status = (TextView) holder.getView(R.id.tv_status);
            switch (applyBean.getStatus()) {// 0申请中，1同意，2拒绝
                case 0:
                    status.setText("申请中");
                    break;
                case 1:
                    status.setText("同意");
                    status.setTextColor(Color.GREEN);
                    break;
                case 2:
                    status.setText("拒绝");
                    status.setTextColor(Color.RED);
                    break;
            }
            holder.setText(R.id.tv_time, MyApplication.format.format(applyBean.getTime()));
        }
    }
}
