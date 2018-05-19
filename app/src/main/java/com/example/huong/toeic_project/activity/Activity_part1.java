package com.example.huong.toeic_project.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.huong.toeic_project.dialog.Dialog_KQ;
import com.example.huong.toeic_project.classes.QuestionPart1;
import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.SQLForQuestion;
import com.example.huong.toeic_project.classes.Status;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Activity_part1 extends AppCompatActivity implements View.OnClickListener {
    final int MOVING_PART=102;
    MediaPlayer mediaPlayer;
    SeekBar seekBarPart1;
    ImageView imgPlay;
    RadioButton rbtnA,rbtnB,rbtnC,rbtnD;
    ImageView imgQuestion;
    TextView tvQuestion,tvTime;
    Button btnStatus;
    ArrayList<QuestionPart1> listQuestionP1=new ArrayList<QuestionPart1>();
    Status status=new Status(); //Mang luu trang thai lam bai
    int count=0;
    boolean review=false;
    Handler seekHandler = new Handler();
    Runnable run = new Runnable() { @Override public void run() { seekUpdation(); } };

    public void seekUpdation() {
        int second=mediaPlayer.getCurrentPosition()/1000;
        seekBarPart1.setProgress(second);
        seekHandler.postDelayed(run, 1000);
        int realSecond=second%60;
        int realFullSecond=(mediaPlayer.getDuration()/1000) % 60;
        String strTime=String.valueOf(second/60)+":"+String.valueOf(realSecond);
        String strFullTime=String.valueOf(mediaPlayer.getDuration()/1000/60)+":"+String.valueOf(realFullSecond);
        tvTime.setText(strTime+"/"+strFullTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part1);
        tvQuestion=(TextView)findViewById(R.id.tvQuestion_part1);
        tvTime=(TextView)findViewById(R.id.tvAudioTime);
        seekBarPart1=(SeekBar)findViewById(R.id.seekBarPart1);
        imgPlay=(ImageView)findViewById(R.id.imgPlayPart1);
        rbtnA=(RadioButton)findViewById(R.id.rbtnA);
        rbtnC=(RadioButton)findViewById(R.id.rbtnC);
        rbtnB=(RadioButton)findViewById(R.id.rbtnB);
        rbtnD=(RadioButton)findViewById(R.id.rbtnD);
        rbtnA.setOnClickListener(this);
        rbtnB.setOnClickListener(this);
        rbtnC.setOnClickListener(this);
        rbtnD.setOnClickListener(this);
        imgQuestion=(ImageView)findViewById(R.id.imgQuestion);
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
        SQLForQuestion sqlForQuestion=new SQLForQuestion(Activity_part1.this);
        listQuestionP1=sqlForQuestion.getQuestion_Part1();
        //
        Intent callIntent=getIntent();
        review=callIntent.getBooleanExtra("AllowWatchRes",false);
        if (!review)
            btnStatus.setVisibility(View.GONE);
        else
            btnStatus.setVisibility(View.VISIBLE);

        status=(Status)callIntent.getSerializableExtra("status");   //update
        for (int i=0;i<=9;i++)      //input Key for status
            status.setKey(i,listQuestionP1.get(i).getCorrectAnswer());
        //hien thi cau hoi
        callIntent.getIntExtra("count",0);
        displayQuestion(count);
        syncRbtnWithStatus();       //display radio button same as status
        //
        mediaPlayer = MediaPlayer.create(Activity_part1.this, R.raw.part1);
        seekBarPart1.setMax(mediaPlayer.getDuration()/1000);
        seekUpdation();
        seekBarPart1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.icon_stop);
                }
                else if(mediaPlayer.isPlaying()){
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
                if(count==0){
                    dialog_exit();
                }else
                    { count--;
                        displayQuestion(count);
                        syncRbtnWithStatus();
                }
                return true;
            case R.id.next:
                if(count<9) {
                    count++;
                    displayQuestion(count);
                    syncRbtnWithStatus();
                }
                else{
                    Intent intent =new Intent(getApplicationContext(),Activity_part2.class);
                    intent.putExtra("status",status);
                    intent.putExtra("AllowWatchRes",review);
                    intent.putExtra("count",11);
                    startActivity(intent);
                    sendToMain(status,RESULT_CANCELED);
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
        AlertDialog.Builder builder=new AlertDialog.Builder(Activity_part1.this);
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
    public void setDisplayCheckAnwser(RadioButton rbtnX,int idImage)    //hien thi su kien nguoi dung chon dap an X
    {
        RadioButton[] listRdbtn={rbtnA,rbtnB,rbtnC,rbtnD};
        resetRbtn();
        for (RadioButton rbtnI:listRdbtn)
        {
            if (rbtnI==rbtnX)
            {
                rbtnI.setBackgroundResource(idImage);
                rbtnI.setChecked(true);
            }
        }
    }
    @Override
    public void onClick(View v) {
        if (!review) //khong phai dang xem lai bai lam, neu la review thi khong cho chon dap an
        switch (v.getId()){
            case R.id.rbtnA:
                setDisplayCheckAnwser(rbtnA,R.drawable.a1);
                status.setCustomerAnswer(count,1);
                return;
            case R.id.rbtnB:
                setDisplayCheckAnwser(rbtnB,R.drawable.b1);
                status.setCustomerAnswer(count,2);
                return;
            case R.id.rbtnC:
                setDisplayCheckAnwser(rbtnC,R.drawable.c1);
                status.setCustomerAnswer(count,3);
                return;
            case R.id.rbtnD:
                setDisplayCheckAnwser(rbtnD,R.drawable.d1);
                status.setCustomerAnswer(count,4);
                return;
        }
    }
    public void resetRbtn(){
        rbtnA.setBackgroundResource(R.drawable.a);
        rbtnB.setBackgroundResource(R.drawable.b);
        rbtnC.setBackgroundResource(R.drawable.c);
        rbtnD.setBackgroundResource(R.drawable.d);
        rbtnA.setChecked(false);
        rbtnB.setChecked(false);
        rbtnC.setChecked(false);
        rbtnD.setChecked(false);
    }

    public  void syncRbtnWithStatus()    //dong bo trang thai cac rbtn vs status cua cau hoi hien tai
    {
        switch (status.getCustomerAnwser(count))
        {
            case 1://A
                setDisplayCheckAnwser(rbtnA,R.drawable.a1);
                break;
            case 2:
                setDisplayCheckAnwser(rbtnB,R.drawable.b1);
                break;
            case 3:
                setDisplayCheckAnwser(rbtnC,R.drawable.c1);
                break;
            case 4:
                setDisplayCheckAnwser(rbtnD,R.drawable.d1);
                break;
            default:
                resetRbtn();
        }
    }

    public void displayQuestion(int index)
    {
        tvQuestion.setText("Question "+String.valueOf(index+1));
        String imgName=listQuestionP1.get(index).getImageName();
        Context context=getApplicationContext();
        int imgId=context.getResources().getIdentifier(imgName,"drawable",context.getPackageName());
        imgQuestion.setImageResource(imgId);
    }

}
