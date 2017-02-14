package personal.edu.news.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zhy.changeskin.SkinManager;

import personal.edu.news.R;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
public class Activity_AboutUs extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        inClick();
        inData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }

    void inClick(){
        toolbar= (Toolbar) findViewById(R.id.aboutus_toolbar);
        tv= (TextView) findViewById(R.id.aboutus_tv);
    }
    void inData(){
        toolbar.setNavigationIcon(R.mipmap.btn_return);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        ApplicationInfo applicationInfo = null;
        try {
            manager = getApplicationContext().getPackageManager();
            applicationInfo = manager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =(String) manager.getApplicationLabel(applicationInfo);
        tv.setText("版本:"+version+"\n作者:"+applicationName);

    }
}
