package personal.edu.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.news.R;

/**
 * Created by Administrator on 2017/1/5 0005.
 */
public class Seek_Score extends BaseAdapter {
    Context context;
    ArrayList<String>list;

    public Seek_Score(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
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
        ViewH vh;
        if(convertView==null){
            vh=new ViewH();
            convertView= LayoutInflater.from(context).inflate(R.layout.addtitle_listview_item,null);
            vh.tv= (TextView) convertView.findViewById(R.id.add_item_tv);
            convertView.setTag(vh);
        }else{
            vh= (ViewH) convertView.getTag();
        }
        vh.tv.setText(list.get(position));

        return convertView;
    }

    class ViewH{
        TextView tv;
    }

}
