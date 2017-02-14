package personal.edu.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import personal.edu.news.adapter.Small_MessagerApadter;
import personal.edu.news.entity.Entity;
import personal.edu.news.entity.ViewPager_User;

/**
 * Created by Administrator on 2017/1/4 0004.
 */
public class Message_Small_Fragment extends Fragment {
    ArrayList<Entity.ResultBean.DataBean> list;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_small_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title=getArguments().getString("title");
        sa=new Small_MessagerApadter(getContext());
        list=new ArrayList<>(); alllist=new ArrayList<>();
        recyclerView= (RecyclerView) view.findViewById(R.id.main_fragment_recyclerview);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.main_fragment_swipe);
        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);

        inData(title);
        setRecyclerView();
    }

    public void inData(final String name){
        Log.i("msg","刷新数据");
        StringRequest stringRequest =new StringRequest(Request.Method.POST,"http://v.juhe.cn/toutiao/index",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        //网络访问成功的返回数据

                        Entity netWark=new Gson().fromJson(s, Entity.class);

                        Message ms=new Message();
                        ms.obj=netWark;
                        ms.what=0;
                        handler.sendMessage(ms);
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<String, String>();
                map.put("type",name);
                map.put("key","237658ec4e5e6854e4ef226aff7c7e85");
                return map;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    boolean isloadd;
    void setRecyclerView(){
        sa.setOnItem(new Small_MessagerApadter.OnItem() {
            @Override
            public void send(int position) {
                Entity.ResultBean.DataBean viewPager_user=list.get(position);
                Intent intent=new Intent(getActivity(), Activity_WebView.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("hb",viewPager_user);
                intent.putExtras(bundle);
                Shade_Main.setnews(getActivity(),0);
                startActivity(intent);
            }
        });
        //下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inData(title);
            }
        });

        //上拉加载更新
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(linearLayoutManager.findLastVisibleItemPosition()==sa.getList().size()){
                    switch (newState){
                        case 0:
                        if(isloadd){
                            loadMo();
                            sa.notifyDataSetChanged();
                        }
                            break;
                        case 1:
                            isloadd=false;
                            break;
                        case 2:
                            isloadd=true;
                            break;
                    }
                }
            }
        });
    }


    boolean load;
    void loadMo(){
        if(load){
            return;
        }
        sa.setState(Small_MessagerApadter.DATA_LOADING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                load=true;
                Log.i("msg","进入Runnable");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Log.i("msg","handler发送");
                    handler.sendEmptyMessage(1);

                }
            }
        }).start();
    }




    int number;
    boolean NoMore;
    Small_MessagerApadter sa;
    ArrayList<Entity.ResultBean.DataBean>alllist;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    number=10;
                    list.clear();
                    alllist.clear();
                    Entity entity= (Entity) msg.obj;
                    alllist= (ArrayList<Entity.ResultBean.DataBean>) entity.getResult().getData();
                    for(int i=0;i<number;i++){
                        list.add(alllist.get(i));//前10条数据给了数据源
                    }
                    sa.setList(list);
                    recyclerView.setAdapter(sa);
                    sa.notifyDataSetChanged();
                    number+=10;
                    break;
                case 1:

                    load=false;
                    if(number>=alllist.size()){
                        NoMore=true;
                        number=alllist.size();
                    }
                    //加载更多数据
                    list=sa.getList();
                    for (int i=list.size();i<number;i++){
                        list.add(alllist.get(i));
                    }
                    sa.setList(list);
                    if(NoMore){
                        sa.setState(Small_MessagerApadter.DATA_NOMORE);
                    }else {
                        sa.setState(Small_MessagerApadter.DATA_FINISH);
                        number+=10;
                    }
                    sa.notifyDataSetChanged();
                    break;
            }
        }
    };
}
