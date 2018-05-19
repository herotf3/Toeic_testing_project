package com.example.huong.toeic_project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.classes.Status;

/**
 * Created by Huong on 6/30/2016.
 */
public class AdapterStatus extends BaseAdapter {
    Context context;
   Status statuses=new Status();
    Status fullList=new Status();

    public AdapterStatus(Context context, Status l){
        statuses=l;
        fullList=l;
        this.context=context;
    }
    @Override
    public int getCount() {
        return statuses.getKey().length;
    }



    @Override
    public Object getItem(int position) {
        return statuses.getKey()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.listitemp_status,null);
        }


        TextView tvAnswer=(TextView)convertView.findViewById(R.id.tvcustomerAnswer);
        TextView tvID=(TextView)convertView.findViewById(R.id.tvID);
        TextView tvKey=(TextView)convertView.findViewById(R.id.tvKey);
        if (statuses.getCustomerAnwser(position)==statuses.getKey()[position])
            tvAnswer.setTextColor(Color.rgb(2,154,204));
        else
            tvAnswer.setTextColor(Color.rgb(221,22,142));
        switch (statuses.getCustomerAnswer()[position]){
            case 0: tvAnswer.setText("");break;
            case 1:  tvAnswer.setText("A");break;
            case 2:  tvAnswer.setText("B");break;
            case 3:  tvAnswer.setText("C");break;
            case 4:  tvAnswer.setText("D");break;
        }

        switch (statuses.getKey()[position])
        {
            case 0:tvKey.setText("-");break;
            case 1:tvKey.setText("A");break;
            case 2:tvKey.setText("B");break;
            case 3:tvKey.setText("C");break;
            case 4:tvKey.setText("D");break;
        }
        int ID=position+1;
        tvID.setText("Question: "+ID);
        return convertView;
    }
}
