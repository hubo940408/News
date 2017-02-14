package personal.edu.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.news.R;
import personal.edu.news.entity.ViewPager_User;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
public class Add_RecyclerView_Adapter extends RecyclerView.Adapter<Add_RecyclerView_Adapter.MyViewHolder> {
    Context context;
    ArrayList<ViewPager_User> list;
    public Add_RecyclerView_Adapter(Context context) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.add_recyclerview_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position).getName());
        if(onClick!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onclick(position);//短按
                }
            });
        }
    }
    OnClick onClick;

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void onclick(int position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.add_recycler_tv);
        }
    }
}
