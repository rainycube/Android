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

import com.rainycube.petbuddy.R;
import com.rainycube.petbuddy.util.JSONPetResponse;
import com.rainycube.petbuddy.dataset.PetItem;
import com.rainycube.petbuddy.service.PetListServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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
    List<PetItem> petList;

    public interface RequestInterface {
        /*
         * Retrofit get annotation with our URL
         * And our method that will return us details of pet.
        */
        @GET("list")
        Call<ResponseBody> getJson();
    }

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
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);
        itemRecyclerView.setLayoutManager(layoutManager);

        // RecyclerView 의 Item에 대한 개별 코드 구현 필요
        // Realm 데이터베이스 사용하여 처음만 데이터 받음

        RequestInterface service = PetListServiceGenerator.createService(RequestInterface.class);
        Call<ResponseBody> call = service.getJson();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Response is successful");
                    try {
                        petList = JSONPetResponse.create(response.body().string());
                        setRealm(petList);
                        recyclerViewItemAdapter.refresh(petList);
                    } catch (IOException e) {
                        Log.e(TAG, "IOException : " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Response is successful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error, " + t.getMessage());
            }
        });

        recyclerViewItemAdapter = new RecyclerViewItemAdapter(context, petList);
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

    private void setRealm( List<PetItem> list) {
        cleanUp();
        Realm realm = Realm.getInstance(getRealmConfig());
        realm.beginTransaction();
        realm.copyToRealm(list);
        realm.commitTransaction();
        realm.close();
    }

    private void basicLinkQuery(Context context) {
        Realm realm = Realm.getInstance(getRealmConfig());
        Log.d(TAG,"Number of pets : " + realm.where(PetItem.class).count());

        RealmResults<PetItem> results = realm.where(PetItem.class).findAll();

        Log.d(TAG, "Size of result set : " + results.size());
        realm.close();
    }

    private RealmConfiguration getRealmConfig() {
        return new RealmConfiguration
                .Builder(context)
                .name("petitem.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    private void cleanUp() {
        Realm.deleteRealm(getRealmConfig());
    }
}
