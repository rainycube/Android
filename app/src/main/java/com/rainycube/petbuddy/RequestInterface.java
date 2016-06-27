package com.rainycube.petbuddy;

import com.rainycube.petbuddy.dataset.PetItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by SBKim on 2016-06-27.
 */
public interface RequestInterface {
    @GET("{fileName}")
    Call<ResponseBody> getPetList(
            @Path("fileName") String fileName
    );
}
