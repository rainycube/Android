package com.rainycube.petbuddy.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rainycube.petbuddy.R;
import com.rainycube.petbuddy.dataset.JSONPetResponse;
import com.rainycube.petbuddy.dataset.ListViewItem;
import com.rainycube.petbuddy.dataset.PetItem;
import com.rainycube.petbuddy.service.PetListServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SBKim on 2016-06-13.
 */
public class ListViewItemAdapter extends BaseAdapter implements RecyclerViewItemAdapter.OnItemClickListener {

    private final String TAG = "ListViewItemAdapter";

    ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    TextView listViewTitle;
    RecyclerView itemRecyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewItemAdapter recyclerViewItemAdapter;
    int resource;
    ArrayList<PetItem> petList = new ArrayList<>();

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

        listViewTitle = (TextView) convertView.findViewById(R.id.txt_title);
        itemRecyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_item);

        listViewTitle.setText(listViewItemList.get(position).getTitle());
        itemRecyclerView.setHasFixedSize(true);
//        itemRecyclerView.setHorizontalFadingEdgeEnabled(true);
//        itemRecyclerView.setFadingEdgeLength(10);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);
        itemRecyclerView.setLayoutManager(layoutManager);

        // RecyclerView 의 Item에 대한 개별 코드 구현 필요
        // Image Loading Library => Picasso or Glide 고려중...

        RequestInterface service = PetListServiceGenerator.createService(RequestInterface.class);
        Call<ResponseBody> call = service.getJson();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "Response is successful");
                    try {
                        Log.d(TAG, response.body().string());
                    } catch (IOException e) {
                        Log.d(TAG, e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error, " + t.getMessage());
            }
        });

        PetItem item = new PetItem();
        item.setPetName("찌루");
        item.setPetType("비글");
        item.setPetGender("남");
        item.setTradeLocation("서울시 영등포구 여의도동");
        item.setPetImgUrl("http://www.stmartinsdogkennels.com/attachments/Slider/0ab6e099-9350-26a0-6981-7e395a3967a1/23695_pets_vertical_store_dogs_small_tile_8_CB312176604_.jpg");
        petList.add(item);

        PetItem item2 = new PetItem();
        item2.setPetType("말티즈");
        item2.setTradeLocation("서울시 강남구 수서동");
        item2.setPetImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Emily_Maltese.jpg/250px-Emily_Maltese.jpg");
        petList.add(item2);

        PetItem item3 = new PetItem();
        item3.setPetType("코통 드 튈레아");
        item3.setTradeLocation("성남시 분당구 정자동");
       item3.setPetImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Coton_de_Tular_2.jpg/250px-Coton_de_Tular_2.jpg");
        petList.add(item3);

        PetItem item4 = new PetItem();
        item4.setPetType("웰시코기");
        item4.setTradeLocation("성남시 분당구 판교동");
        item4.setPetImgUrl("http://ninedog.cafe24.com/web/img/show/cogi2.jpg");
        petList.add(item4);

        recyclerViewItemAdapter = new RecyclerViewItemAdapter(context, petList);
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
        return listViewItemList.size();
    }

    public void addItem(Drawable image, String title) {
        ListViewItem item = new ListViewItem();

        item.setImage(image);
        item.setTitle(title);

        listViewItemList.add(item);
    }
}
