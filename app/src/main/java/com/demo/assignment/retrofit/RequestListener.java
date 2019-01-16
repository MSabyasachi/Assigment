package com.demo.assignment.retrofit;

/**
 * Created by Sabyasachi Moharana on 20_02_2018
 */

public interface RequestListener<T> {

    void onResponse(T response);

    void onDisplayError(String errorMsg);
}