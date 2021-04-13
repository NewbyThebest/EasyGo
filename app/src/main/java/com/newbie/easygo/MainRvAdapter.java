package com.newbie.easygo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MainRvAdapter extends RecyclerView.Adapter<MainRvAdapter.MyHolder> {

    private List<GoodData> mList;//数据源
    private OnItemClickListener mOnItemClickListener;

    MainRvAdapter(List<GoodData> list) {
        mList = list;
    }

    public interface OnItemClickListener{
        public void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.title.setText(mList.get(position).title);
        holder.price.setText(mList.get(position).price);
        holder.seller.setText(mList.get(position).seller);

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick();
            }
        });

        if (CommonData.getCommonData().getUserType() == Constants.SELLER){
            holder.buy.setText("编辑");
        }

        Glide.with(holder.img.getContext()).load(mList.get(position).imgUrl).into(holder.img);
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 自定义的ViewHolder
     */
    class MyHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;
        TextView price;
        TextView seller;
        Button buy;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price);
            seller = itemView.findViewById(R.id.seller);
            buy = itemView.findViewById(R.id.buy);
        }
    }
}