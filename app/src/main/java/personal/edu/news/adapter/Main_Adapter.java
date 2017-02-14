package personal.edu.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import personal.edu.news.entity.ViewPager_User;


/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class Main_Adapter extends FragmentStatePagerAdapter {
    List<Fragment> list;
    List<ViewPager_User> title;

    public Main_Adapter(FragmentManager fm) {
        super(fm);
        list=new ArrayList<>();
        title=new ArrayList<>();
    }

    public List<Fragment> getList() {
        return list;
    }

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public List<ViewPager_User> getTitle() {
        return title;
    }

    public void setTitle(List<ViewPager_User> title) {
        this.title = title;
    }

    //视图创建
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    //长度
    @Override
    public int getCount() {
        return list.size();
    }

    //设置标题
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position).getName();
    }
}
