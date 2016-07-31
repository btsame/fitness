package com.dkjs.fitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dkjs.fitness.R;

/**
 * Created by Administrator on 2016/7/31.
 */
public class PicSelectAdapter extends BaseAdapter {

    Context mContext;
    private String[] itemNames = new String[]{"拍照", "相册"};
    private int[] itemPics = new int[]{android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_gallery};

    public PicSelectAdapter() {
    }

    public PicSelectAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return itemNames.length;
    }

    @Override
    public Object getItem(int position) {
        return itemNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView = layoutInflater.inflate(R.layout.item_pic_select, null);
        ((ImageView) itemView.findViewById(R.id.iv_pic_select)).setImageResource(itemPics[position]);
        ((TextView) itemView.findViewById(R.id.tv_pic_select)).setText(itemNames[position]);
        return itemView;
    }
}