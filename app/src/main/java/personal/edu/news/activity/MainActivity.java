package personal.edu.news.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhy.changeskin.SkinManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.entity.ViewPager_User;
import personal.edu.news.fragment.Main_Message;
import personal.edu.news.fragment.Main_Redian;
import personal.edu.news.fragment.Main_Seek;
import personal.edu.news.sjk.TitleExpress;

public class MainActivity extends AppCompatActivity {
    private class LogInListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Toast.makeText(MainActivity.this, "授权成功！", Toast.LENGTH_LONG).show();
            System.out.println("o.toString() ------------------------->        " + o.toString());
            JSONObject jsonObject = (JSONObject) o;

            //设置openid和token，否则获取不到下面的信息
            initOpenidAndToken(jsonObject);
            //获取QQ用户的各信息
            getUserInfo();
        }

        @Override
        public void onError(UiError uiError) {

            Toast.makeText(MainActivity.this, "授权出错！", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "授权取消！", Toast.LENGTH_LONG).show();
        }
    }

    //需要腾讯提供的一个Tencent类
    private Tencent mTencent;
    //还需要一个IUiListener 的实现类（LogInListener implements IUiListener）
    private LogInListener mListener;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle abt;

    TextView left_login;
    RelativeLayout fragment_rl,left_url,set_rl;

    CircleImageView left_im;
    FragmentManager fm;
    FragmentTransaction ft;
    RelativeLayout left_aboutus;
    RelativeLayout check_one,check_two,check_three;
    ImageView zixun,redian,sousuo;
    TextView zixun_tv,redian_tv,sousuo_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SkinManager.getInstance().register(this);
        ShareSDK.initSDK(this);
        //首先需要用APP ID 获取到一个Tencent实例
        mTencent = Tencent.createInstance("100371282", this.getApplicationContext());
        //初始化一个IUiListener对象，在IUiListener接口的回调方法中获取到有关授权的某些信息
        // （千万别忘了覆写onActivityResult方法，否则接收不到回调）
        mListener = new LogInListener();
        inClick();
        setToolbar();
        setImager();
    }

    void inClick(){
        left_url= (RelativeLayout) findViewById(R.id.left_url_rl);
        left_im= (CircleImageView) findViewById(R.id.main_left_photo);
        set_rl= (RelativeLayout) findViewById(R.id.left_six_rl);
        fragment_rl= (RelativeLayout) findViewById(R.id.main_fragment);//碎片
        left_aboutus= (RelativeLayout) findViewById(R.id.left_aboutus_rl);
        left_login= (TextView) findViewById(R.id.left_login_tv);//点击登录按钮
        fm=getSupportFragmentManager();
        zixun_tv= (TextView) findViewById(R.id.main_tv_zixun);
        redian_tv= (TextView) findViewById(R.id.main_tv_redian);
        sousuo_tv= (TextView) findViewById(R.id.main_tv_sousuo);
        check_one= (RelativeLayout) findViewById(R.id.main_check_one);
        check_two= (RelativeLayout) findViewById(R.id.main_check_two);
        check_three= (RelativeLayout) findViewById(R.id.main_check_three);
        zixun= (ImageView) findViewById(R.id.main_check_zixun);
        redian= (ImageView) findViewById(R.id.main_check_redian);
        sousuo= (ImageView) findViewById(R.id.main_check_sousuo);
    }

    /**
     * 装置右侧菜单栏
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    /**
     * Toolbar数据
     */
    void setToolbar(){
        toolbar= (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.main_drawerlayout);
        toolbar.setTitle("NewsDay");
        setSupportActionBar(toolbar);
        abt=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.dra_open,R.string.dra_close);
        //初始化状态
        abt.syncState();
        //加载给抽屉
        drawerLayout.addDrawerListener(abt);

        int width =getWindowManager().getDefaultDisplay().getWidth();
        Shade_Main.setwidth(this,width);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_toolbar_menu:
                        showShare();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    Main_Message main_message;
    Main_Redian main_redian;
    Main_Seek main_seek;

    TitleExpress te;
    void setImager(){
        //收藏网页跳转
        left_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Activity_Url.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });
        ft=fm.beginTransaction();
        main_redian=new Main_Redian();
        main_message=new Main_Message();
        main_seek=new Main_Seek();
        ft.add(R.id.main_fragment,main_message);
        ft.add(R.id.main_fragment,main_redian);
        ft.add(R.id.main_fragment,main_seek);
        ft.hide(main_redian).hide(main_message).hide(main_seek);
        ft.show(main_message);
        ft.commit();
        check_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zixun_tv.setTextColor(android.graphics.Color.parseColor("#374ce4"));//蓝色
                redian_tv.setTextColor(android.graphics.Color.parseColor("#7b7979"));//灰色
                sousuo_tv.setTextColor(android.graphics.Color.parseColor("#7b7979"));
                zixun.setBackgroundResource(R.mipmap.new_selected);
                redian.setBackgroundResource(R.mipmap.collect_unselected);
                sousuo.setBackgroundResource(R.mipmap.find_defult);
                ft=fm.beginTransaction();
                ft.hide(main_message).hide(main_redian).hide(main_seek);
                ft.show(main_message);
                ft.commit();
            }
        });
        check_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zixun_tv.setTextColor(android.graphics.Color.parseColor("#7b7979"));
                redian_tv.setTextColor(android.graphics.Color.parseColor("#374ce4"));//蓝色
                sousuo_tv.setTextColor(android.graphics.Color.parseColor("#7b7979"));//灰色
                zixun.setBackgroundResource(R.mipmap.new_unselected);
                redian.setBackgroundResource(R.mipmap.collect_selected);
                sousuo.setBackgroundResource(R.mipmap.find_defult);
                ft=fm.beginTransaction();
                ft.hide(main_message).hide(main_redian).hide(main_seek);
                ft.show(main_redian);
                ft.commit();
            }
        });
        check_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zixun_tv.setTextColor(android.graphics.Color.parseColor("#7b7979"));
                redian_tv.setTextColor(android.graphics.Color.parseColor("#7b7979"));//灰色
                sousuo_tv.setTextColor(android.graphics.Color.parseColor("#374ce4"));//蓝色
                zixun.setBackgroundResource(R.mipmap.new_unselected);
                redian.setBackgroundResource(R.mipmap.collect_unselected);
                sousuo.setBackgroundResource(R.mipmap.find_selected);
                ft=fm.beginTransaction();
                ft.hide(main_message).hide(main_redian).hide(main_seek);
                ft.show(main_seek);
                ft.commit();
            }
        });

        set_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Activity_Set.class);
                startActivity(intent);
            }
        });

        left_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Activity_AboutUs.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });
        te=new TitleExpress(this);
        boolean title_main= Shade_Main.getadd(this);
        if(!title_main){
            te.addTo1(new ViewPager_User("shehui","社会"));
            te.addTo1(new ViewPager_User("guonei","国内"));
            te.addTo1(new ViewPager_User("guoji","国际"));
            te.addTo1(new ViewPager_User("yule","娱乐"));
            te.addTo1(new ViewPager_User("tiyu","体育"));
            te.addTo1(new ViewPager_User("junshi","军事"));
            te.addTo1(new ViewPager_User("keji","科技"));
            te.addTo1(new ViewPager_User("caijing","财经"));
            te.addTo1(new ViewPager_User("shishang","时尚"));
            Shade_Main.setadd(this,true);
        }

        left_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTencent.isSessionValid()) {
                    mTencent.login(MainActivity.this, "all", mListener);
                }
                drawerLayout.closeDrawers();
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent=new Intent();
        intent.setAction("更新");
        sendBroadcast(intent);
    }






    //确保能接收到回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String openid = jsonObject.getString("openid");
            String token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");

            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {

        //sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
        QQToken mQQToken = mTencent.getQQToken();
        UserInfo userInfo = new UserInfo(MainActivity.this, mQQToken);
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(final Object o) {
                JSONObject userInfoJson = (JSONObject) o;
                Message msgNick = new Message();
                msgNick.what = 0;//昵称
                try {
                    msgNick.obj = userInfoJson.getString("nickname");//直接传递一个昵称的内容过去
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(msgNick);
                //子线程 获取并传递头像图片，由Handler更新
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmapHead = null;if (((JSONObject) o).has("figureurl")) {
                            try {
                                String headUrl = ((JSONObject) o).getString("figureurl_qq_2");
                                bitmapHead = Util.getbitmap(headUrl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Message msgHead = new Message();
                            msgHead.what = 1;
                            msgHead.obj = bitmapHead;
                            mHandler.sendMessage(msgHead);
                        }
                    }
                }).start();
            }
            @Override
            public void onError(UiError uiError) {
                Log.e("GET_QQ_INFO_ERROR", "获取qq用户信息错误");
                Toast.makeText(MainActivity.this, "获取qq用户信息错误", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancel() {
                Log.e("GET_QQ_INFO_CANCEL", "获取qq用户信息取消");
                Toast.makeText(MainActivity.this, "获取qq用户信息取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class Util {
        public static String TAG="UTIL";
        public static Bitmap getbitmap(String imageUri) {
            Log.v(TAG, "getbitmap:" + imageUri);
            // 显示网络上的图片
            Bitmap bitmap = null;
            try {
                URL myFileUrl = new URL(imageUri);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();

                Log.v(TAG, "image download finished." + imageUri);
            } catch (IOException e) {
                e.printStackTrace();
                Log.v(TAG, "getbitmap bmp fail---");
                return null;
            }
            return bitmap;
        }
    }
    //显示获取到的头像和昵称
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {//获取昵称
                left_login.setText((CharSequence) msg.obj);
            } else if (msg.what == 1) {//获取头像
                left_im.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            dialog();

        }
        return false;
//        return super.onKeyDown(keyCode, event);
    }

    void dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确定退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
