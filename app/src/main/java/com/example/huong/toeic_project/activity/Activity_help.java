package com.example.huong.toeic_project.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huong.toeic_project.R;

public class Activity_help extends AppCompatActivity {
    TextView tvPart1,tvPart2,tvPart3,tvPart4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvPart1=(TextView)findViewById(R.id.tvHelep_p1);
        tvPart2=(TextView)findViewById(R.id.tvHelep_p2);
        tvPart3=(TextView)findViewById(R.id.tvHelep_p3);
        tvPart4=(TextView)findViewById(R.id.tvHelep_p4);
        tvPart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(Activity_help.this);
                dialog.setContentView(R.layout.dialog_help1);
                dialog.show();

            }
        });
        tvPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(Activity_help.this);
                dialog.setContentView(R.layout.dialog_hepl2);

                dialog.show();
            }
        });
        tvPart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(Activity_help.this);
                dialog.setContentView(R.layout.dialog_help3);

                dialog.show();
            }
        });
        tvPart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(Activity_help.this);
                dialog.setContentView(R.layout.dialog_help3);
                TextView tv=(TextView)dialog.findViewById(R.id.tvHelp_part3) ;
                tv.setText("Part 4");
                dialog.show();
            }
        });
    }
}
