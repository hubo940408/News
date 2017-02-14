package personal.edu.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.entity.Courier_User;

/**
 * Created by Administrator on 2017/2/4 0004.
 */
public class Courier_Item_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Courier_User.ShowapiResBodyBean.DataBean>list;

    public Courier_Item_Adapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<Courier_User.ShowapiResBodyBean.DataBean> getList() {
        return list;
    }

    public void setList(ArrayList<Courier_User.ShowapiResBodyBean.DataBean> list) {
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
        ViewHol vh;
        if(convertView==null){
            vh=new ViewHol();
            convertView= LayoutInflater.from(context).inflate(R.layout.courier_item,null);
            vh.time= (TextView) convertView.findViewById(R.id.courier_time_tv);
            vh.xinxi= (TextView) convertView.findViewById(R.id.courier_xinxi_tv);
            convertView.setTag(vh);
        }else {
            vh= (ViewHol) convertView.getTag();
        }
        Courier_User.ShowapiResBodyBean.DataBean listBean=list.get(position);
        vh.time.setText(listBean.getTime());
        vh.xinxi.setText(listBean.getContext());
        return convertView;
    }

    class ViewHol{
        TextView time,xinxi;

    }


}
