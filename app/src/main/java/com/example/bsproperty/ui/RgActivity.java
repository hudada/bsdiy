package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsproperty.R;

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

    private int limit;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_rg;
    }

    @Override
    protected void loadData() {
        limit = getIntent().getIntExtra("0",0);
    }


    @OnClick(R.id.btn_0)
    public void onViewClicked() {
        String user = etUser.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)){
            showToast("请输入完整信息");
            return;
        }
    }
}
