package com.example.vlad.cityinfo.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {
    @GET("/wikipediaSearchJSON")
    Call<SearchResponse> search(@Query("q") String cityTitle, @Query("maxRows") int maxCount, @Query("username") String userName);
}
