package com.example.huong.toeic_project.activity;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;

import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.adapter.Adapter_level;
import com.example.huong.toeic_project.classes.ItemMainMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewLevel;

    ArrayList<ItemMainMenu> itemMainMenus =new ArrayList<>();

    Adapter_level adapterLevel =new Adapter_level(this, itemMainMenus);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewLevel=(ListView)findViewById(R.id.listViewLevel);
        addLevel();

        listViewLevel.setAdapter(adapterLevel);
        listViewLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent =new Intent(getApplicationContext(),Activity_part.class);

                        startActivity(intent);
                        return;
                    case 1:
                        Intent intent4 =new Intent(getApplicationContext(),Activity_savedPoint.class);
                        startActivity(intent4);
                    case 2:
                        Intent intent5 =new Intent(getApplicationContext(),Activity_help.class);
                        startActivity(intent5);
                        return;
                }
            }
        });
    }

    public void addLevel(){
        ItemMainMenu temp=new ItemMainMenu();
        temp.setItemName("Làm bài");
        temp.setPicLevel(BitmapFactory.decodeResource(getResources(),R.drawable.ic_level1));

        /*
        itemMainMenus.add(temp);
        temp=new ItemMainMenu();
        temp.setItemName("ItemMainMenu 2");
        temp.setPicLevel(BitmapFactory.decodeResource(getResources(),R.drawable.ic_level2));

        itemMainMenus.add(temp);
        temp=new ItemMainMenu();
        temp.setItemName("ItemMainMenu 3");
        temp.setPicLevel(BitmapFactory.decodeResource(getResources(),R.drawable.ic_level3));
        */
        itemMainMenus.add(temp);
        temp=new ItemMainMenu();
        temp.setItemName("Kết quả ");
        temp.setPicLevel(BitmapFactory.decodeResource(getResources(),R.drawable.ic_kq));

        itemMainMenus.add(temp);

        temp=new ItemMainMenu();
        temp.setItemName("Trợ giúp");
        temp.setPicLevel(BitmapFactory.decodeResource(getResources(),R.drawable.help));

        itemMainMenus.add(temp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){

        }
        return super.onKeyDown(keyCode, event);
    }
}
