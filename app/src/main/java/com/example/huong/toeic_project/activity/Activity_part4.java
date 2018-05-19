package com.example.huong.toeic_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.huong.toeic_project.dialog.Dialog_KQ;
import com.example.huong.toeic_project.adapter.QuestionAdapter;
import com.example.huong.toeic_project.classes.QuestionPart3_4;
import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.SQLForQuestion;
import com.example.huong.toeic_project.classes.Status;

import java.util.ArrayList;

public class Activity_part4 extends AppCompatActivity {
    ListView listViewpart4;
    SeekBar seekBarPart4;
    ImageView imgPlay;
    TextView tvQuestion,tvTime;
    EditText edtAnswer;
    Button btnStatus;
    //
    MediaPlayer mediaPlayer;
    android.os.Handler seekHandler = new android.os.Handler();
    Runnable run = new Runnable() { @Override public void run() { seekUpdation(); } };
    public void seekUpdation() {
        int second=mediaPlayer.getCurrentPosition()/1000;
        seekBarPart4.setProgress(second);
        seekHandler.postDelayed(run, 1000);
        int realSecond=second%60;
        int realFullSecond=(mediaPlayer.getDuration()/1000) % 60;
        String strTime=String.valueOf(second/60)+":"+String.valueOf(realSecond);
        String strFullTime=String.valueOf(mediaPlayer.getDuration()/1000/60)+":"+String.valueOf(realFullSecond);
        tvTime.setText(strTime+"/"+strFullTime);
    }
    //
    ArrayList<QuestionPart3_4> questionPart4=new ArrayList<>();
    int ID=2;
    int count=70;
    boolean review=false;
    //Mang luu trang thai lam bai
    Status status=new Status();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part4);
        edtAnswer=(EditText)findViewById(R.id.edtAnswer);
        tvQuestion=(TextView)findViewById(R.id.tvQs) ;
        tvTime=(TextView)findViewById(R.id.tvAudioTime);
        listViewpart4=(ListView)findViewById(R.id.listViewPart4);
        seekBarPart4=(SeekBar)findViewById(R.id.seekBarPart4);
        imgPlay=(ImageView)findViewById(R.id.imgPlayPart4);
        btnStatus=(Button)findViewById(R.id.btnStatus) ;
        //----
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_KQ dialog_status=new Dialog_KQ(status);
                dialog_status.show(getFragmentManager(),"");
            }
        });

        //----
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.part4);
        seekBarPart4.setMax(mediaPlayer.getDuration()/1000);
        seekUpdation();
        seekBarPart4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                    mediaPlayer.seekTo(i*1000);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mediaPlayer.isPlaying()) {
                    imgPlay.setImageResource(R.drawable.icon_stop);
                    mediaPlayer.start();
                }
                else {
                    imgPlay.setImageResource(R.drawable.icon_play);
                    mediaPlayer.pause();
                }
            }
        });
        //data
        final SQLForQuestion SQLForQuestion =new SQLForQuestion(Activity_part4.this);
        questionPart4= SQLForQuestion.getQuestion_Part4();
        //tiền xử lí status
        Intent callingIntent=getIntent();
        review=callingIntent.getBooleanExtra("AllowWatchRes",false);
        if (!review)
            btnStatus.setVisibility(View.GONE);
        else
            btnStatus.setVisibility(View.VISIBLE);
        status=(Status)callingIntent.getSerializableExtra("status");
        for (int i=0;i<30;i++)
            status.setKey(70+i,questionPart4.get(i).getCorrectAnswer());
        //list view
        ArrayList<QuestionPart3_4> question_parts=new ArrayList<>();
        QuestionPart3_4 question=new QuestionPart3_4();
        question=questionPart4.get(0);
        question_parts.add(question);
        question=new QuestionPart3_4();
        question=questionPart4.get(1);
        question_parts.add(question);
        question=new QuestionPart3_4();
        question=questionPart4.get(2);
        question_parts.add(question);
        QuestionAdapter questionAdapter=new QuestionAdapter(Activity_part4.this,this,question_parts,count,status);
        listViewpart4.setAdapter(questionAdapter);
        //xu li list khi nguoi dung chon dap an

        edtAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String note=edtAnswer.getText().toString();
                int choice=Integer.parseInt(note.substring(0,note.indexOf("-")) ); //lua chon cua nguoi dung
                int idQuestion=Integer.parseInt(note.substring(note.indexOf("-")+1,note.length())); //thu tu cua cau hoi
                status.setCustomerAnswer(idQuestion,choice);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                if(count==70){
                    Intent intent =new Intent(getApplicationContext(),Activity_part3.class);
                    intent.putExtra("status",status);
                    intent.putExtra("AllowWatchRes",review);
                    startActivity(intent);
                    finish();
                    mediaPlayer.stop();
                }else
                {
                    count=count-3;
                    int k=count+3;
                    tvQuestion.setText("Question " + (count+1)+"-"+k);
                    ArrayList<QuestionPart3_4> question_parts=new ArrayList<>();
                    QuestionPart3_4 question=new QuestionPart3_4();
                    ID=ID-5;
                    question=questionPart4.get(ID);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart4.get(ID+1);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart4.get(ID+2);
                    ID=ID+2;
                    question_parts.add(question);
                    QuestionAdapter questionAdapter=new QuestionAdapter(Activity_part4.this,this,question_parts,count,status);
                    listViewpart4.setAdapter(questionAdapter);
                }

                return true;
            case R.id.next:
                if(count<=94) {
                    count=count+3;
                    int k=count+3;
                    tvQuestion.setText("Question " + (count+1)+"-"+k);
                    ArrayList<QuestionPart3_4> question_parts=new ArrayList<>();
                    QuestionPart3_4 question=new QuestionPart3_4();
                    question=questionPart4.get(ID+1);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart4.get(ID+2);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart4.get(ID+3);
                    ID=ID+3;
                    question_parts.add(question);
                    QuestionAdapter questionAdapter=new QuestionAdapter(Activity_part4.this,this,question_parts,count,status);
                    listViewpart4.setAdapter(questionAdapter);

                }else {
                    if (review)
                    {
                        mediaPlayer.stop();
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Activity_result.class);
                        intent.putExtra("status", status);
                        startActivity(intent);
                        finish();
                        mediaPlayer.stop();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK)
        {
            dialog_exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void sendToMain(Status data,int resultCode)  //quay lai Activity_part va mang theo trang thai
    {
        Intent intent=getIntent();
        intent.putExtra("status",data);
        setResult(resultCode,intent);
        finish();
    }

    public void dialog_exit(){
        AlertDialog.Builder builder=new AlertDialog.Builder(Activity_part4.this);
        builder.setTitle("Thông báo");
        if (review)
            builder.setMessage("Quay về trang chính?");
        else
            builder.setMessage("Bạn muốn hủy bài làm?");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mediaPlayer.stop();
                if (review)
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                else
                    sendToMain(status, Activity_part.IN_PROGESS);
            }
        });
        builder.show();

    }
}
