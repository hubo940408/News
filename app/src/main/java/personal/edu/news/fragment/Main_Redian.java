package personal.edu.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.activity.Activity_WebView;
import personal.edu.news.adapter.DemoAdapter;
import personal.edu.news.entity.Entity;
import personal.edu.news.entity.Seek_News;
import personal.edu.news.view.ViewLayout;

/**
 * Created by Administrator on 2017/1/4 0004.
 */
public class Main_Redian extends Fragment {
    RecyclerView recyclerView;
    DemoAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Entity.ResultBean.DataBean>list;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new DemoAdapter(getActivity());
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.redian_swipe);
        recyclerView= (RecyclerView) view.findViewById(R.id.redian_recyclerView);
        list=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        inClick();
        inData();
    }

    void inData(){
        adapter.setOnData(new DemoAdapter.OnData() {
            @Override
            public void ondata(int position) {
                Entity.ResultBean.DataBean viewPager_user=list.get(position);
                Intent intent=new Intent(getActivity(), Activity_WebView.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("hb",viewPager_user);
                intent.putExtras(bundle);
                Shade_Main.setnews(getActivity(),0);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inClick();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_redian,container,false);
    }

    void inClick(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://v.juhe.cn/toutiao/index"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Entity netWark=new Gson().fromJson(s, Entity.class);
                Message ms=new Message();
                ms.obj=netWark;
                ms.what=0;
                handler.sendMessage(ms);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"数据读取失败",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<String, String>();
                map.put("type","toutiao");
                map.put("key","237658ec4e5e6854e4ef226aff7c7e85");
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.start();
    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        list.clear();
                        Entity entity= (Entity) msg.obj;
                        list= (ArrayList<Entity.ResultBean.DataBean>) entity.getResult().getData();
                        adapter.setList(list);

                        adapter.notifyDataSetChanged();
                        break;
                }
        }
    };
}
