package personal.edu.news.adapter;

import android.app.Activity;
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
import personal.edu.news.Shade.Shade_Main;
import personal.edu.news.entity.Entity;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.BaseViewHolder>  {
    Context context;
    ArrayList<Entity.ResultBean.DataBean> list;

    public ArrayList<Entity.ResultBean.DataBean> getList() {
        return list;
    }

    public void setList(ArrayList<Entity.ResultBean.DataBean> list) {
        this.list = list;
    }

    public DemoAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.redian_item,null);
        BaseViewHolder baseViewHolder=new BaseViewHolder(view);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getThumbnail_pic_s()).placeholder(R.mipmap.img_news_loding)
                .error(R.mipmap.img_news_lodinglose).into(((BaseViewHolder) holder).im);
        holder.tv.setText(list.get(position).getTitle());
        if(onData!=null){
            ((BaseViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onData.ondata(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView im;
        TextView tv;
        public BaseViewHolder(View itemView) {
            super(itemView);
            im= (ImageView) itemView.findViewById(R.id.redian_item_im);
            tv= (TextView) itemView.findViewById(R.id.redian_item_tv);
           int width= Shade_Main.getwidth(context);
            ViewGroup.LayoutParams params = im.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width/2;
            params.height =  (int) (200 + Math.random() * 400) ;
            im.setLayoutParams(params);
        }
    }


    OnData onData;

    public OnData getOnData() {
        return onData;
    }

    public void setOnData(OnData onData) {
        this.onData = onData;
    }

    public interface OnData{
        void ondata(int position);
    }


}
