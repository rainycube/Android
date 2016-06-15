package com.rainycube.petbuddy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rainycube.petbuddy.adapter.PetRecyclerViewAdapter;
import com.rainycube.petbuddy.dataset.PetItem;

import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SearchActivity extends AppCompatActivity {

    private final String TAG = "SearchActivity";

    private RealmSearchView realmSearchView;
    private PetRecyclerViewAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadPetData();

        realmSearchView = (RealmSearchView) findViewById(R.id.search_view);
        realm = Realm.getInstance(getRealmConfig());
        adapter = new PetRecyclerViewAdapter(this, realm, "petType");
        realmSearchView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    private void loadPetData() {
        try {

            Realm realm = Realm.getInstance(getRealmConfig());
            RealmResults<PetItem> realmResults = realm.where(PetItem.class).findAll();
            Log.d(TAG, "Result Size : " + Integer.toString(realmResults.size()));

            realm.close();

        } catch (Exception e) {
            Log.d(TAG, "Could not load pet data." + e.getMessage());
        }
    }

    private String complexQuery() {
        String status = "\n\nPerforming complex Query operation...";

//        Realm realm = Realm.getInstance(realmConfig);
//        status += "\nNumber of pets: " + realm.where(PetItem.class).count();
//
//
//        // Find all persons where age between 7 and 9 and name begins with "Person".
//        RealmResults<PetItem> results = realm.where(PetItem.class)
//                .between("age", 7, 9)       // Notice implicit "and" operation
//                .beginsWith("name", "Person").findAll();
//        status += "\nSize of result set: " + results.size();
//
//        realm.close();
        return status;
    }

    private RealmConfiguration getRealmConfig() {
        return new RealmConfiguration
                .Builder(this)
                .name("petitem.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    private void cleanUp() {
        Realm.deleteRealm(getRealmConfig());
    }
}
