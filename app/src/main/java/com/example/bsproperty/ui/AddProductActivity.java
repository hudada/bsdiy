package com.example.bsproperty.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.utils.LQRPhotoSelectUtils;
import com.zhy.http.okhttp.builder.PostFileBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.request.PostFileRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class AddProductActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.cb_isact)
    CheckBox cbIsact;
    @BindView(R.id.et_act_price)
    EditText etActPrice;
    @BindView(R.id.rl_isact)
    RelativeLayout rlIsact;
    @BindView(R.id.iv_img)
    ImageView ivImg;

    private boolean isAct;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private File mFile;
    private long mSid;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("新增商品");
        btnRight.setText("保存");
        btnRight.setVisibility(View.VISIBLE);
        cbIsact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAct = isChecked;
                if (isAct) {
                    rlIsact.setVisibility(View.VISIBLE);
                } else {
                    rlIsact.setVisibility(View.GONE);
                }
            }
        });
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                Glide.with(mContext).load(outputUri).into(ivImg);
                mFile = outputFile;
            }
        }, false);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_add_product;
    }

    @Override
    protected void loadData() {
        mSid = getIntent().getLongExtra("sid", 0);
    }

    @OnClick({R.id.btn_back, R.id.btn_right, R.id.iv_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_right:
                if (!checkEditEmpty(etName, etInfo, etPrice)) {
                    if (mFile == null) {
                        showToast("请选择图片");
                        return;
                    }
                    String name = etName.getText().toString().trim();
                    String info = etInfo.getText().toString().trim();
                    String price = etPrice.getText().toString().trim();
                    PostFormBuilder postFormBuilder = OkHttpTools.postFile(mContext, ApiManager.PRODUCT_ADD,
                            "file", mFile)
                            .addParams("sid", mSid + "")
                            .addParams("name", name)
                            .addParams("info", info)
                            .addParams("price", price);
                    if (isAct) {
                        if (!checkEditEmpty(etActPrice)) {
                            String actProce = etActPrice.getText().toString().trim();
                            postFormBuilder.addParams("isActivity", true + "")
                                    .addParams("actProce", actProce);
                        }else{
                            dismissDialog();
                            return;
                        }
                    } else {
                        postFormBuilder.addParams("isActivity", false + "");
                    }
                    postFormBuilder.build()
                            .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                @Override
                                public void onResponse(BaseResponse baseResponse) {
                                    showToast("添加成功");
                                    finish();
                                }
                            });
                }
                break;
            case R.id.iv_img:
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setItems(new String[]{
                        "拍照选择", "本地相册选择", "取消"
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                PermissionGen.with((Activity) mContext)
                                        .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA
                                        ).request();
                                break;
                            case 1:
                                PermissionGen.needPermission((Activity) mContext,
                                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                );
                                break;
                            case 2:
                                break;
                        }
                    }
                }).show();
                break;
        }
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("权限申请");
        builder.setMessage("在设置-应用-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
}
