package personal.edu.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.entity.Seek_News;

/**
 * Created by Administrator on 2017/1/5 0005.
 */
public class Seek_News_Adapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list;

    public Seek_News_Adapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> getList() {
        return list;
    }

    public void setList(ArrayList<Seek_News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view2= LayoutInflater.from(context).inflate(R.layout.small_message_cent,null);
        MyHolditem myHolditem=new MyHolditem(view2);
        return myHolditem;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MyHolditem) holder).top_tv.setText(list.get(position).getTitle());
        ((MyHolditem) holder).center_tv.setText(list.get(position).getChannelName());
        ((MyHolditem) holder).bott_tv.setText(list.get(position).getPubDate());
        for (int i=0;i<list.get(position).getImageurls().size();i++){
            if(list.get(position).getImageurls().get(i)!=null){
                Glide.with(context).load(list.get(position).getImageurls().get(0).getUrl()).placeholder(R.mipmap.img_news_loding)
                        .error(R.mipmap.img_news_lodinglose).into(((MyHolditem) holder).im);
            }
        }
        if(onNews!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNews.setview(position);
                }
            });
        }

    }
    OnNews onNews;

    public OnNews getOnNews() {
        return onNews;
    }

    public void setOnNews(OnNews onNews) {
        this.onNews = onNews;
    }

    public interface OnNews{
        void setview(int position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolditem extends RecyclerView.ViewHolder{
        ImageView im;
        TextView top_tv,center_tv,bott_tv;
        public MyHolditem(View itemView) {
            super(itemView);
            im= (ImageView) itemView.findViewById(R.id.netwak_center_im);
            top_tv= (TextView) itemView.findViewById(R.id.netwak_center_title_tv);
            center_tv= (TextView) itemView.findViewById(R.id.netwak_center_author_name_tv);
            bott_tv= (TextView) itemView.findViewById(R.id.netwak_center_date_tv);
        }
    }

}
