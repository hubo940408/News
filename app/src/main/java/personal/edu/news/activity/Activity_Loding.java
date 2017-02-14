package personal.edu.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import personal.edu.news.R;

/**
 * Loding页面 加载数据
 * Created by Administrator on 2017/1/3 0003.
 */
public class Activity_Loding extends AppCompatActivity {
    ImageView im;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        inClick();
    }

    void inClick(){
        im= (ImageView) findViewById(R.id.loding_im);
        im.setImageResource(R.mipmap.logo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setView();
    }

    void setView(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.loding_im);
        im.setAnimation(animation);
        //页面跳转定时器
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                  handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task,1000);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Intent intent=new Intent(Activity_Loding.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };


}
