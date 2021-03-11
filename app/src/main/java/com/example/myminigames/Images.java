package com.example.myminigames;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Images extends BaseAdapter {

    private Context context;

    public Images(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        if(convertView == null){
            img = new ImageView(this.context);
            img.setLayoutParams(new GridView.LayoutParams(105, 120));
            img.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else img = (ImageView) convertView;
        img.setImageResource(R.drawable.dos);
        return img;
    }
}
