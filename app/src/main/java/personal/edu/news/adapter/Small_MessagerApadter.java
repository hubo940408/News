package personal.edu.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.entity.Entity;

/**
 * 新闻内容碎片适配器
 * Created by Administrator on 2017/1/5 0005.
 */
public class Small_MessagerApadter extends RecyclerView.Adapter {


    private final int NETWAK_TOP=0;
    private final int NETWAK_CENTER=1;
    private final int NETWAK_BOTT=2;

    Context context;
    ArrayList<Entity.ResultBean.DataBean>list;
    int state;//尾部状态
    public static final int DATA_LOADING=0;//数据加载中
    public static final int DATA_FINISH=1;//数据加载完成
    public static final int DATA_NOMORE=2;//没有更多数据

    public Small_MessagerApadter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<Entity.ResultBean.DataBean> getList() {
        return list;
    }

    public void setList(ArrayList<Entity.ResultBean.DataBean> list) {
        this.list = list;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载首尾不同的布局
        if(viewType==NETWAK_TOP){
            //头布局
            View view1= LayoutInflater.from(context).inflate(R.layout.small_message_top,null);
            MyViewHoldtop myViewHoldtop=new MyViewHoldtop(view1);
            return myViewHoldtop;
        }else if(viewType == NETWAK_CENTER){
            //中间布局
            View view2=LayoutInflater.from(context).inflate(R.layout.small_message_cent,null);
            MyViewHoldcenter myViewHoldcenter=new MyViewHoldcenter(view2);
            return myViewHoldcenter;
        }else if(viewType==NETWAK_BOTT){
            //尾布局
            View view3=LayoutInflater.from(context).inflate(R.layout.small_message_botton,null);
            MyViewHoldbot myViewHoldbot=new MyViewHoldbot(view3);
            return myViewHoldbot;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyViewHoldtop){
            ((MyViewHoldtop) holder).tv.setText(list.get(position).getTitle());
            Glide.with(context).load(list.get(position).getThumbnail_pic_s()).placeholder(R.mipmap.img_news_loding)
                    .error(R.mipmap.img_news_lodinglose).into(((MyViewHoldtop) holder).im);
        }else if(holder instanceof MyViewHoldcenter){
            ((MyViewHoldcenter) holder).top_tv.setText(list.get(position).getTitle());
            ((MyViewHoldcenter) holder).center_tv.setText(list.get(position).getAuthor_name());
            ((MyViewHoldcenter) holder).bott_tv.setText(list.get(position).getDate());
            Glide.with(context).load(list.get(position).getThumbnail_pic_s()).placeholder(R.mipmap.img_news_loding)
                    .error(R.mipmap.img_news_lodinglose).into(((MyViewHoldcenter) holder).im);
        }else if(holder instanceof MyViewHoldbot){
            switch (getState()) {
                case DATA_FINISH:
                    ((MyViewHoldbot) holder).tv.setText("上拉加载更多......");
                    ((MyViewHoldbot) holder).pb.setVisibility(View.GONE);
                    break;
                case DATA_LOADING:
                    Log.i("msg", "正在努力加载......");
                    ((MyViewHoldbot) holder).tv.setText("正在努力加载......");
                    ((MyViewHoldbot) holder).pb.setVisibility(View.VISIBLE);
                    break;
                case DATA_NOMORE:
                    ((MyViewHoldbot) holder).tv.setText("没有更多数据......");
                    ((MyViewHoldbot) holder).pb.setVisibility(View.GONE);
                    break;
            }
        }

        if(onItem!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItem.send(position);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        //判断布局位置
        if(list.size()==0){
            return NETWAK_BOTT;
        }
        if(position==0){
            return NETWAK_TOP;
        }else if(position<list.size()){
            return NETWAK_CENTER;
        }else {
            return NETWAK_BOTT;
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class MyViewHoldbot extends RecyclerView.ViewHolder{
        TextView tv;
        ProgressBar pb;
        public MyViewHoldbot(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.netwak_bottom_tv);
            pb= (ProgressBar) itemView.findViewById(R.id.netwak_bottom_pb);
        }
    }

    class MyViewHoldtop extends RecyclerView.ViewHolder{
        ImageView im;
        TextView tv;
        public MyViewHoldtop(View itemView) {
            super(itemView);
            im= (ImageView) itemView.findViewById(R.id.netwark_top_im);
            tv= (TextView) itemView.findViewById(R.id.netwark_top_tv);
        }
    }

    class MyViewHoldcenter extends RecyclerView.ViewHolder{
        ImageView im;
        TextView top_tv,center_tv,bott_tv;
        public MyViewHoldcenter(View itemView) {
            super(itemView);
            im= (ImageView) itemView.findViewById(R.id.netwak_center_im);
            top_tv= (TextView) itemView.findViewById(R.id.netwak_center_title_tv);
            center_tv= (TextView) itemView.findViewById(R.id.netwak_center_author_name_tv);
            bott_tv= (TextView) itemView.findViewById(R.id.netwak_center_date_tv);
        }
    }

    public interface OnItem{
        void send(int position);
    }

    OnItem onItem;

    public OnItem getOnItem() {
        return onItem;
    }

    public void setOnItem(OnItem onItem) {
        this.onItem = onItem;
    }
}

