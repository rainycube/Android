package com.rainycube.petbuddy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainycube.petbuddy.R;
import com.rainycube.petbuddy.dataset.PetItem;

import java.util.ArrayList;

/**
 * Created by sbkim on 2016. 6. 13..
 */
public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    private ArrayList<PetItem> datas;

    private Context context;
    public void setSelectItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onSelectItem(View v, int position);
    }

    public RecyclerViewItemAdapter(Context context, ArrayList<PetItem> list) {
        this.context = context;
        this.datas = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sample_item, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerItemViewHolder recyclerItemViewHolder = (RecyclerItemViewHolder) viewHolder;
        ImageView itemImage = recyclerItemViewHolder.itemImage;
        TextView itemTitle = recyclerItemViewHolder.itemTitle;
        TextView itemContent = recyclerItemViewHolder.itemContent;

        //itemImage.setImageDrawable(datas.get(position).getImage());
        Glide.with(context).load(datas.get(position).getPetImgUrl()).centerCrop().into(itemImage);
        itemTitle.setText(datas.get(position).getPetType());
        itemContent.setText(datas.get(position).getTradeLocation());
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null) {
                onItemClickListener.onSelectItem(v, getPosition());
            }
        }
    }

    public class RecyclerItemViewHolder extends ItemViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemContent;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.imv_item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.txt_item_title);
            itemContent = (TextView) itemView.findViewById(R.id.txt_item_content);
        }
    }
}
