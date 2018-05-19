package com.example.huong.toeic_project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huong.toeic_project.classes.KetQua;
import com.example.huong.toeic_project.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Huong on 7/4/2016.
 */
public class Adapter_savedPoint extends BaseAdapter {
    Context context;
    ArrayList<KetQua> ketQuas =new ArrayList<>();
    ArrayList<KetQua> fullList=new ArrayList<>();
    public Adapter_savedPoint(Context context, ArrayList<KetQua> l){
        ketQuas=l;
        fullList=l;
        this.context=context;
    }
    @Override
    public int getCount() {
        return ketQuas.size();
    }

    @Override
    public Object getItem(int position) {
        return ketQuas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.listitemp_kq,null);
        }
       KetQua ketQua=ketQuas.get(position);
        TextView tvName=(TextView)convertView.findViewById(R.id.tvName);
        TextView tvTime=(TextView)convertView.findViewById(R.id.tvTime);
        TextView tvScore=(TextView)convertView.findViewById(R.id.tvScore);
        TextView tvIcon=(TextView) convertView.findViewById(R.id.imgKQ_itemp);
        tvName.setText(ketQua.getName());
        tvScore.setText("Điểm: "+ketQua.getScore());
        tvTime.setText(ketQua.getTime());
        Random random=new Random();
        int color= Color.argb(0,0,random.nextInt(),random.nextInt(255));
        tvIcon.setBackgroundColor(color);
        tvIcon.setText(ketQua.getName().charAt(0)+"");

        return convertView;
    }
}
