package personal.edu.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.adapter.Guide_View_Adapter;

/**
 * 引导界面
 * Created by Administrator on 2017/1/3 0003.
 */
public class Activity_Guide extends AppCompatActivity {
    ViewPager vp;
    Button but;
    ArrayList<View> list;
    Guide_View_Adapter gva;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        SkinManager.getInstance().register(this);
        Login();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    void Login(){
        boolean guide=Shade_Main.getGuide(this);
        Intent intent;
        //判断是否为第一次登陆
        if(guide){
            //为true就跳转页面
            intent=new Intent(Activity_Guide.this,Activity_Loding.class);
            Shade_Main.setGuide(Activity_Guide.this,true);
            startActivity(intent);
            finish();
        }else {
            //是第一次登陆就开始加载布局
            inClick();
            setView();
            inData();
        }
    }
    void inClick(){
        vp= (ViewPager) findViewById(R.id.guide_viewpager_vp);
        but= (Button) findViewById(R.id.guide_button_bt);
        but.setVisibility(View.GONE);
    }

    void setView(){
        list=new ArrayList<>();
        //加载ViewPager图片
        View view1= LayoutInflater.from(this).inflate(R.layout.guide_view_item,null);
        view1.setBackgroundResource(R.mipmap.lead_1);
        list.add(view1);
        View view2=LayoutInflater.from(this).inflate(R.layout.guide_view_item,null);
        view2.setBackgroundResource(R.mipmap.lead_2);
        list.add(view2);
        View view3=LayoutInflater.from(this).inflate(R.layout.guide_view_item,null);
        view3.setBackgroundResource(R.mipmap.lead_3);
        list.add(view3);

        gva=new Guide_View_Adapter(this);
        gva.setList(list);
        vp.setAdapter(gva);
    }

    boolean flag;//判断滑动状态
    void inData(){
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case 0:
                        Intent intent;
                        if(flag&&guideView){
                            intent=new Intent(Activity_Guide.this,Activity_Loding.class);
                            Shade_Main.setGuide(Activity_Guide.this,true);
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case 1:
                        flag = true;
                        break;
                    case 2:
                        flag = false;
                        break;
                }
            }
        });
    }

    boolean guideView;
    void setPosition(int position){
        if(position==0){
            guideView=false;
            but.setVisibility(View.GONE);
        }else if(position==1){
            guideView=false;
            but.setVisibility(View.GONE);
        }else if(position==2){
            guideView=true;
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.guide_but_anim);
            but.startAnimation(animation);
            but.setVisibility(View.VISIBLE);
            but.setText("点击开始新闻之旅");
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(Activity_Guide.this, Activity_Loding.class);
                    Shade_Main.setGuide(Activity_Guide.this,true);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

}
