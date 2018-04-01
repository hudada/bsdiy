package com.example.bsproperty.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.eventbus.LoginEvent;
import com.example.bsproperty.fragment.MerchantFragment01;
import com.example.bsproperty.fragment.MerchantFragment02;
import com.example.bsproperty.fragment.MerchantFragment03;
import com.example.bsproperty.fragment.UserFragment01;
import com.example.bsproperty.fragment.UserFragment02;
import com.example.bsproperty.fragment.UserFragment03;
import com.example.bsproperty.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class MerchantMainActivity extends BaseActivity {


    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tb_bottom)
    TabLayout tbBottom;



    private long backTime;
    private MerchantFragment01 fragment01;
    private MerchantFragment02 fragment02;
    private MerchantFragment03 fragment03;
    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter adapter;
    private String[] tabs = new String[]{
            "店铺详情", "商品订单", "我的"
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        MyApplication.getInstance().setUserBean(SpUtils.getUserBean(this));

        fragment01 = new MerchantFragment01();
        fragment02 = new MerchantFragment02();
        fragment03 = new MerchantFragment03();
        fragments = new ArrayList<>();
        fragments.add(fragment01);
        fragments.add(fragment02);
        fragments.add(fragment03);


        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);

        tbBottom.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < fragments.size(); i++) {
            if (i == 0) {
                tbBottom.addTab(tbBottom.newTab().setText(tabs[i]), true);
            } else {
                tbBottom.addTab(tbBottom.newTab().setText(tabs[i]), false);
            }
        }
        tbBottom.setupWithViewPager(vpContent);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    @Override
    protected int getRootViewId() {
        return R.layout.activity_user_main;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backTime < 2000) {
            super.onBackPressed();
        } else {
            showToast(this, "再按一次，退出程序");
            backTime = System.currentTimeMillis();
        }
        backTime = System.currentTimeMillis();
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

}
