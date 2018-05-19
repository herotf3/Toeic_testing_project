package com.example.huong.toeic_project.activity;

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
import android.widget.Toast;

import com.example.huong.toeic_project.dialog.Dialog_KQ;
import com.example.huong.toeic_project.classes.QuestionPart2;
import com.example.huong.toeic_project.R;
import com.example.huong.toeic_project.SQLForQuestion;
import com.example.huong.toeic_project.classes.Status;

import java.util.ArrayList;

public class Activity_part2 extends AppCompatActivity implements View.OnClickListener {
    SeekBar seekBarPart2;
    ImageView imgPlay;
    Button btnStatus;
    TextView tvTime;
    //
    ArrayList<QuestionPart2> listQuestionP2=new ArrayList<QuestionPart2>();
    //
    MediaPlayer mediaPlayer;
    Handler seekHandler = new Handler();
    Runnable run = new Runnable() { @Override public void run() { seekUpdation(); } };

    public void seekUpdation() {
        int second=mediaPlayer.getCurrentPosition()/1000;
        seekBarPart2.setProgress(second);
        seekHandler.postDelayed(run, 1000);
        int realSecond=second%60;
        int realFullSecond=(mediaPlayer.getDuration()/1000) % 60;
        String strTime=String.valueOf(second/60)+":"+String.valueOf(realSecond);
        String strFullTime=String.valueOf(mediaPlayer.getDuration()/1000/60)+":"+String.valueOf(realFullSecond);
        tvTime.setText(strTime+"/"+strFullTime);
    }
    //
    boolean review=false;
    int count=11;
    TextView tvQuestion;
    RadioButton rbtnA,rbtnB,rbtnC,rbtnD;
    private int[] dapAn={2,3,1,1,2,1,2,3,1,2,3,1,1,3,2,3,2,2,3,1,2,1,3,2,3,1,3,2,3,2};
    //Mang luu trang thai lam bai
    Status status=new Status();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part2);
        tvQuestion=(TextView)this.findViewById(R.id.tvQuestion_part2) ;
        tvTime=(TextView)findViewById(R.id.tvAudioTime);
        rbtnA=(RadioButton)findViewById(R.id.rbtnA_2) ;
        rbtnB=(RadioButton)findViewById(R.id.rbtnB_2) ;
        rbtnC=(RadioButton)findViewById(R.id.rbtnC_2);
        btnStatus=(Button)findViewById(R.id.btnStatus) ;
        rbtnA.setOnClickListener(this);
        rbtnC.setOnClickListener(this);
        rbtnB.setOnClickListener(this);
        //----
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_KQ dialog_status=new Dialog_KQ(status);
                dialog_status.show(getFragmentManager(),"");
            }
        });

        //get data
        SQLForQuestion sqlForQuestion=new SQLForQuestion(Activity_part2.this);
        listQuestionP2=sqlForQuestion.getQuestion_Part2();
        Intent callIntent=getIntent();

        review=callIntent.getBooleanExtra("AllowWatchRes",false);
        if (!review)
            btnStatus.setVisibility(View.GONE);
        else
            btnStatus.setVisibility(View.VISIBLE);

        status=(Status)callIntent.getSerializableExtra("status");
        for (int i=0;i<30;i++)
            status.setKey(i,listQuestionP2.get(i).getCorrectAnswer());

        callIntent.getIntExtra("count",11);
        tvQuestion.setText("Question " + count);
        syncRbtnWithStatus();
        //views

        seekBarPart2=(SeekBar)findViewById(R.id.seekBarPart2);
        imgPlay=(ImageView)findViewById(R.id.imgPlay_part2);
        //am thanh
        mediaPlayer = MediaPlayer.create(Activity_part2.this, R.raw.part2);
        seekBarPart2.setMax(mediaPlayer.getDuration()/1000);
        seekUpdation();
        seekBarPart2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                if(count==11){
                    Intent intent =new Intent(getApplicationContext(),Activity_part1.class);
                    intent.putExtra("status",status);
                    intent.putExtra("AllowWatchRes",review);
                    intent.putExtra("count",9);
                    startActivity(intent);
                    finish();
                }else
                {
                    count=count-1;
                    tvQuestion.setText("Question " + count);
                    syncRbtnWithStatus();
                }
                return true;
            case R.id.next:
                if(count<40) {
                    count++;
                    tvQuestion.setText("Question " + count);
                    syncRbtnWithStatus();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Activity_part3.class);
                    intent.putExtra("status",status);
                    intent.putExtra("AllowWatchRes",review);
                    intent.putExtra("count",40);
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
        AlertDialog.Builder builder=new AlertDialog.Builder(Activity_part2.this);
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
        switch (v.getId()){
            case R.id.rbtnA_2:
                setDisplayCheckAnwser(rbtnA,R.drawable.a1);
                status.setCustomerAnswer(count-1,1);
                return;
            case R.id.rbtnB_2:
                setDisplayCheckAnwser(rbtnB,R.drawable.b1);
                status.setCustomerAnswer(count-1,2);
                return;
            case R.id.rbtnC_2:
                setDisplayCheckAnwser(rbtnC,R.drawable.c1);
                status.setCustomerAnswer(count-1,3);
                return;
        }
    }

    public void resetRbtn(){
        rbtnA.setBackgroundResource(R.drawable.a);
        rbtnB.setBackgroundResource(R.drawable.b);
        rbtnC.setBackgroundResource(R.drawable.c);
        rbtnA.setChecked(false);
        rbtnB.setChecked(false);
        rbtnC.setChecked(false);
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
            default:
                resetRbtn();
        }
    }

}
