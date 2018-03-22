package com.example.bsproperty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_0)
    Button btn0;
    @BindView(R.id.btn_1)
    Button btn1;

    private int limit;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void loadData() {
        limit = getIntent().getIntExtra("limit", 0);

        switch (limit) {
            case MyApplication.CURR_ADMIN:
                btn1.setVisibility(View.GONE);
                break;
            case MyApplication.CURR_USER:
                break;
            case MyApplication.CURR_MERCHANT:
                break;

        }

    }


    @OnClick({R.id.btn_0, R.id.btn_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_0:
                String user = etUser.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
                    showToast("请输入完整信息");
                    return;
                }
                switch (limit) {
                    case MyApplication.CURR_ADMIN:
                        break;
                    case MyApplication.CURR_USER:
                        startActivity(new Intent(mContext,UserMainActivity.class));
                        finish();
                        break;
                    case MyApplication.CURR_MERCHANT:
                        break;

                }
                break;
            case R.id.btn_1:
                jumpAct(RgActivity.class, limit);
                break;
        }
    }
}
