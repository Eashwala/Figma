package com.ipl.user.apiutils;


import com.ipl.user.commonutils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    // private static String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNTliNzk4Nzc0MmM3ZTRjZTQ1ZWQzYzNmOWFmNmUxN2Y0NDE4OWQ2MzgyMGUzOGRhYThmY2RjNGIyOTU0OWQ0ZDM4Njk0YWVhZDNjZTc2MzYiLCJpYXQiOjE1OTQ2MzQzOTcsIm5iZiI6MTU5NDYzNDM5NywiZXhwIjoxNjI2MTcwMzk3LCJzdWIiOiI5Iiwic2NvcGVzIjpbIioiXX0.KaOUYd6DAkUAD3WlkZKry_5R3ZkqGTnEHXtiZWz1SFRgBQnX81rF7D3E_DP48ikZXhFE-zt0-gq86i5P3Ykip99RgZeRO9CLjiUnifW2AIIYlLeTQ9IRprgrFIH40nm-K0ioSQGlfmQKG2NfvcriMkFPLSPx27baCPmUX7hJ9xvo7r34ZrbPiISymzNRvJxmozLZ0sm3zLJBjiyebr59dXz3XqBTwpmjkLBjcJPUbIrz4IkSy5WsoruzV48us4wLn4OogIlflfOX5h6UMUWVdJ-bYnq93CScFEbkHm-NxQEAMzo68Fzg22k29XY-DC3iFPpHrtiifxXpEWxzaWG5bTDC0PSpr0HmTxLWKV4pEhjHyVsUfVmpRiY-z2mUhUvkN_iDMXVErQPTTH3X97Fp-uhqLI37YznrkgVonj-dvS_KGzY8HcBwDwX7pJJtV_aGq9PMEbY57krCP5qej_ZFx0EgqoZGnf0rHVbUcfD1kj-5-QXhdbGt7MZ5gM9ZQqX31DD6utgXPqYIZ_Fy4Zef2Pgyy8omT5bLNeZs4uytGV1sa2UwkrNXbHOCw14dtBNyHyRzOzOUyK-CEenc74yfMP3NxS8Kc_m7VQ6_9wK9YccXk-TvmHYgRUDiRz0oCsLcKrJWaarexsSraKKV5GdMC02XQKEFYTYCeTocqxM0TXs";
  //  private static final String BASE_URL = "https://zvcg1rgvk7.execute-api.us-east-1.amazonaws.com/Prod/";

    public static Retrofit getApiClientInstance() {
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
                    .baseUrl(Constants.BASE_URL)        //.client(okHttpClient )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
