package com.rainycube.petbuddy.util;

import android.util.Log;

import com.google.gson.Gson;
import com.rainycube.petbuddy.dataset.PetItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SBKim on 2016-06-14.
 */
public class JSONPetResponse {
    private static final String TAG = "JSONPetResponse";

    public static List<PetItem> create(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            //use GSON to parse
            if (jsonArray != null) {
                Gson gson = new Gson();
                PetItem[] objResponse = gson.fromJson(jsonArray.toString(), PetItem[].class);
                return Arrays.asList(objResponse);
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException : " + e.getMessage());
        }
        return null;
    }
}
