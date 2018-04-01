package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiyActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.rg_root)
    RadioGroup rgRoot;
    @BindView(R.id.ll_xz)
    LinearLayout llXz;
    @BindView(R.id.ll_zl)
    LinearLayout llZl;
    @BindView(R.id.ll_tl)
    LinearLayout llTl;
    @BindView(R.id.ll_sg)
    LinearLayout llSg;
    @BindView(R.id.et_word)
    EditText etWord;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private LayoutInflater mInflater;

    private View mXzSelected;
    private String mXz;
    private double mXzPrice;

    private View mZlSelected;
    private String mZl;
    private double mZlPrice;

    private View mTlSelected;
    private String mTl;
    private double mTlPrice;

    private View mSgSelected;
    private String mSg;
    private double mSgPrice;

    private String size;
    private double mSizePrice;

    private double totalPrice;
    private String sname;
    private String saddr;
    private Long suid;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("在线DIY");
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_diy;
    }

    @Override
    protected void loadData() {
        initXZ();
        initZL();
        initTl();
        initSg();
        rgRoot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) findViewById(checkedId);
                size = button.getText().toString().trim();
                switch (size) {
                    case "6寸":
                        mSizePrice = 1;
                        break;
                    case "8寸":
                        mSizePrice = 1.2;
                        break;
                    case "10寸":
                        mSizePrice = 1.5;
                        break;
                    case "12寸":
                        mSizePrice = 1.8;
                        break;
                }
                changeTotalPrice();
            }
        });
        sname = getIntent().getStringExtra("sname");
        saddr = getIntent().getStringExtra("saddr");
        suid = getIntent().getLongExtra("suid", 0);
    }

    private void initSg() {
        for (int i = 0; i < 4; i++) {
            View view = mInflater.inflate(R.layout.item_diy, null, true);
            ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            switch (i) {
                case 0:
                    iv_img.setImageResource(R.mipmap.sg_caomei);
                    tv_title.setText("草莓");
                    break;
                case 1:
                    iv_img.setImageResource(R.mipmap.sg_huangtao);
                    tv_title.setText("黄桃");
                    break;
                case 2:
                    iv_img.setImageResource(R.mipmap.sg_lanmei);
                    tv_title.setText("蓝莓");
                    break;
                case 3:
                    iv_img.setImageResource(R.mipmap.sg_shangshen);
                    tv_title.setText("桑葚");
                    break;
            }
            view.setOnClickListener(new SGClickListener(i));
            llSg.addView(view);
        }
    }

    private void initTl() {
        for (int i = 0; i < 2; i++) {
            View view = mInflater.inflate(R.layout.item_diy, null, true);
            ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            switch (i) {
                case 0:
                    iv_img.setImageResource(R.mipmap.tl_naiyou);
                    tv_title.setText("奶油");

                    break;
                case 1:
                    iv_img.setImageResource(R.mipmap.tl_qiaokeli);
                    tv_title.setText("巧克力");

                    break;
            }
            view.setOnClickListener(new TLClickListener(i));
            llTl.addView(view);
        }
    }

    private void initZL() {
        for (int i = 0; i < 3; i++) {
            View view = mInflater.inflate(R.layout.item_diy, null, true);
            ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            switch (i) {
                case 0:
                    iv_img.setImageResource(R.mipmap.zl_haimian);
                    tv_title.setText("海绵蛋糕");

                    break;
                case 1:
                    iv_img.setImageResource(R.mipmap.zl_musi);
                    tv_title.setText("慕斯蛋糕");

                    break;
                case 2:
                    iv_img.setImageResource(R.mipmap.zl_qifeng);
                    tv_title.setText("戚风蛋糕");

                    break;
            }
            view.setOnClickListener(new ZLClickListener(i));
            llZl.addView(view);
        }
    }

    private void initXZ() {
        for (int i = 0; i < 4; i++) {
            View view = mInflater.inflate(R.layout.item_diy, null, true);
            ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            switch (i) {
                case 0:
                    iv_img.setImageResource(R.mipmap.xz_fang);
                    tv_title.setText("方形");
                    break;
                case 1:
                    iv_img.setImageResource(R.mipmap.xz_star);
                    tv_title.setText("星形");
                    break;
                case 2:
                    iv_img.setImageResource(R.mipmap.xz_xing);
                    tv_title.setText("心形");
                    break;
                case 3:
                    iv_img.setImageResource(R.mipmap.xz_yuan);
                    tv_title.setText("圆形");
                    break;
            }
            view.setOnClickListener(new XZClickListener(i));
            llXz.addView(view);
        }
    }


    @OnClick({R.id.btn_back, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_ok:
                if (mSizePrice <= 0 ||
                        mXzPrice <= 0 ||
                        mZlPrice <= 0 ||
                        mTlPrice <= 0 ||
                        mSgPrice <= 0
                        ) {
                    showToast("请选择完整");
                    return;
                }
                String word = etWord.getText().toString().trim();
                if (TextUtils.isEmpty(word)) {
                    word = "生日快乐";
                }
                if (MyApplication.getInstance().getUserBean().getRole() == MyApplication.CURR_USER) {
                    OkHttpTools.sendPost(mContext, ApiManager.ORDER_ADD)
                            .addParams("sname", sname)
                            .addParams("saddr", saddr)
                            .addParams("suid", suid + "")
                            .addParams("pid", "diy")
                            .addParams("price", totalPrice + "")
                            .addParams("img", "diy")
                            .addParams("title", "DIY蛋糕")
                            .addParams("diyInfo", mXz + "+" + mZl + "+" + mTl + "+" + mSg + "+文字：" + word)
                            .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                            .build().execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
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

    private class XZClickListener implements View.OnClickListener {
        private int flag;

        public XZClickListener(int flag) {
            this.flag = flag;
        }

        @Override
        public void onClick(View v) {
            if (mXzSelected != null) {
                mXzSelected.findViewById(R.id.iv_checked).setVisibility(View.GONE);
            }
            mXzSelected = v;
            mXzSelected.findViewById(R.id.iv_checked).setVisibility(View.VISIBLE);
            switch (flag) {
                case 0:
                    mXz = "方形";
                    mXzPrice = 40;
                    break;
                case 1:
                    mXz = "星形";
                    mXzPrice = 60;
                    break;
                case 2:
                    mXz = "心形";
                    mXzPrice = 40;
                    break;
                case 3:
                    mXz = "圆形";
                    mXzPrice = 30;
                    break;
            }
            changeTotalPrice();
        }
    }

    private class ZLClickListener implements View.OnClickListener {
        private int flag;

        public ZLClickListener(int flag) {
            this.flag = flag;
        }

        @Override
        public void onClick(View v) {
            if (mZlSelected != null) {
                mZlSelected.findViewById(R.id.iv_checked).setVisibility(View.GONE);
            }
            mZlSelected = v;
            mZlSelected.findViewById(R.id.iv_checked).setVisibility(View.VISIBLE);
            switch (flag) {
                case 0:
                    mZl = "海绵蛋糕";
                    mZlPrice = 60;
                    break;
                case 1:
                    mZl = "慕斯蛋糕";
                    mZlPrice = 100;
                    break;
                case 2:
                    mZl = "戚风蛋糕";
                    mZlPrice = 80;
                    break;
            }
            changeTotalPrice();
        }
    }

    private class TLClickListener implements View.OnClickListener {
        private int flag;

        public TLClickListener(int flag) {
            this.flag = flag;
        }

        @Override
        public void onClick(View v) {
            if (mTlSelected != null) {
                mTlSelected.findViewById(R.id.iv_checked).setVisibility(View.GONE);
            }
            mTlSelected = v;
            mTlSelected.findViewById(R.id.iv_checked).setVisibility(View.VISIBLE);
            switch (flag) {
                case 0:
                    mTl = "奶油";
                    mTlPrice = 30;
                    break;
                case 1:
                    mTl = "巧克力";
                    mTlPrice = 35;
                    break;
            }
            changeTotalPrice();
        }
    }

    private class SGClickListener implements View.OnClickListener {
        private int flag;

        public SGClickListener(int flag) {
            this.flag = flag;
        }

        @Override
        public void onClick(View v) {
            if (mSgSelected != null) {
                mSgSelected.findViewById(R.id.iv_checked).setVisibility(View.GONE);
            }
            mSgSelected = v;
            mSgSelected.findViewById(R.id.iv_checked).setVisibility(View.VISIBLE);
            switch (flag) {
                case 0:
                    mSg = "草莓";
                    mSgPrice = 20;
                    break;
                case 1:
                    mSg = "黄桃";
                    mSgPrice = 15;
                    break;
                case 2:
                    mSg = "蓝莓";
                    mSgPrice = 20;
                    break;
                case 3:
                    mSg = "桑葚";
                    mSgPrice = 20;
                    break;
            }
            changeTotalPrice();
        }
    }

    private void changeTotalPrice() {
        totalPrice = mSizePrice * (mXzPrice + mZlPrice + mTlPrice + mSgPrice);
        tvPrice.setText("￥ " + totalPrice + "元");
    }
}
