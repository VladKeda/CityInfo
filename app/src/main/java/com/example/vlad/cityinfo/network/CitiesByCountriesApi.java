package com.example.vlad.cityinfo.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CitiesByCountriesApi {
    @GET("/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json")
    Call<ResponseBody> getData();
}
