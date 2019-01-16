package com.demo.assignment.retrofit;

import com.demo.assignment.models.PlayerInfoModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sabyasachi Moharana on 20_02_2018
 */

public interface RequestInterface {

    // url = https://devapi.dawriplus.com/sample.json
    @GET("/sample.json")
    Call<PlayerInfoModel> getPlayerList();
}
