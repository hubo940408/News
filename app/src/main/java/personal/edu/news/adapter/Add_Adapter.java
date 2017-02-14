package personal.edu.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.entity.ViewPager_User;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class Add_Adapter extends BaseAdapter {
    Context context;
    ArrayList<ViewPager_User> list;

    public Add_Adapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<ViewPager_User> getList() {
        return list;
    }

    public void setList(ArrayList<ViewPager_User> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolader vh;
        if(convertView==null){
            vh=new ViewHolader();
            convertView= LayoutInflater.from(context).inflate(R.layout.addtitle_listview_item,null);
            vh.tv= (TextView) convertView.findViewById(R.id.add_item_tv);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolader) convertView.getTag();
        }
            vh.tv.setText(list.get(position).getName());

        return convertView;
    }
    class ViewHolader{
        TextView tv;
    }
}
