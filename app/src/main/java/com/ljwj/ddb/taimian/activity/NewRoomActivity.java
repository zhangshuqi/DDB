package com.ljwj.ddb.taimian.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.adapter.PictureAdapter;
import com.ljwj.ddb.taimian.bean.NewRoomBean;
import com.ljwj.ddb.taimian.bean.NewRoomClientDataDao;
import com.ljwj.ddb.taimian.bean.PicturesBeean;
import com.ljwj.ddb.taimian.utils.AndroidBugWorkaround;
import com.ljwj.ddb.taimian.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class NewRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_CODE = 1;
    private static final int RESULT_CAMERA = 2;
    private ImageView picture;
    private Bitmap mBitmap;
    private Uri imageUri;
    private ImageView newroom_backs_iv;
    private TextView newrooms_name_tv;
    private TextView newroom_title_tv;
    private GridView newroom_gv;
    private LinearLayout newroom_newtable_ll;
    private String TAG = "NewRoomActivity";
    private String name, title, uid;
    private ArrayList<PicturesBeean> list;
    private PictureAdapter pa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newroom_activity);
        AndroidBugWorkaround.assistActivity(findViewById(R.id.newrooactivity), 40);
        //初始化
        initView();
        initData();
        initClick();
    }

    private void initData() {
        Intent intent = getIntent();
        name = intent.getStringExtra(Constant.NAME);
        title = intent.getStringExtra(Constant.TITLE);
        uid = intent.getStringExtra(Constant.UID);
        newrooms_name_tv.setText(name);
        newroom_title_tv.setText(title);
        list = new ArrayList<>();
        //适配器
        PicturesBeean bean = new PicturesBeean();
        list.add(bean);
        pa = new PictureAdapter(this, list);
        newroom_gv.setAdapter(pa);
        queryData(uid);
    }

    private void queryData(String id) {
        //通过当前的Id查找响应的图片
        //房间类型的数据库
        NewRoomClientDataDao ncd = new NewRoomClientDataDao(NewRoomActivity.this);

        List<NewRoomBean> queryList = ncd.query(id);
        try {
            for (NewRoomBean bean : queryList
                    ) {
                String uriString = bean.getmImag();
                Uri parse = Uri.parse(uriString);
                mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                //imgByte(mBitmap);
                BitmapDrawable img = new BitmapDrawable(mBitmap);//转成Drawable
                PicturesBeean picturesBeean = new PicturesBeean();
                picturesBeean.setImage(img);
                list.add(picturesBeean);
            }
        } catch (Exception e) {
        }
    }

    private void initClick() {
        picture.setOnClickListener(this);
        newroom_backs_iv.setOnClickListener(this);
        newroom_newtable_ll.setOnClickListener(this);
        newroom_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    Intent camera_intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(camera_intent, RESULT_CAMERA);
                }
            }
        });
    }

    private void initView() {
        picture = (ImageView) findViewById(R.id.newroom_img_iv);
        newroom_backs_iv = (ImageView) findViewById(R.id.newroom_backs_iv);
        newrooms_name_tv = (TextView) findViewById(R.id.newrooms_name_tv);
        newroom_title_tv = (TextView) findViewById(R.id.newroom_title_tv);
        newroom_gv = (GridView) findViewById(R.id.newroom_gv);

        newroom_newtable_ll = (LinearLayout) findViewById(R.id.newroom_newtable_ll);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //调用系统相机功能
//            case R.id.newroom_camera_iv:
//                Intent camera_intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                startActivity(camera_intent);
//                break;
            //调用系统相册功能
            case R.id.newroom_img_iv:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_CODE);
                break;
//            case R.id.iv_bitmap:
//                if(iamgeUri!=null){
//                    Intent image_intent = new Intent(this,ImageActivity.class);
//                    image_intent.setData(iamgeUri);
//                    startActivity(image_intent);
//                }
//                break;
            case R.id.newrooms_backs_iv:
                finish();
                break;
            case R.id.newroom_newtable_ll:
                //新建台面
                Toast.makeText(this, "新建台面", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //系统返回结果回调的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //RESULT_CODE判断是否对应的请求码，RESULT_OK有没有选取图片，data不等于空
        try {
            if (requestCode == RESULT_CODE && resultCode == Activity.RESULT_OK && data != null) {
                //获取到图片的Uri
                imageUri = data.getData();
                savaImage(imageUri);
                //获取到的图片
                mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                //imgByte(mBitmap);
                BitmapDrawable img = new BitmapDrawable(mBitmap);//转成Drawable
                setImaAdapter(img);//设置参数到适配器上


            } else if (requestCode == RESULT_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                if (uri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        mBitmap = (Bitmap) bundle.get("data"); //get bitmap
                        BitmapDrawable img = new BitmapDrawable(mBitmap);//转成Drawable
                        setImaAdapter(img);//设置参数到适配器上
                    } else {

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将图片的uri更新到对应ID数据库中

    private void savaImage(Uri uri) {
        //更新到数据库
        NewRoomClientDataDao ncd = new NewRoomClientDataDao(NewRoomActivity.this);
        ContentValues values = new ContentValues();
        values.put("img", uri.toString());//正式客户
        //将图片的uri更新到对应ID数据库中
        ncd.updata(values, uid);
    }

    //设置drawable到适配器
    private void setImaAdapter(Drawable img) {
        PicturesBeean pt = new PicturesBeean();
        pt.setImage(img);

        list.add(pt);

        pa.notifyDataSetChanged();
    }

    private void imgByte(final Bitmap mBitmap) {
        if (mBitmap == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                byte[] bytes = os.toByteArray();
                //更新数据库
                NewRoomClientDataDao ncd = new NewRoomClientDataDao(NewRoomActivity.this);
                //通过用户原来的ID修改客户类型及服务器返回来的ID
                ContentValues values = new ContentValues();
                values.put("img", bytes);//正式客户
                //数据更新
                ncd.updata(values, uid); //临时客户的ID
            }
        }).start();
    }

}
