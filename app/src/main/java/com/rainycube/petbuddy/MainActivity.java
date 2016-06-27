/*
 * Copyright (C) 2016 RainyCube PetBuddy Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rainycube.petbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rainycube.petbuddy.adapter.ListViewItemAdapter;
import com.rainycube.petbuddy.dataset.PetItem;
import com.rainycube.petbuddy.service.PetListServiceGenerator;
import com.rainycube.petbuddy.util.JSONPetResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends RealmBaseActivity implements NavigationView.OnNavigationItemSelectedListener, ListViewItemAdapter.OnRecyclerViewItemClickListener {

    private final String TAG = "MainActivity";

    private final String mainImageUrl = "http://kr.iwall365.com/iPhoneWallpaper/640x960/1511/White-dog-portrait-pink-flowers_640x960_iPhone_4_wallpaper.jpg";

    ImageView imageViewMainHeader;

    ListView listViewMain;

    ListViewItemAdapter listViewItemAdapter;

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            realm = Realm.getInstance(getRealmConfig());
        } catch (RealmMigrationNeededException migrationNeedException) {
            Log.e(TAG, "migrationNeedException path : " + migrationNeedException.getPath());
            Realm.deleteRealm(getRealmConfig());
            realm = Realm.getInstance(getRealmConfig());
        }

        // List Items
        onLoadPetItems();

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLoadPetItems();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(realm.isInTransaction()) {
                realm.commitTransaction();
            }
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRecyclerViewItemSelect(View v, int position) {
        Toast.makeText(this, "Click " + Integer.toString(position), Toast.LENGTH_SHORT).show();
    }

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorHomeActionBarTitle));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 실시간 검색 Activity 구현 : https://realm.io/kr/news/android-search-text-view/
                startActivity(new Intent(MainActivity.this, SearchActivity.class));


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.listview_header, null);

        listViewMain = (ListView) findViewById(R.id.lv_main);
        imageViewMainHeader = (ImageView) findViewById(R.id.imv_main_header);

        Glide.with(this).load(mainImageUrl).centerCrop().into(imageViewMainHeader);

        listViewMain.addHeaderView(listHeader);

        listViewItemAdapter = new ListViewItemAdapter(this, R.layout.listview_row);
        listViewItemAdapter.addItem("Recent searches");
//        listViewItemAdapter.addItem("Recently viewed");
//        listViewItemAdapter.addItem("Just for the weekend");
//        listViewItemAdapter.addItem("Most Popular");
//        listViewItemAdapter.addItem("Favorites");
        listViewItemAdapter.setSelectItemClickListener(this);

        listViewMain.setAdapter(listViewItemAdapter);
    }

    // 상대경로 -> 절대경로
    private String getFullPath(String path) {
        StringBuilder s = new StringBuilder("절대주소");
        s.append(path);
        return s.toString();
    }

    public void onLoadPetItems() {
        RequestInterface service = PetListServiceGenerator.createService(RequestInterface.class);
        Call<ResponseBody> call = service.getPetList("list");
        call.enqueue(new Callback<ResponseBody>() {
                         @Override
                         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                             Log.i(TAG, "onResponse : " + response.isSuccessful());
                             try {
                                 long timestamp = System.currentTimeMillis();
                                 List<PetItem> petList = JSONPetResponse.create(response.body().string());
                                 realm.beginTransaction();
                                 for (PetItem pet : petList) {
                                     pet.setTimeStamp(timestamp);
                                     pet.setPetImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Emily_Maltese.jpg/250px-Emily_Maltese.jpg");
//                                     if (pet.getPetId() % 2 == 0)
//                                         pet.setPetImgUrl("http://www.stmartinsdogkennels.com/attachments/Slider/0ab6e099-9350-26a0-6981-7e395a3967a1/23695_pets_vertical_store_dogs_small_tile_8_CB312176604_.jpg");
//                                     else if (pet.getPetId() % 3 == 0)
//                                         pet.setPetImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Emily_Maltese.jpg/250px-Emily_Maltese.jpg");
//                                     else if (pet.getPetId() % 4 == 0)
//                                         pet.setPetImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Coton_de_Tular_2.jpg/250px-Coton_de_Tular_2.jpg");
//                                     else
//                                         pet.setPetImgUrl("http://ninedog.cafe24.com/web/img/show/cogi2.jpg");
                                 }
                                 Collection<PetItem> updatedPets = realm.copyToRealmOrUpdate(petList);
                                 Log.i(TAG, " item Updated or Created:" + updatedPets.size());
                                 realm.commitTransaction();
                                 final RealmResults<PetItem> allSorted = realm.where(PetItem.class).findAllSorted(PetItem.DefaultSortField, PetItem.DefaultSortASC);
                                 listViewItemAdapter.setData(allSorted);
                             } catch (IOException e) {
                                 Log.e(TAG, "IOException : " + e.getMessage());
                             }
                         }

                         @Override
                         public void onFailure(Call<ResponseBody> call, Throwable t) {
                             Log.e(TAG, "onFailure : " + t.getMessage());
                         }
                     }

        );
    }
}
