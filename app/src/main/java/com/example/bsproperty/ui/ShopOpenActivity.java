package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopOpenActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.et_addr)
    EditText etAddr;
    @BindView(R.id.et_tel)
    EditText etTel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("新的店铺");
        btnRight.setText("保存");
        btnRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_shop_open;
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
                if (!checkEditEmpty(etName, etInfo, etAddr, etTel)) {
                    final String name = etName.getText().toString().trim();
                    String info = etInfo.getText().toString().trim();
                    final String addr = etAddr.getText().toString().trim();
                    final String tel = etTel.getText().toString().trim();
                    OkHttpTools.sendPost(mContext, ApiManager.SHOP_ADD)
                            .addParams("name", name)
                            .addParams("info", info)
                            .addParams("addr", addr)
                            .addParams("tel", tel)
                            .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                            .build()
                            .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                @Override
                                public void onResponse(BaseResponse baseResponse) {
                                    showToast("开店成功");
                                    MyApplication.getInstance().getUserBean().setName(name);
                                    MyApplication.getInstance().getUserBean().setAddr(addr);
                                    MyApplication.getInstance().getUserBean().setTel(tel);
                                    finish();
                                }
                            });
                }
                break;
        }
    }
}
