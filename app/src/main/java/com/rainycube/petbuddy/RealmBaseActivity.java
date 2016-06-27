package com.rainycube.petbuddy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rainycube.petbuddy.dataset.PetItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected RealmConfiguration getRealmConfig() {
        return new RealmConfiguration
                .Builder(this)
                .name("petitem.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    protected void cleanUp() {
        Realm.deleteRealm(getRealmConfig());
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
//        Log.d(TAG,"Number of pets : " + realm.where(PetItem.class).count());

        RealmResults<PetItem> results = realm.where(PetItem.class).findAll();

//        Log.d(TAG, "Size of result set : " + results.size());
        realm.close();
    }
}
