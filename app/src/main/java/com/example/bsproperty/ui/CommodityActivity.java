package com.example.bsproperty.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.ProductBean;
import com.example.bsproperty.bean.ShopBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommodityActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sale)
    TextView tvSale;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.tv_top)
    TextView tvTop;

    private ProductBean productBean;
    private String sname;
    private String saddr;
    private Long suid;

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (MyApplication.getInstance().getUserBean().getRole() == MyApplication.CURR_USER) {
            btnOk.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
        } else {
            btnOk.setVisibility(View.GONE);
            btnRight.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_commodity;
    }

    @Override
    protected void loadData() {
        productBean = (ProductBean) getIntent().getSerializableExtra("data");
        Glide.with(mContext).load(ApiManager.IMAGE + productBean.getImg()).into(ivImg);
        tvTitle.setText(productBean.getName());
        tvName.setText(productBean.getName());
        if (productBean.isActivity()) {
            tvSale.setVisibility(View.VISIBLE);
            tvOldPrice.setText("原价：￥" + productBean.getPrice());
            tvPrice.setText("￥" + productBean.getActProce());
        } else {
            tvPrice.setText("￥" + productBean.getPrice());
            tvOldPrice.setVisibility(View.GONE);
            tvSale.setVisibility(View.GONE);
        }
        if (productBean.getIsTop() == 1) {
            tvTop.setVisibility(View.VISIBLE);
        } else {
            tvTop.setVisibility(View.GONE);
        }
        tvTotal.setText("月售：" + productBean.getSum());
        tvInfo.setText(productBean.getInfo());
        sname = getIntent().getStringExtra("sname");
        saddr = getIntent().getStringExtra("saddr");
        suid = getIntent().getLongExtra("suid", 0);
    }

    @OnClick({R.id.btn_back, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_ok:
                if (MyApplication.getInstance().getUserBean().getRole() == MyApplication.CURR_USER) {
                    PostFormBuilder postFormBuilder = OkHttpTools.sendPost(mContext, ApiManager.ORDER_ADD)
                            .addParams("sname", sname)
                            .addParams("saddr", saddr)
                            .addParams("suid", suid + "")
                            .addParams("pid", productBean.getId() + "")
                            .addParams("price", productBean.getPrice() + "")
                            .addParams("img", productBean.getImg())
                            .addParams("title", productBean.getName())
                            .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "");
                    if (productBean.isActivity()) {
                        postFormBuilder.addParams("actPrice", productBean.getActProce() + "");
                    }
                    postFormBuilder.build().execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                        @Override
                        public void onResponse(BaseResponse baseResponse) {
                            showToast("预定成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                }
                break;
        }
    }
}
