package com.ipl.user.apiutils;

import com.ipl.user.commonutils.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CognitoClient {
    private static Retrofit retrofit;
    public static Retrofit getCognitoClientInstance() {
        if (retrofit == null) {

//            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request original = chain.request();
//                    Request.Builder reqbuilder = original.newBuilder().addHeader("Authorization",token).method(original.method(), original.body());
//
//              Request request = reqbuilder.build();
//              return chain.proceed(request);
//
//                }
//            }).build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.cognitodomainname)   //.client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

//public class MyOkHttpInterceptor implements Interceptor {
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//        if (!"/posts".contains(originalRequest.url()) ) {
//            return chain.proceed(originalRequest);
//        }
//
//                      Request newRequest = originalRequest.newBuilder()
//                .header("X-Authorization", token)
//                .build();
//
//        return chain.proceed(newRequest);
//    }
//
