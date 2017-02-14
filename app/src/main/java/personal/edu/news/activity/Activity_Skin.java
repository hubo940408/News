package personal.edu.news.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zhy.changeskin.SkinManager;

import personal.edu.news.R;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class Activity_Skin extends AppCompatActivity {
    ImageView green,navy,woodgrain;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        green= (ImageView) findViewById(R.id.skin_green);
        navy= (ImageView) findViewById(R.id.skin_navy);
        woodgrain= (ImageView) findViewById(R.id.skin_woodgrain);
        inClick();
    }


    void inClick(){
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().changeSkin("green");
                finish();
            }
        });
        navy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().changeSkin("navy");
                finish();
            }
        });
        woodgrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().changeSkin("woodgrain");
                finish();
            }
        });
    }
}
