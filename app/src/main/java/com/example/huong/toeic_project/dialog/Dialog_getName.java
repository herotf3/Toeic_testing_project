package com.example.huong.toeic_project.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.SQLForQuestion;
import com.example.huong.toeic_project.SQLForSave;
import com.example.huong.toeic_project.classes.KetQua;

/**
 * Created by Huong on 7/4/2016.
 */
public class Dialog_getName extends DialogFragment {
    com.example.huong.toeic_project.SQLForQuestion SQLForQuestion;
    SQLiteDatabase sqLiteDatabase;
    NotificationManager notificationManager​;
    KetQua ketqua=new KetQua();
    public Dialog_getName(KetQua l){
        ketqua=l;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        final View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_name,null);
        builder.setView(v);
        final EditText edtName=(EditText)v.findViewById(R.id.edtName);
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton("Lưu ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=edtName.getText().toString();
                final SQLForSave mySQLite=new SQLForSave(getActivity());
                mySQLite.InsertKQ(name,ketqua.getTime(),ketqua.getScore());
                Toast.makeText(getActivity(), "Đã lưu", Toast.LENGTH_SHORT).show();
            }


        });
        return builder.create();
    }




}
