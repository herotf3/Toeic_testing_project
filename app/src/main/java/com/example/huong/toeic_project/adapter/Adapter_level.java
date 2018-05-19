package com.example.huong.toeic_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huong.toeic_project.classes.ItemMainMenu;
import com.example.huong.toeic_project.R;

import java.util.ArrayList;

/**
 * Created by Huong on 6/25/2016.
 */
public class Adapter_level extends BaseAdapter {
    private Context context;
    private ArrayList<ItemMainMenu> itemMainMenus;
    public Adapter_level(Context context, ArrayList<ItemMainMenu>l){
        this.context=context;
        itemMainMenus =l;

    }
    @Override
    public int getCount() {
        return itemMainMenus.size();
    }

    @Override
    public Object getItem(int position) {
        return itemMainMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.layout_level,null);
        }

        ItemMainMenu itemMainMenu = itemMainMenus.get(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.imgLevel);
        TextView tvName=(TextView)convertView.findViewById(R.id.tvLevel);



        Bitmap bitmap= itemMainMenu.getPicLevel();
        img.setImageBitmap(bitmap);



        tvName.setText(itemMainMenu.getItemName());





        return convertView;
    }

}

