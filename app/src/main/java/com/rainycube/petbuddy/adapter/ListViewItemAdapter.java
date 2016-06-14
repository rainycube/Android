package com.rainycube.petbuddy.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rainycube.petbuddy.R;
import com.rainycube.petbuddy.dataset.ListViewItem;
import com.rainycube.petbuddy.dataset.RecyclerViewItem;

import java.util.ArrayList;

/**
 * Created by SBKim on 2016-06-13.
 */
public class ListViewItemAdapter extends BaseAdapter implements RecyclerViewItemAdapter.OnItemClickListener{
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    TextView title;
    RecyclerView itemRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewItemAdapter recyclerViewItemAdapter;
    int resource;

    public void setSelectItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        public void onRecyclerViewItemSelect(View v, int position);
    }

    public ListViewItemAdapter(int resource) {
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        title = (TextView) convertView.findViewById(R.id.txt_title);
        title.setText(listViewItemList.get(position).getTitle());
        itemRecyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_item);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setHorizontalFadingEdgeEnabled(true);
        itemRecyclerView.setFadingEdgeLength(10);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        mLayoutManager.setStackFromEnd(true);
        itemRecyclerView.setLayoutManager(mLayoutManager);

        // RecyclerView 의 Item에 대한 개별 코드 구현 필요
        // Image Loading Library => Picasso or Glide 고려중...

        ArrayList<RecyclerViewItem> sampleList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            RecyclerViewItem item = new RecyclerViewItem();
            item.setImage(listViewItemList.get(position).getImage());
            item.setTitle("Item " + i);
            item.setContent("item " + i + " Content");
            sampleList.add(item);
        }

        recyclerViewItemAdapter = new RecyclerViewItemAdapter(context, sampleList);
        recyclerViewItemAdapter.setSelectItemClickListener(this);
        itemRecyclerView.setAdapter(recyclerViewItemAdapter);
        return convertView;
    }

    @Override
    public void onSelectItem(View v, int position) {
        onRecyclerViewItemClickListener.onRecyclerViewItemSelect(v, position);
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
        return listViewItemList.size() ;
    }

    public void addItem(Drawable image, String title) {
        ListViewItem item = new ListViewItem();

        item.setImage(image);
        item.setTitle(title);

        listViewItemList.add(item);
    }
}
