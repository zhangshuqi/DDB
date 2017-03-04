package com.ljwj.ddb.taimian.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.bean.NewRoomBean;
import com.ljwj.ddb.taimian.bean.PicturesBeean;

import java.util.List;

/**
 * Created by dell on 2017/3/2.
 */

//自定义适配器
public class PictureAdapter extends BaseAdapter {
        private List<PicturesBeean> mPicturesList;
        private Context mContext;

        public PictureAdapter( Context context,List picturesList) {
            mContext=context;
            mPicturesList=picturesList;
        }

        @Override
        public int getCount()
        {
                return mPicturesList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return mPicturesList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                LayoutInflater from = LayoutInflater.from(mContext);//获取布局填充器
                convertView = from.inflate(R.layout.picture_item, null);
                viewHolder = new ViewHolder();
                viewHolder.image = (ImageView) convertView.findViewById(R.id.picture_iv);
                viewHolder.tvCamera= (TextView) convertView.findViewById(R.id.tv_camera);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (position==0){
                viewHolder.tvCamera.setVisibility(View.VISIBLE);
                viewHolder.image.setVisibility(View.GONE);
            }else {
                viewHolder.tvCamera.setVisibility(View.GONE);
                viewHolder.image.setVisibility(View.VISIBLE);
                PicturesBeean picturesB = mPicturesList.get(position);
                viewHolder.image.setImageDrawable(picturesB.getmImag());
            }
            return convertView;
        }

    class ViewHolder
    {
        public ImageView image;
        public TextView tvCamera;
    }
}
