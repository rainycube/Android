package com.rainycube.petbuddy.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rainycube.petbuddy.R;
import com.rainycube.petbuddy.util.JSONPetResponse;
import com.rainycube.petbuddy.dataset.PetItem;
import com.rainycube.petbuddy.service.PetListServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by SBKim on 2016-06-13.
 */
public class ListViewItemAdapter extends BaseAdapter {

    private final String TAG = "ListViewItemAdapter";

    ArrayList<String> listViewItemList = new ArrayList<>();
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    TextView listViewTitle;
    RecyclerView itemRecyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewItemAdapter recyclerViewItemAdapter;
    Context context;
    int resource;

    public void setSelectItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        public void onRecyclerViewItemSelect(View v, int position);
    }

    public ListViewItemAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        listViewTitle = (TextView) convertView.findViewById(R.id.txt_title);
        itemRecyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_item);

        listViewTitle.setText(listViewItemList.get(position).toString());
        itemRecyclerView.setHasFixedSize(true);
//        itemRecyclerView.setHorizontalFadingEdgeEnabled(true);
//        itemRecyclerView.setFadingEdgeLength(10);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setStackFromEnd(true);
        itemRecyclerView.setLayoutManager(layoutManager);

        List<PetItem> list = new ArrayList<>();
        PetItem petItem = new PetItem();
        petItem.setPetId(0);
        petItem.setPetName("쥬쥬");
        petItem.setPetType("닥스훈트");
        petItem.setPetGender("수컷");
        petItem.setTradeLocation("분당");
        petItem.setPetImgUrl("http://ninedog.cafe24.com/web/img/show/cogi2.jpg");
        petItem.setTimeStamp(System.currentTimeMillis());
        list.add(petItem);
        recyclerViewItemAdapter = new RecyclerViewItemAdapter(context, list);
        recyclerViewItemAdapter.setSelectItemClickListener(new RecyclerViewItemAdapter.OnItemClickListener() {
            @Override
            public void onSelectItem(View v, int position) {
                onRecyclerViewItemClickListener.onRecyclerViewItemSelect(v, position);
            }
        });
        itemRecyclerView.setAdapter(recyclerViewItemAdapter);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    public void addItem(String title) {
        listViewItemList.add(title);
    }

    public void setData(List<PetItem> list) {
        recyclerViewItemAdapter.setData(list);
//        recyclerViewItemAdapter.notifyDataSetChanged();
        Log.d(TAG, "setData()");
    }

}
