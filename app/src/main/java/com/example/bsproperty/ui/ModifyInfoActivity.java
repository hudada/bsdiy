package com.example.bsproperty.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.UserBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyInfoActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_addr)
    EditText etAddr;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.rl_shop)
    RelativeLayout rlShop;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("修改信息");
        btnRight.setText("保存");
        btnRight.setVisibility(View.VISIBLE);
        if (MyApplication.getInstance().getUserBean().getRole() == MyApplication.CURR_MERCHANT) {
            rlShop.setVisibility(View.VISIBLE);
        } else {
            rlShop.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_modify_info;
    }

    @Override
    protected void loadData() {
        if (MyApplication.getInstance().getUserBean().getRole() == MyApplication.CURR_MERCHANT) {
            etName.setText(MyApplication.getInstance().getUserBean().getName());
        }
        etAddr.setText(MyApplication.getInstance().getUserBean().getAddr());
        etTel.setText(MyApplication.getInstance().getUserBean().getTel());
    }

    @OnClick({R.id.btn_back, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_right:
                if (MyApplication.getInstance().getUserBean().getRole() == MyApplication.CURR_MERCHANT) {
                    if (!checkEditEmpty(etName, etAddr, etTel)) {
                        String name = etName.getText().toString().trim();
                        String addr = etAddr.getText().toString().trim();
                        String tel = etTel.getText().toString().trim();
                        OkHttpTools.sendPost(mContext, ApiManager.APPLY_ADD)
                                .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                                .addParams("addr", addr)
                                .addParams("tel", tel)
                                .addParams("name", name)
                                .build()
                                .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                    @Override
                                    public void onResponse(BaseResponse baseResponse) {
                                        showToast("修改申请已提交");
                                        finish();
                                    }
                                });
                    }
                } else {
                    if (!checkEditEmpty(etAddr, etTel)) {
                        final String addr = etAddr.getText().toString().trim();
                        final String tel = etTel.getText().toString().trim();
                        OkHttpTools.sendPost(mContext, ApiManager.USER_CHANGE + MyApplication.getInstance().getUserBean().getId())
                                .addParams("addr", addr)
                                .addParams("tel", tel)
                                .build()
                                .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                    @Override
                                    public void onResponse(BaseResponse baseResponse) {
                                        showToast("修改成功");
                                        MyApplication.getInstance().getUserBean().setAddr(addr);
                                        MyApplication.getInstance().getUserBean().setTel(tel);
                                        finish();
                                    }
                                });
                    }
                }
                break;
        }
    }
}
