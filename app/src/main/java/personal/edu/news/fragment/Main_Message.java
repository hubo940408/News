package personal.edu.news.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import personal.edu.news.R;
import personal.edu.news.activity.Activity_Title;
import personal.edu.news.adapter.Main_Adapter;
import personal.edu.news.entity.CubeTransformer;
import personal.edu.news.entity.ViewPager_User;
import personal.edu.news.sjk.TitleExpress;

/**
 * 资讯碎片
 * Created by Administrator on 2017/1/3 0003.
 */
public class Main_Message extends Fragment {
    TabLayout tabLayout;
    ImageView im;
    Main_Adapter ma;
    TitleExpress te;
    List<Fragment> list;
    List<ViewPager_User>title_list;
    ViewPager viewPager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout= (TabLayout) view.findViewById(R.id.main_message_tablayout);
        im= (ImageView) view.findViewById(R.id.main_message_add_im);
        viewPager= (ViewPager) view.findViewById(R.id.main_message_viewpager);
        ma=new Main_Adapter(getActivity().getSupportFragmentManager());
        list=new ArrayList<>();
        title_list=new ArrayList<>();
        inClick();
        getintent();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_message_fragment,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Activity_Title.class);
                startActivity(intent);
            }
        });
    }
    Message_Small_Fragment message_small_fragment;

    void inClick(){

        te=new TitleExpress(getContext());
        list.clear();
        title_list.clear();
        title_list=te.check1();
        for(ViewPager_User vpu:title_list){
            message_small_fragment=new Message_Small_Fragment();
            Bundle bundle=new Bundle();
            bundle.putString("title",vpu.getChannelId());
            message_small_fragment.setArguments(bundle);
            list.add(message_small_fragment);
        }
        ma.setList(list);
        ma.setTitle(title_list);
        ma.notifyDataSetChanged();
        setViewPager();
    }
    void setViewPager(){
        tabLayout.setTabTextColors(Color.BLUE,android.graphics.Color.parseColor("#e45913"));//前一个默认，后一个选中
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);//下划线颜色
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//Fix代表固定，Scrollable代表可滑动
        viewPager.setAdapter(ma);
        //将TabLayout与ViewPager绑定在一起
        tabLayout.setupWithViewPager(viewPager);

        //ViewPager 跳转动画(网上查到的方法)
//        viewPager.setPageTransformer(true, new CubeTransformer());
    }



    class MyRe extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String Action=intent.getAction();
            switch (Action){
                case "更新":
                    inClick();
                    break;
            }
        }
    }
    MyRe mr;
    void getintent(){
        mr=new MyRe();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("更新");
        getContext().registerReceiver(mr,intentFilter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mr);
    }
}
