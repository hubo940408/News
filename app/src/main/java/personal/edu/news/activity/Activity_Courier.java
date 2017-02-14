package personal.edu.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.changeskin.SkinManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.adapter.Courier_Item_Adapter;
import personal.edu.news.entity.Courier_User;
import personal.edu.news.quicksidebardemo.MainActivity_main;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class Activity_Courier extends AppCompatActivity {
    Button company_but,but;
    TextView courier;
    EditText et;
    ListView lv;
    Courier_Item_Adapter cia;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        SkinManager.getInstance().register(this);
        company_but= (Button) findViewById(R.id.courier_company_but);
        but= (Button) findViewById(R.id.courier_but);
        cia=new Courier_Item_Adapter(this);
        lv= (ListView) findViewById(R.id.courier_lv);
        courier= (TextView) findViewById(R.id.courier_company);
        et= (EditText) findViewById(R.id.courier_number);
        inClick();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
    Intent intent;
    void inClick(){
        company_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("msg","跳转页面");
                intent=new Intent(Activity_Courier.this, MainActivity_main.class);
                startActivity(intent);
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num=et.getText().toString();
                String gonsi=courier.toString();
                if(num.length()!=0&&gonsi.length()!=0){
                    new Thread(new MyRu()).start();
                }else{
                    Toast.makeText(Activity_Courier.this,"请输入上面的信息",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    class MyRu implements Runnable{

        @Override
        public void run() {
            Message ms=new Message();
            ms.what=0;
            String num=et.getText().toString();
            if(num.length()!=0){
                String nam=setCourier(num);
                ms.obj=nam;
                Log.i("msg","打印nam："+nam);
            }
            handler.sendMessage(ms);
        }
    }

    ArrayList<Courier_User.ShowapiResBodyBean.DataBean> list;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    list=new ArrayList<>();
                    String sss;
                    sss= (String) msg.obj;
                    Log.i("msg","打印ss："+sss);
                    Courier_User courier_user=new Gson().fromJson(sss,Courier_User.class);
                    list= (ArrayList<Courier_User.ShowapiResBodyBean.DataBean>) courier_user.getShowapi_res_body().getData();
                    cia.setList(list);
                    lv.setAdapter(cia);
                    cia.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected void onRestart() {
        super.onRestart();
        courier.setText(Shade_Main.getcourier(this));
    }

    String setCourier(String number_courier){
        String ser = null;
        String path="http://route.showapi.com/64-19";
        StringBuffer sbb=new StringBuffer(path);
        sbb.append("?showapi_appid=");
        sbb.append("29809");
        sbb.append("&showapi_sign=a6d7f5b9f4cd44f780e25479a66abf83");
        sbb.append("&com=");
        sbb.append("auto");
        sbb.append("&nu=");
        sbb.append(number_courier);

        try {
            URL url=new URL(sbb.toString());
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
                ser=sb.toString();
            }else {
                Toast.makeText(Activity_Courier.this,"没查询到物流信息",Toast.LENGTH_SHORT).show();
                ser="取值失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("msg","res:"+ser);
        return ser;
    }
}
