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
import com.rainycube.petbuddy.RealmBaseActivity;
import com.rainycube.petbuddy.dataset.PetItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by sbkim on 2016. 6. 13..
 */
public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.ItemViewHolder> {
    private final String TAG = "RecyclerViewItemAdapter";

    private OnItemClickListener onItemClickListener;

    private List<PetItem> pets;

    private Context context;

    private RecyclerView mAttechedRecyclerView;

    private int mClickedPosition = -1;

    public void setSelectItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onSelectItem(View v, int position);
    }

    public RecyclerViewItemAdapter(Context context, List<PetItem> list) {
        this.context = context;
        this.pets = list;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public RecyclerViewItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sample_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemAdapter.ItemViewHolder viewHolder, int position) {
        PetItem petItem = pets.get(position);
        if (petItem != null) {
            ImageView itemImage = viewHolder.itemImage;
            TextView itemTitle = viewHolder.itemTitle;
            TextView itemContent = viewHolder.itemContent;

            Glide.with(getContext()).load(petItem.getPetImgUrl()).centerCrop().into(itemImage);
            itemTitle.setText(petItem.getPetType());
            itemContent.setText(petItem.getTradeLocation());
            Log.d(TAG, "onBindViewHolder() : Item " + Integer.toString(position) + " => " +  petItem.getPetImgUrl());
        }
    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        mAttechedRecyclerView = recyclerView;
//    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if (pets != null)
            return pets.size();
        else
            return 0;
    }

    public Object getItem(int position) {
        if (pets == null || pets.get(position) == null) {
            return null;
        }
        return pets.get(position);
    }

    public void setData(List<PetItem> list) {
        if (pets != null) {
            pets.clear();
            pets.addAll(list);
        }
        else {
            pets = list;
        }
        notifyDataSetChanged();
        Log.d(TAG, "setData() : " + Integer.toString(pets.size()));
    }

    public void updatePets() {
        Realm realm = Realm.getInstance(getRealmConfig());

        // Pull all the petItems from the realm
        RealmResults<PetItem> petItems = realm.where(PetItem.class).findAllSorted(PetItem.DefaultSortField, PetItem.DefaultSortASC);

        // Put these items in the Adapter
        //setData(petItems);
        if(petItems != null) {
                this.pets = petItems;
        }
        notifyDataSetChanged();
        getItemCount();
//        mAttechedRecyclerView.scrollToPosition(0);
//        mAttechedRecyclerView.invalidate();
        realm.close();
    }

    private RealmConfiguration getRealmConfig() {
        return new RealmConfiguration
                .Builder(getContext())
                .name("petitem.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView itemImage;
        TextView itemTitle;
        TextView itemContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemImage = (ImageView) itemView.findViewById(R.id.imv_item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.txt_item_title);
            itemContent = (TextView) itemView.findViewById(R.id.txt_item_content);
        }

        @Override
        public void onClick(View v) {
//            if(onItemClickListener != null) {
//                onItemClickListener.onSelectItem(v, getPosition());
//            }
//            PetItem petItem = (PetItem) getItem(getAdapterPosition());
//            mClickedPosition = this.getAdapterPosition();
//            // Update the realm object affected by the user
//            Realm realm = Realm.getInstance(getRealmConfig());
//
//            // Acquire the list of realm petItems matching the name of the clicked Pet.
//            PetItem pet = realm.where(PetItem.class).equalTo(PetItem.PETID, petItem.getPetId()).findFirst();
//
//            realm.beginTransaction();
//            pet.setPetName(pet.getPetName());
//            pet.setTimeStamp(System.currentTimeMillis());
//            realm.commitTransaction();
//            realm.close();
//            updatePets();
        }
    }
}
