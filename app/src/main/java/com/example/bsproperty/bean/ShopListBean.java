package com.example.bsproperty.bean;

import java.util.ArrayList;
import java.util.List;

public class ShopListBean extends BaseResponse {

	private ArrayList<ShopBean> data;

	public ArrayList<ShopBean> getData() {
		return data;
	}

	public void setData(ArrayList<ShopBean> data) {
		this.data = data;
	}
}
