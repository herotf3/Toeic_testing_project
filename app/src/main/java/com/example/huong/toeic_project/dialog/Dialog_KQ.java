package com.example.huong.toeic_project.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;

import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.adapter.AdapterStatus;
import com.example.huong.toeic_project.classes.Status;

/**
 * Created by Huong on 7/4/2016.
 */
public class Dialog_KQ extends DialogFragment {
    Status status=new Status();
    Status originStatus=new Status();
    public Dialog_KQ(Status l){
        status=l;
        originStatus=l;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Các câu bạn đã chọn");
        final View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_status,null);
        builder.setView(v);
        ListView listView=(ListView)v.findViewById(R.id.listViewStatus);
        status=originStatus;
       AdapterStatus ketQuaAdapter=new AdapterStatus(getActivity(),status);
        listView.setAdapter(ketQuaAdapter);
        return builder.create();
    }

}
