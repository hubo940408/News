package personal.edu.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.entity.WebView_User;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class Url_Adapter extends BaseAdapter {
    Context context;
    ArrayList<WebView_User>list;

    public ArrayList<WebView_User> getList() {
        return list;
    }

    public void setList(ArrayList<WebView_User> list) {
        this.list = list;
    }

    public Url_Adapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
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
        WebViewHolder wh;
        if(convertView==null){
            wh=new WebViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.url_item,null);
            wh.top= (TextView) convertView.findViewById(R.id.url_item_toptv);
            wh.bot= (TextView) convertView.findViewById(R.id.url_item_bottv);
            convertView.setTag(wh);
        }else{
            wh= (WebViewHolder) convertView.getTag();
        }
        wh.top.setText(list.get(position).getName());
        wh.bot.setText(list.get(position).getUrl());
        return convertView;
    }

    class WebViewHolder{
        TextView top,bot;
    }
}
