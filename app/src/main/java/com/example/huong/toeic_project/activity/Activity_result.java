package com.example.huong.toeic_project.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huong.toeic_project.dialog.Dialog_KQ;
import com.example.huong.toeic_project.dialog.Dialog_getName;
import com.example.huong.toeic_project.classes.KetQua;
import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.classes.Status;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_result extends AppCompatActivity {
    Button btnAgain,btnStatus,btnSave,btnReview;
    TextView tvResult;
    ImageView imgRes;
    Status status=new Status();
    KetQua ketQua=new KetQua();
    NotificationManager notificationManager​;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btnAgain=(Button)findViewById(R.id.btnAgain);
        tvResult=(TextView)findViewById(R.id.tvResult);
        imgRes=(ImageView)findViewById(R.id.imgRes);
        btnStatus=(Button)findViewById(R.id.btnXemDapAn);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnReview=(Button)findViewById(R.id.btnReview);
        //-------
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ketQua.setScore(getTotalPoint(status));

                Date today=new Date(System.currentTimeMillis());
                SimpleDateFormat timeFormat= new SimpleDateFormat();
                String s=timeFormat.format(today.getTime());
                ketQua.setTime(s);
                Dialog_getName Dialog_getName =new Dialog_getName(ketQua);
                Dialog_getName.show(getFragmentManager(),"");
                //----

            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Activity_part1.class);
                intent.putExtra("status",status);
                intent.putExtra("AllowWatchRes",true);
                startActivity(intent);
            }
        });
        //-------
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_KQ dialog_status=new Dialog_KQ(status);
                dialog_status.show(getFragmentManager(),"");
            }
        });
        //
        Intent callIntent=getIntent();
        status=(Status)callIntent.getSerializableExtra("status");
        //
        tvResult.setText(String.valueOf(getTotalPoint(status)));
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    int getTotalPoint(Status status) {
        int count = 0,point=0;
        for (int i = 0; i < 100; i++)
            if (status.getCustomerAnwser(i)!=0 && status.getCustomerAnwser(i) == status.getKey()[i])
                count++;
        int[] checkPoint = new int[101];
        for (int i=0;i<=6;i++)
            checkPoint[i]=5;
        for (int i=7;i<=53;i++)
            checkPoint[i]=5+(i-6)*5;
        for (int i=54;i<=87;i++)
            checkPoint[i]=270+(i-54)*5;
        for (int i=88;i<=92;i++)
            checkPoint[i]=470+(i-88)*5;
        for (int i=93;i<=100;i++)
            checkPoint[i]=495;
        return checkPoint[count];
    }

}

