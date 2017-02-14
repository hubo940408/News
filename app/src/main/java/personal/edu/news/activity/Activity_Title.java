package personal.edu.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.adapter.Add_Adapter;
import personal.edu.news.adapter.Add_RecyclerView_Adapter;
import personal.edu.news.entity.ViewPager_User;
import personal.edu.news.sjk.TitleExpress;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
public class Activity_Title extends AppCompatActivity {
    Toolbar toolbar;
    Shade_Main sm;
    TitleExpress te;
    ListView lv;
    ArrayList<ViewPager_User> list_lv;
    RecyclerView recyclerView;
    ArrayList<ViewPager_User> list_re;
    Add_RecyclerView_Adapter ara;
    Add_Adapter aad;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtitle);
        SkinManager.getInstance().register(this);
        inClick();
        inData();
        setRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    void setRecyclerView(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,5, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);//设置布局管理器
        list_re=new ArrayList<>();
        list_re=te.check1();
        ara=new Add_RecyclerView_Adapter(this);
        ara.setOnClick(onClick);
        recyclerView.setAdapter(ara);
        ara.setList(list_re);
        ara.notifyDataSetChanged();
    }

    /**
     * 上面recycle人view的点击事件
     */
    Add_RecyclerView_Adapter.OnClick onClick=new Add_RecyclerView_Adapter.OnClick() {
        @Override
        public void onclick(int position) {
            ArrayList<ViewPager_User> arrayList=ara.getList();
            String name=arrayList.get(position).getName();
            ArrayList<ViewPager_User> list=te.checkOne1(name);
            String channelId=list.get(0).getChannelId();
            ViewPager_User viewPager_user=new ViewPager_User(channelId,name);
            te.addTo2(viewPager_user);
            te.remover1(name);
            list_lv.add(viewPager_user);
            ara.getList().remove(position);//删除
            ara.setList(arrayList);

            Timer timer=new Timer();
            TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };
            timer.schedule(task,200);
        }
    };

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    ara.notifyDataSetChanged();
                    aad.notifyDataSetChanged();
                    break;
            }
        }
    };
    void inClick(){
        toolbar= (Toolbar) findViewById(R.id.two_toolbar);
        recyclerView= (RecyclerView) findViewById(R.id.addtitle_recycler);
        lv= (ListView) findViewById(R.id.addtitle_listview);
        list_lv=new ArrayList<>();
        aad=new Add_Adapter(this);
        te=new TitleExpress(this);
        sm=new Shade_Main();
    }

    void inData(){
        toolbar.setTitle("泊哥新闻");
        toolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

            list_lv=te.check2();

        aad.setList(list_lv);
        lv.setAdapter(aad);
        setview();
    }

    ArrayList<ViewPager_User> list1;

    void setview(){
        //下方listview的点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=aad.getList().get(position).getName();
                list1=new ArrayList<>();
                list1=te.checkOne1(name);
                 ArrayList<ViewPager_User> list=te.checkOne2(name);
                String channelId=list.get(0).getChannelId();
                if(list1.size()==0){
                    te.addTo1(new ViewPager_User(channelId,name));
                }
                te.remover2(name);
                aad.getList().remove(position);
                aad.notifyDataSetChanged();
                setRecyclerView();
            }
        });
    }



}
