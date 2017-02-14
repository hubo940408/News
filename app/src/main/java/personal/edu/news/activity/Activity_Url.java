package personal.edu.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.adapter.Url_Adapter;
import personal.edu.news.entity.WebView_User;
import personal.edu.news.sjk.TitleExpress;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class Activity_Url extends AppCompatActivity {
    ListView listView;
    Url_Adapter ua;
    Toolbar toolbar;
    TitleExpress te;
    ArrayList<WebView_User>list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        SkinManager.getInstance().register(this);
        ua=new Url_Adapter(this);
        te=new TitleExpress(this);
        toolbar= (Toolbar) findViewById(R.id.url_toolbar);
        listView= (ListView) findViewById(R.id.url_lv);
        inClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    void inClick(){
        toolbar.setTitle("我的收藏");
        toolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list=new ArrayList<>();
        list=te.checkwebview();
        ua.setList(list);
        listView.setAdapter(ua);
        ua.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Activity_Url.this,Activity_WebView.class);
                intent.putExtra("url",list.get(position).getUrl());
                Shade_Main.setnews(Activity_Url.this,2);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                te.removerwebview(list.get(position).getUrl());
                ArrayList<WebView_User>list1=ua.getList();
                list1.remove(position);
                ua.setList(list1);
                ua.notifyDataSetChanged();
                return true;
            }
        });
    }
}
