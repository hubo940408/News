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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.activity.Activity_WebView;
import personal.edu.news.adapter.Seek_News_Adapter;
import personal.edu.news.adapter.Seek_Score;
import personal.edu.news.entity.Seek_News;
import personal.edu.news.sjk.TitleExpress;

/**
 * 搜索碎片
 * Created by Administrator on 2017/1/4 0004.
 */
public class Main_Seek extends Fragment{
    EditText et;
    ListView lv;

    ArrayList<String> strings;
    Seek_Score aa;
    LinearLayout gone;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_seek,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et= (EditText) view.findViewById(R.id.seek_edittext);
        lv= (ListView) view.findViewById(R.id.seek_listview_lv);
        sna=new Seek_News_Adapter(getActivity());
        recyclerView= (RecyclerView) view.findViewById(R.id.seek_recyclerview);
        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        gone= (LinearLayout) view.findViewById(R.id.seek_gong);
        te=new TitleExpress(getActivity());
        aa=new Seek_Score(getActivity());
        gone.setVisibility(View.GONE);
        inClick();
        setView();
    }
    void inClick(){
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        strings=new ArrayList<>();
        strings=te.checkScore();
        aa.setList(strings);
        lv.setAdapter(aa);
        aa.notifyDataSetChanged();
        sna.setOnNews(new Seek_News_Adapter.OnNews() {
            @Override
            public void setview(int position) {
                ArrayList<Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean>listnews;
                listnews=sna.getList();
                Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean seek_news=listnews.get(position);
                Intent intent=new Intent(getActivity(), Activity_WebView.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("hb_news",seek_news);
                Shade_Main.setnews(getActivity(),1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("msg","历史记录点击监听");
                String namenews=aa.getList().get(position);
                et.setText(namenews);
            }
        });
    }


    String name;
    void setView(){
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gone.setVisibility(View.VISIBLE);
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name=s.toString();
                Log.i("msg","输入字符串长度"+name.length());
                new Thread(new Myclass()).start();
                gone.setVisibility(View.GONE);
            }
        });
    }


    TitleExpress te;
    public String getUrl(){
        String res = null;

        String path="http://route.showapi.com/109-35";
        StringBuffer stringBuffer=new StringBuffer(path);
        stringBuffer.append("?showapi_appid=");
        stringBuffer.append("29809");
        stringBuffer.append("&title=");
        stringBuffer.append(name);
        Log.i("msg","name="+name);
        stringBuffer.append("&showapi_sign=a6d7f5b9f4cd44f780e25479a66abf83");
        try {
            URL url=new URL(stringBuffer.toString());
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();//通过通道打开链接
            //设置请求方式 默认GET
            connection.setRequestMethod("GET");//必须大写
            //链接时间
            connection.setConnectTimeout(5000);//链接最大时间
            connection.setReadTimeout(5000);//读取最大时间
            if (connection.getResponseCode()==200){
                //链接成功之后拿到返回的数据
                InputStream inp= connection.getInputStream();//拿到返回的数据
                byte[]b=new byte[1024];
                int len=0;
                StringBuffer sb=new StringBuffer();
                while ((len=inp.read(b))!=-1){
                    sb.append(new String(b,0,len));
                }
                res=sb.toString();
                Log.i("msg","res:"+res);
            }else {
                res="连接失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    class Myclass implements Runnable{

        @Override
        public void run() {
            String ss=getUrl();
            Log.i("msg","搜索新闻结束："+ss);
            Message ms=new Message();
            ms.obj=ss;
            ms.what=0;
            handler.sendMessage(ms);
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.i("msg","开始解析");
                    if(te.checkOneScore(name).size()==0&&name.length()!=0){
                        te.addScore(name);
                    }
                    String mss= (String) msg.obj;
                    Log.i("msg","handler接受长度："+mss.length());
                    Seek_News seek_news=new Gson().fromJson(mss,Seek_News.class);
                    list= (ArrayList<Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean>)
                            seek_news.getShowapi_res_body().getPagebean().getContentlist();
                    sna.setList(list);
                    Log.i("msg","解析结束：list长度："+list.size());
                    recyclerView.setAdapter(sna);
                    sna.notifyDataSetChanged();
                    break;
            }
        }
    };



    ArrayList<Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list;
    Seek_News_Adapter sna;



}
