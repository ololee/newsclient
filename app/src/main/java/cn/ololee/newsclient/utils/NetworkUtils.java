package cn.ololee.newsclient.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    public static final String BASE_URL="http://ololee.cn:8080";
    private static class SingleRetrofit{
        private static final Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    private NetworkUtils() {
    }

    public static Retrofit getRetrofit(){
        return SingleRetrofit.retrofit;
    }
}
