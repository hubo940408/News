package personal.edu.news.activity;

import android.app.Application;

import com.zhy.changeskin.SkinManager;

/**
 * Created by Administrator on 2017/2/4 0004.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
