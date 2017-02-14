package personal.edu.news.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.zhy.changeskin.SkinManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.adapter.Weather_Adapter;
import personal.edu.news.entity.Weather_User;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public class Activity_Weather extends AppCompatActivity{
    TextView city_tv;
    ProgressBar city_pb;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        SkinManager.getInstance().register(this);
        city_tv= (TextView) findViewById(R.id.weather_city);
        city_pb= (ProgressBar) findViewById(R.id.weather_city_pb);
        listView= (ListView) findViewById(R.id.weather_listview);
        wa=new Weather_Adapter(this);
        inClick();
    }


    //声明AMapLocationClientOption对象
     public AMapLocationClientOption mLocationOption = null;

    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    void inClick(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());

        mLocationOption=new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(8000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        Log.i("msg","启动定位");
        //可以通过类implement方式实现AMapLocationListener接口，也可以通过创造接口类对象的方法实现

        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        city_tv.setText(amapLocation.getCity());
                        city_pb.setVisibility(View.GONE);
                        handler.sendEmptyMessage(0);
                    }else {
                        city_tv.setText("定位失败");
                        city_pb.setVisibility(View.GONE);
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.i("msg","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());

                    }
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    //提取天气信息的广播！
    class MyWeather implements Runnable{

        @Override
        public void run() {
            Message ms=new Message();
            ms.what=1;
            ms.obj=weatherCity(city_tv.getText().toString());
            handler.sendMessage(ms);
        }
    }

    ArrayList<Weather_User.ResultBean.FutureBean> lists;
    Weather_Adapter wa;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    new Thread(new MyWeather()).start();
                    break;
                case 1:
                    lists=new ArrayList<>();
                    String cityweather= (String) msg.obj;
                    Weather_User weather_user= new Gson().fromJson(cityweather,Weather_User.class);
                    Log.i("msg",weather_user.getResult().getFuture().toString());
                    lists= (ArrayList<Weather_User.ResultBean.FutureBean>)
                            weather_user.getResult().getFuture();

                    wa.setList(lists);
                    listView.setAdapter(wa);
                    wa.notifyDataSetChanged();
                    break;
            }
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }


    String weatherCity(String cityname){
        String res=null;
        try {
            String path="http://v.juhe.cn/weather/index";
            StringBuffer sbb=new StringBuffer(path);
            sbb.append("?format=2&cityname=");
            sbb.append(URLEncoder.encode(cityname, "utf-8"));
            sbb.append("&key=");
            sbb.append("ad0ab138eb23851b05d80959a7009947");

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


}
