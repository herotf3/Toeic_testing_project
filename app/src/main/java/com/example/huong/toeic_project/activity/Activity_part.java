package com.example.huong.toeic_project.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huong.toeic_project.adapter.PartAdapter;
import com.example.huong.toeic_project.classes.PartList;
import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.classes.Status;

import java.util.ArrayList;

public class Activity_part extends AppCompatActivity {
    final static int MOVE_PART=111;
    final static int IN_PROGESS=100;
    ListView listViewPart;
    ArrayList<PartList> parts=new ArrayList<>();
    TextView tvPart;
    PartAdapter partAdapter=new PartAdapter(this,parts);
    //Mang luu trang thai lam bai
    Status status=new Status();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);

        tvPart=(TextView)findViewById(R.id.tvPart) ;
        listViewPart=(ListView)findViewById(R.id.listViewPart);
        addPart();
        listViewPart.setAdapter(partAdapter);
        listViewPart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sendingIntent=new Intent();
                switch (position){
                    case 0:
                        sendingIntent=new Intent(getApplicationContext(),Activity_part1.class);
                        break;
                    case 1:
                        sendingIntent=new Intent(getApplicationContext(),Activity_part2.class);
                        break;
                    case 2:
                        sendingIntent =new Intent(getApplicationContext(),Activity_part3.class);
                        break;
                    case 3:
                        sendingIntent =new Intent(getApplicationContext(),Activity_part4.class);
                        break;
                }
                sendingIntent.putExtra("status",status);
                sendingIntent.putExtra("count",0);
                if (sendingIntent!=null)
                    startActivityForResult(sendingIntent,MOVE_PART);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null)
            return;
        if (requestCode==MOVE_PART)
        {
            if (resultCode==IN_PROGESS) //dang lam bai (chua nop)
                status=(Status) data.getSerializableExtra("status");    //cap nhat trang thai lam bai
        }
    }

    public void addPart(){
        PartList temp=new PartList();
        temp.setNamePart("Photographs");
        temp.setStt("Part 1");
        temp.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.icon_part1));

        parts.add(temp);
        temp=new PartList();
        temp.setNamePart("Question – Response");
        temp.setStt("Part 2");
        temp.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.icon_part2));

        parts.add(temp);
        temp=new PartList();
        temp.setNamePart("Short Conversations");
        temp.setStt("Part 3");
        temp.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.icon_part3));

        parts.add(temp);
        temp=new PartList();
        temp.setNamePart("Short Talks");
        temp.setStt("Part 4");
        temp.setPic(BitmapFactory.decodeResource(getResources(),R.drawable.icon_part4));

        parts.add(temp);
    }
}
