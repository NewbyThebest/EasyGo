package com.newbie.easygo.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.newbie.easygo.R;
import com.newbie.easygo.common.CommonData;
import com.newbie.easygo.common.Constants;
import com.newbie.easygo.common.GoodData;

import java.util.List;

public class MainRvAdapter extends RecyclerView.Adapter<MainRvAdapter.MyHolder> {

    private List<GoodData> mList;
    private OnItemClickListener mOnItemClickListener;
    private boolean mIsShowBtn;

    MainRvAdapter(List<GoodData> list,boolean isShowBtn) {
        mList = list;
        mIsShowBtn = isShowBtn;
    }

    public void setList(List<GoodData> mList) {
        this.mList = mList;
    }

    public interface OnItemClickListener{
        public void onItemClick(GoodData data);
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
        holder.price.setText("￥" + mList.get(position).price);
        holder.seller.setText("商家："+ mList.get(position).seller);

        if (mIsShowBtn){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mList.get(position));

                }
            });
            holder.buy.setVisibility(View.VISIBLE);
            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mList.get(position));
                }
            });
        }else {
            holder.category.setText("类别："+ mList.get(position).category);
            holder.category.setVisibility(View.VISIBLE);
        }



        if (CommonData.getCommonData().getUserType() == Constants.SELLER){
            holder.buy.setText("编辑");
            holder.seller.setVisibility(View.INVISIBLE);
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
        TextView category;
        Button buy;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price);
            seller = itemView.findViewById(R.id.seller);
            category = itemView.findViewById(R.id.tv_category);
            buy = itemView.findViewById(R.id.buy);
        }
    }
}