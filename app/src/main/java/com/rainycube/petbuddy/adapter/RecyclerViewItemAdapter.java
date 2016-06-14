package com.rainycube.petbuddy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainycube.petbuddy.R;
import com.rainycube.petbuddy.dataset.PetItem;

import java.util.List;

/**
 * Created by sbkim on 2016. 6. 13..
 */
public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "RecyclerViewItemAdapter";

    private OnItemClickListener onItemClickListener;

    private List<PetItem> datas;

    private Context context;

    public void setSelectItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onSelectItem(View v, int position);
    }

    public RecyclerViewItemAdapter(Context context, List<PetItem> list) {
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

        // Image 주소 완성되기 전까지...
        if(datas.get(position).getPetId() % 2 ==  0)
            datas.get(position).setPetImgUrl("http://www.stmartinsdogkennels.com/attachments/Slider/0ab6e099-9350-26a0-6981-7e395a3967a1/23695_pets_vertical_store_dogs_small_tile_8_CB312176604_.jpg");
        else if(datas.get(position).getPetId() % 3 == 0)
            datas.get(position).setPetImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Emily_Maltese.jpg/250px-Emily_Maltese.jpg");
        else if(datas.get(position).getPetId() % 4 == 0)
            datas.get(position).setPetImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Coton_de_Tular_2.jpg/250px-Coton_de_Tular_2.jpg");
        else
            datas.get(position).setPetImgUrl("http://ninedog.cafe24.com/web/img/show/cogi2.jpg");

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
        if(datas == null)
            return 0;
        return datas.size();
    }

    // 상대경로 -> 절대경로
    private String getFullPath(String path) {
        StringBuilder s = new StringBuilder("절대주소");
        s.append(path);
        return s.toString();
    }

    public void refresh(List<PetItem> list) {
        this.datas = list;
        notifyDataSetChanged();
        Log.i(TAG, "refresh()");
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
