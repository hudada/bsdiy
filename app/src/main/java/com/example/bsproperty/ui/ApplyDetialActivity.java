package com.example.bsproperty.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.bean.ApplyBean;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyDetialActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_addr)
    TextView tvAddr;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private ApplyBean applyBean;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("申请详情");
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_apply_detial;
    }

    @Override
    protected void loadData() {
        applyBean = (ApplyBean) getIntent().getSerializableExtra("data");
        tvName.setText(applyBean.getOldName() + " ----> " + applyBean.getName());
        tvAddr.setText(applyBean.getOldAddr() + " ----> " + applyBean.getAddr());
        tvTel.setText(applyBean.getOldTel() + " ----> " + applyBean.getTel());
    }

    @OnClick({R.id.btn_back, R.id.btn_ok, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_ok:
                OkHttpTools.sendPost(mContext, ApiManager.APPLY_CHANGE)
                        .addParams("id", applyBean.getId() + "")
                        .addParams("action", "0")
                        .build()
                        .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                            @Override
                            public void onResponse(BaseResponse baseResponse) {
                                showToast("操作成功");
                                finish();
                            }
                        });
                break;
            case R.id.btn_cancel:
                OkHttpTools.sendPost(mContext, ApiManager.APPLY_CHANGE)
                        .addParams("id", applyBean.getId() + "")
                        .addParams("action", "1")
                        .build()
                        .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                            @Override
                            public void onResponse(BaseResponse baseResponse) {
                                showToast("操作成功");
                                finish();
                            }
                        });
                break;
        }
    }
}
