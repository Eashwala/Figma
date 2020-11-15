package com.ipl.user.apiutils;

import com.ipl.user.commonutils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebsocketApiClient {

    private static Retrofit retrofit;

    public static Retrofit getApiClientInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.WEBSOCKET_BASE_URL)        //.client(okHttpClient )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
