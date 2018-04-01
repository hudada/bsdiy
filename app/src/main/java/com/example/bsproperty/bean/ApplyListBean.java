package com.example.bsproperty.bean;

import java.util.ArrayList;

/**
 * Created by wdxc1 on 2018/3/31.
 */

public class ApplyListBean extends BaseResponse {
    private ArrayList<ApplyBean> data;

    public ArrayList<ApplyBean> getData() {
        return data;
    }

    public void setData(ArrayList<ApplyBean> data) {
        this.data = data;
    }
}
