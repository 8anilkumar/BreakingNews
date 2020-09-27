package com.anilkumarnishad.breakingnews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("top-headlines")
    Call<News> getTopNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageCount
    );

    @GET("everything")
    Call<News> getNews(
            @Query("q") String q,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageCount
    );
}
