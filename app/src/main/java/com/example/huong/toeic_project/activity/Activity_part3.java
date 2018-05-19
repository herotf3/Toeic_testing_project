package com.example.huong.toeic_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
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

public class Activity_part3 extends AppCompatActivity {
    ListView listViewpart3;
    SeekBar seekBarPart3;
    ImageView imgPlay;
    TextView tvQuestion,tvTime;
    EditText edtAnwser;
    Button btnStatus;
    //
    MediaPlayer mediaPlayer;
    Handler seekHandler = new Handler();
    Runnable run = new Runnable() { @Override public void run() { seekUpdation(); } };

    public void seekUpdation() {
        int second=mediaPlayer.getCurrentPosition()/1000;
        seekBarPart3.setProgress(second);
        seekHandler.postDelayed(run, 1000);
        int realSecond=second%60;
        int realFullSecond=(mediaPlayer.getDuration()/1000) % 60;
        String strTime=String.valueOf(second/60)+":"+String.valueOf(realSecond);
        String strFullTime=String.valueOf(mediaPlayer.getDuration()/1000/60)+":"+String.valueOf(realFullSecond);
        tvTime.setText(strTime+"/"+strFullTime);
    }
    //
    ArrayList<QuestionPart3_4> questionPart3=new ArrayList<>();
    int count=40;   //index of first question in list (in group of three questions)
    int ID=2;
    boolean review=false;
    //
    Status status=new Status();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part3);
        seekBarPart3=(SeekBar)findViewById(R.id.seekBarPart3);
        imgPlay=(ImageView)findViewById(R.id.imgPlayPart3);
        tvQuestion=(TextView)findViewById(R.id.tvQs) ;
        tvTime=(TextView)findViewById(R.id.tvAudioTime);
        edtAnwser=(EditText)findViewById(R.id.edtAnwser);
        btnStatus=(Button)findViewById(R.id.btnStatus) ;
        //----
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_KQ dialog_status=new Dialog_KQ(status);
                dialog_status.show(getFragmentManager(),"");
            }
        });

        //get data
        final SQLForQuestion SQLForQuestion =new SQLForQuestion(Activity_part3.this);  //lay database
        questionPart3= SQLForQuestion.getQuestion_Part3();
        //tiền xử lí status
        Intent callingIntent=getIntent();
        review=callingIntent.getBooleanExtra("AllowWatchRes",false);
        if (!review)
            btnStatus.setVisibility(View.GONE);
        else
            btnStatus.setVisibility(View.VISIBLE);
        status=(Status)callingIntent.getSerializableExtra("status");
        for (int i=0;i<30;i++)
            status.setKey(40+i,questionPart3.get(i).getCorrectAnswer());
        //list view
        listViewpart3=(ListView)findViewById(R.id.listViewPart3);
        ArrayList<QuestionPart3_4> question_parts=new ArrayList<>();
        QuestionPart3_4 question=new QuestionPart3_4();
        question=questionPart3.get(count-40);
        question_parts.add(question);
        question=new QuestionPart3_4();
        question=questionPart3.get(count-40+1);
        question_parts.add(question);
        question=new QuestionPart3_4();
        question=questionPart3.get(count-40+2);
        question_parts.add(question);
        QuestionAdapter questionAdapter=new QuestionAdapter(Activity_part3.this,this,question_parts,count,status);
        listViewpart3.setAdapter(questionAdapter);
        //xu li list khi nguoi dung chon dap an

        edtAnwser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String note=edtAnwser.getText().toString();
                int choice=Integer.parseInt(note.substring(0,note.indexOf("-")) ); //lua chon cua nguoi dung
                int idQuestion=Integer.parseInt(note.substring(note.indexOf("-")+1,note.length())); //thu tu cua cau hoi
                status.setCustomerAnswer(idQuestion,choice);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        //am thanh
        mediaPlayer = MediaPlayer.create(Activity_part3.this, R.raw.part3);
        seekBarPart3.setMax(mediaPlayer.getDuration()/1000);
        seekUpdation();
        seekBarPart3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean  fromUser) {
                if(fromUser==true){
                    mediaPlayer.seekTo(progress*1000);
                }
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
                if(count==40){
                    Intent intent =new Intent(getApplicationContext(),Activity_part2.class);
                    intent.putExtra("status",status);
                    intent.putExtra("AllowWatchRes",review);
                    intent.putExtra("count",40);
                    startActivity(intent);
                    mediaPlayer.stop();
                    finish();
                }else
                {
                    count=count-3;
                    int k=count+3;
                    tvQuestion.setText("Question " + (count+1)+"-"+k);
                    ArrayList<QuestionPart3_4> question_parts=new ArrayList<>();
                    QuestionPart3_4 question=new QuestionPart3_4();
                    ID=ID-5;
                    question=questionPart3.get(ID);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart3.get(ID+1);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart3.get(ID+2);
                    ID=ID+2;

                    question_parts.add(question);
                    QuestionAdapter questionAdapter=new QuestionAdapter(Activity_part3.this,this,question_parts,count,status);
                    listViewpart3.setAdapter(questionAdapter);
                }
                return true;
            case R.id.next:
                if(count<=64) {
                    count=count+3;
                    int k=count+3;
                    tvQuestion.setText("Question " + (count+1)+" - "+k);

                    ArrayList<QuestionPart3_4> question_parts=new ArrayList<>();
                    QuestionPart3_4 question=new QuestionPart3_4();
                    question=questionPart3.get(ID+1);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart3.get(ID+2);
                    question_parts.add(question);
                    question=new QuestionPart3_4();
                    question=questionPart3.get(ID+3);
                    ID=ID+3;
                    question_parts.add(question);
                    QuestionAdapter questionAdapter=new QuestionAdapter(Activity_part3.this,this,question_parts,count,status);
                    listViewpart3.setAdapter(questionAdapter);
                }else {

                    Intent intent = new Intent(getApplicationContext(), Activity_part4.class);
                    intent.putExtra("status",status);
                    intent.putExtra("AllowWatchRes",review);
                    startActivity(intent);
                    finish();
                    mediaPlayer.stop();
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
        AlertDialog.Builder builder=new AlertDialog.Builder(Activity_part3.this);
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