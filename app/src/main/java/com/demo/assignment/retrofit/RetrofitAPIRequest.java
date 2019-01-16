package com.demo.assignment.retrofit;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sabyasachi Moharana on 20_02_2018
 */

public class RetrofitAPIRequest {

    public static final String PRODUCTION_URL = "https://devapi.dawriplus.com";

    private static String TAG = "RetrofitAPIRequest";
    private static Retrofit retrofit;
    private static RetrofitAPIRequest instance;

    public static RetrofitAPIRequest getInstance() {
        if (instance == null) {
            instance = new RetrofitAPIRequest();
        }
        return instance;
    }

    public static Retrofit getRetrofit() {

        if (retrofit == null) {
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(PRODUCTION_URL)
                    .client(getUnsafeOkHttpClient())
                    /*.client(okHttpClient)*/
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .readTimeout(120, TimeUnit.SECONDS).addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .build();
                            return chain.proceed(request);
                        }
                    }).build();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* Generic method for doing
     *  all API requests
     *  in the application */
    public <T> void doRequest(Call<T> call, final RequestListener<T> requestListener) {
        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Log.d(TAG, "Response code:" + response.code());
                Log.d(TAG, "Response message:" + response.message());
                Log.d(TAG, "URL Body:" + call.request().url());

                if (response.code() == 200 || response.code() == 204) {
                    requestListener.onResponse(response.body());
                } else {
                    if (response.message() != null) {
                        Log.d(TAG, "Error Msg:" + response.message());
                        //requestListener.onDisplayError(response.message());
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            requestListener.onDisplayError(jObjError.getString("message"));
                        } catch (Exception e) {
                            requestListener.onDisplayError(e.getMessage());
                        }

                    } else {
                        requestListener.onDisplayError("Server Error, Try Again Later.");
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {

                if (throwable instanceof HttpException) {
                    if (throwable.getMessage() != null) {
                        Log.d(TAG, "Error code:" + ((HttpException) throwable).code());
                        Log.d(TAG, "Error message:" + ((HttpException) throwable).getMessage());
                        Log.d(TAG, "Error Body:" + call.request().url());
                        requestListener.onDisplayError(String.valueOf(((HttpException) throwable).code()) + "\n" + throwable.getMessage());
                    }
                } else {
                    if (throwable != null && throwable.getMessage() != null) {
                        Log.d(TAG, "Error Msg:" + throwable.getMessage());
                        requestListener.onDisplayError(throwable.getMessage());
                        /*if (throwable.getMessage().equalsIgnoreCase("unauthorized")) {

                        }*/
                    } else {
                        requestListener.onDisplayError("Server Error,Try Again Later.");
                    }
                }
            }
        });
    }
}
