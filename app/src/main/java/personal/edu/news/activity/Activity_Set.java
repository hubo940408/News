package personal.edu.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhy.changeskin.SkinManager;

import personal.edu.news.R;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public class Activity_Set extends AppCompatActivity {
    RelativeLayout tianqi,kuaidi,pifu,ziti;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        SkinManager.getInstance().register(this);
        inClick();
        setView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    void inClick(){
        tianqi= (RelativeLayout) findViewById(R.id.set_weather);
        kuaidi= (RelativeLayout) findViewById(R.id.set_express);
        pifu= (RelativeLayout) findViewById(R.id.set_skin);
        ziti= (RelativeLayout) findViewById(R.id.set_typeface);
    }
    Intent intent;
    void setView(){
        tianqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Activity_Set.this,Activity_Weather.class);
                startActivity(intent);
            }
        });
        kuaidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Activity_Set.this,Activity_Courier.class);
                startActivity(intent);
            }
        });
        pifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Activity_Set.this,Activity_Skin.class);
                startActivity(intent);
            }
        });
        ziti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
