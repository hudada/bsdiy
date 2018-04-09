package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.UserObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RgActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_0)
    Button btn0;
    @BindView(R.id.rg_list)
    RadioGroup rgList;

    private int limit;

    @Override
    protected void initView(Bundle savedInstanceState) {
        limit = 1;
        rgList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                limit = Integer.parseInt(findViewById(checkedId).getTag().toString());
            }
        });
        ((RadioButton)rgList.getChildAt(0)).setChecked(true);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_rg;
    }

    @Override
    protected void loadData() {
    }


    @OnClick(R.id.btn_0)
    public void onViewClicked() {
        String user = etUser.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
            showToast("请输入完整信息");
            return;
        }
        String url = "";
        switch (limit) {
            case MyApplication.CURR_USER:
                url = ApiManager.USER_RG + MyApplication.CURR_USER;
                break;
            case MyApplication.CURR_MERCHANT:
                url = ApiManager.USER_RG + MyApplication.CURR_MERCHANT;
                break;
        }
        OkHttpTools.sendPost(mContext, url)
                .addParams("name", user)
                .addParams("pwd", pwd)
                .build()
                .execute(new BaseCallBack<UserObjBean>(mContext, UserObjBean.class) {
                    @Override
                    public void onResponse(UserObjBean userObjBean) {
                        showToast("注册成功");
                        finish();
                    }
                });
    }
}
