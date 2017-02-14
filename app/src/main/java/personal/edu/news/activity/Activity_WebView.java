package personal.edu.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.entity.Entity;
import personal.edu.news.entity.Seek_News;
import personal.edu.news.entity.WebView_User;
import personal.edu.news.sjk.TitleExpress;

/**
 * 具体网页界面
 * Created by Administrator on 2017/1/5 0005.
 */
public class Activity_WebView extends AppCompatActivity {
    WebView webView;
    ImageView imageView;
    ProgressBar progressBar;
    TitleExpress te;
    String url;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        te=new TitleExpress(this);
        imageView= (ImageView) findViewById(R.id.webview_im);
        progressBar= (ProgressBar) findViewById(R.id.webview_pb);
        inClick();
        inData();
    }

    void inClick(){
        webView= (WebView) findViewById(R.id.webview_vb);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int news= Shade_Main.getnews(this);
        switch (news){
            case 0:
                Entity.ResultBean.DataBean dataBean= (Entity.ResultBean.DataBean) bundle.getSerializable("hb");
                name=dataBean.getTitle();
                url=dataBean.getUrl();
                break;
            case 1:
                Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean
                        = (Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean) bundle.getSerializable("hb_news");
                name=contentlistBean.getTitle();
                url=contentlistBean.getLink();
                break;
            case 2:
                Intent intent1=getIntent();
                url=intent1.getStringExtra("url");
                break;
        }
        webView.loadUrl(url);
        list=new ArrayList<>();
        list.clear();

        list=te.check_webView_users(url);
        if(list.size()==0){
            imageView.setImageResource(R.mipmap.star_defult);
        }else{
            imageView.setImageResource(R.mipmap.star_selected);
        }

    }

    void inData(){
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);

                }else {
                    progressBar.setProgress(newProgress);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setView()){
                    imageView.setImageResource(R.mipmap.star_selected);
                    Toast.makeText(Activity_WebView.this,"收藏成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    ArrayList<WebView_User>list;
    boolean setView(){
        list=new ArrayList<>();
        list.clear();
        list=te.check_webView_users(url);
        if(list.size()!=0){
            Toast.makeText(this,"该网页已收藏",Toast.LENGTH_SHORT).show();
            return false;
        }
        WebView_User webView_user=new WebView_User(name,url);
        te.addwebview(webView_user);
        return true;
    }
}
