package com.example.huong.toeic_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huong.toeic_project.classes.PartList;
import com.example.huong.toeic_project.R;

import java.util.ArrayList;

/**
 * Created by Huong on 6/25/2016.
 */
public class PartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PartList> parts;
    public PartAdapter(Context context,ArrayList<PartList>l){
        this.context=context;
        parts=l;

    }

    @Override
    public int getCount() {
        return parts.size();
    }

    @Override
    public Object getItem(int position) {
        return parts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.layout_part,null);
        }

        PartList part=parts.get(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.imgPart);
        TextView tvName=(TextView)convertView.findViewById(R.id.tvPartName);
        TextView tvPart=(TextView)convertView.findViewById(R.id.tvPart);


        Bitmap bitmap=part.getPic();
         img.setImageBitmap(bitmap);



        tvName.setText(part.getNamePart());
        tvPart.setText(part.getStt());




        return convertView;
    }


}
