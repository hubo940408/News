package personal.edu.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.entity.Weather_User;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class Weather_Adapter extends BaseAdapter {
    ArrayList<Weather_User.ResultBean.FutureBean>list;
    Context context;

    public ArrayList<Weather_User.ResultBean.FutureBean> getList() {
        return list;
    }

    public void setList(ArrayList<Weather_User.ResultBean.FutureBean> list) {
        this.list = list;
    }

    public Weather_Adapter(Context context) {
        this.context = context;
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
        ViewWeather vh;
        if(convertView==null){
            vh=new ViewWeather();
            convertView= LayoutInflater.from(context).inflate(R.layout.weather_item,null);
            vh.im= (ImageView) convertView.findViewById(R.id.weather_im);
            vh.temperature_tv= (TextView) convertView.findViewById(R.id.weather_item_temperature);
            vh.weather_tv= (TextView) convertView.findViewById(R.id.weather_item_weather);
            vh.week_tv= (TextView) convertView.findViewById(R.id.weather_item_week);
            vh.date= (TextView) convertView.findViewById(R.id.weather_item_date);
            convertView.setTag(vh);
        }else {
            vh= (ViewWeather) convertView.getTag();
        }
        Weather_User.ResultBean.FutureBean object=list.get(position);
        vh.temperature_tv.setText(object.getTemperature());
        vh.im.setImageResource(R.mipmap.tianqi);
        vh.weather_tv.setText(object.getWeather());
        vh.week_tv.setText(object.getWeek());
        vh.date.setText(object.getDate());

        return convertView;
    }

    class ViewWeather{
        ImageView im;
        TextView temperature_tv,weather_tv,week_tv,date;
    }
}
