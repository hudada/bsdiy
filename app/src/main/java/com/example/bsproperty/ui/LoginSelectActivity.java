package com.example.bsproperty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginSelectActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_user)
    Button btnUser;
    @BindView(R.id.btn_merchant)
    Button btnMerchant;
    @BindView(R.id.btn_admin)
    Button btnAdmin;


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_login_select;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.btn_user, R.id.btn_merchant, R.id.btn_admin})
    public void onViewClicked(View view) {
        Intent intent = new Intent(mContext,LoginActivity.class);
        switch (view.getId()) {
            case R.id.btn_user:
                intent.putExtra("limit", MyApplication.CURR_USER);
                break;
            case R.id.btn_merchant:
                intent.putExtra("limit",MyApplication.CURR_MERCHANT);
                break;
            case R.id.btn_admin:
                intent.putExtra("limit",MyApplication.CURR_ADMIN);
                break;
        }
        startActivity(intent);
        finish();
    }
}
