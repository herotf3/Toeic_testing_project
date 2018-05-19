package com.example.huong.toeic_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.huong.toeic_project.classes.QuestionPart3_4;
import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.classes.Status;

import java.util.ArrayList;

/**
 * Created by Huong on 6/26/2016.
 */
public class QuestionAdapter extends BaseAdapter {
    Activity activity;
    private Context context;
    private ArrayList<QuestionPart3_4> questionPart3s;
    public Status status;
    public int groupNumber; //thứ tự của nhóm câu hỏi : câu hỏi đàu tiên trong nhóm câu hỏi
    public QuestionAdapter(Context context, ArrayList<QuestionPart3_4>l){
        this.context=context;
        questionPart3s=l;
    }
    public QuestionAdapter(Activity a,Context context,ArrayList<QuestionPart3_4> list,int group,Status _status)
    {
        this.activity=a;
        this.context=context;
        this.questionPart3s=list;
        this.status=_status;
        this.groupNumber=group;
    }

    @Override
    public int getCount() {
        return questionPart3s.size();
    }

    @Override
    public Object getItem(int position) {
        return questionPart3s.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.layout_part3,null);
        }
        //xét từng câu hỏi
        QuestionPart3_4 questionPart3=questionPart3s.get(position);

        TextView tvID=(TextView)convertView.findViewById(R.id.tvQuestionpart3);
        TextView tvQuestion=(TextView)convertView.findViewById(R.id.tvQuesition);
        RadioButton rbtnA=(RadioButton)convertView.findViewById(R.id.rbtnA);
        RadioButton rbtnB=(RadioButton)convertView.findViewById(R.id.rbtnB);
        RadioButton rbtnC=(RadioButton)convertView.findViewById(R.id.rbtnC);
        RadioButton rbtnD=(RadioButton)convertView.findViewById(R.id.rbtnD);
        tvID.setText("Question "+questionPart3.getId());
        tvQuestion.setText(questionPart3.getContent());
        rbtnA.setText(questionPart3.getAnswerA());
        rbtnC.setText(questionPart3.getAnswerC());
        rbtnB.setText(questionPart3.getAnswerB());
        rbtnD.setText(questionPart3.getAnswerD());
        final int now=position+groupNumber;   //luu y toan bo la index
        RadioGroup radioGroup=(RadioGroup) convertView.findViewById(R.id.radioGroup);
            switch (status.getCustomerAnwser(now))
            {
                case 1:
                    rbtnA.setChecked(true);
                    rbtnB.setChecked(false);
                    rbtnC.setChecked(false);
                    rbtnD.setChecked(false);
                    break;
                case 2:
                    rbtnA.setChecked(false);
                    rbtnB.setChecked(true);
                    rbtnC.setChecked(false);
                    rbtnD.setChecked(false);
                    break;
                case 3:
                    rbtnD.setChecked(false);
                    rbtnA.setChecked(false);
                    rbtnC.setChecked(true);
                    rbtnB.setChecked(false);
                    break;
                case 4:
                    rbtnA.setChecked(false);
                    rbtnB.setChecked(false);
                    rbtnC.setChecked(false);
                    rbtnD.setChecked(true);
                    break;
                default:
                    radioGroup.clearCheck();
            }

        rbtnA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    EditText edtAwser=(EditText)activity.findViewById(R.id.edtAnswer);
                    edtAwser.setText("1-"+now);
                }
            }
        });
        rbtnB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    EditText edtAwser=(EditText)activity.findViewById(R.id.edtAnswer);
                    edtAwser.setText("2-"+now);
                }
            }
        });
        rbtnC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    EditText edtAwser=(EditText)activity.findViewById(R.id.edtAnswer);
                    edtAwser.setText("3-"+now);
                }
            }
        });
        rbtnD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    EditText edtAwser=(EditText)activity.findViewById(R.id.edtAnswer);
                    edtAwser.setText("4-"+now);
                }
            }
        });
        return convertView;
    }

}
