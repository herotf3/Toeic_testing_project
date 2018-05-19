package com.example.huong.toeic_project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huong.toeic_project.adapter.Adapter_savedPoint;
import com.example.huong.toeic_project.classes.KetQua;
import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.SQLForSave;

import java.util.ArrayList;

public class Activity_savedPoint extends AppCompatActivity {
    ListView listViewKQ;
    ArrayList<KetQua> ketQuas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ket_qua);
        listViewKQ=(ListView)findViewById(R.id.listViewKQ);

        final SQLForSave mySQLite=new SQLForSave(Activity_savedPoint.this);  //lay database
        try {
            ketQuas=mySQLite.getKQ();
        } catch (Exception e) {
            Toast.makeText(Activity_savedPoint.this, "Chưa có bài làm nào", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Adapter_savedPoint adapterSavedPoint =new Adapter_savedPoint(Activity_savedPoint.this,ketQuas);
        listViewKQ.setAdapter(adapterSavedPoint);

    }
}
