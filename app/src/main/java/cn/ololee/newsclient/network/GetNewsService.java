package cn.ololee.newsclient.network;



import java.util.List;

import cn.ololee.newsclient.bean.NewsBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetNewsService {
    @GET("news")
    Call<NewsBean> getNews(@Query("channelId") int channelId,@Query("time") int time,@Query("skip") int skip,@Query("limit") int limit);
}
