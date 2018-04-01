package com.example.bsproperty.bean;

import java.util.ArrayList;

/**
 * Created by wdxc1 on 2018/3/28.
 */

public class OrderListBean extends BaseResponse {
    private ArrayList<OrderBean> data;

    public ArrayList<OrderBean> getData() {
        return data;
    }

    public void setData(ArrayList<OrderBean> data) {
        this.data = data;
    }
}
