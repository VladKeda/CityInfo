package com.example.vlad.cityinfo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityInfoService {
    private static CityInfoService INSTANCE;
    private static final String BASE_URL = "http://api.geonames.org";
    private static final String USERNAME = "vladkeda";
    private Retrofit retrofit;

    private CityInfoService() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static CityInfoService getInstance() {
        if (INSTANCE == null) {
            synchronized (CityInfoService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CityInfoService();
                }
            }
        }
        return INSTANCE;
    }

    public SearchApi getSearchApi() {
        return retrofit.create(SearchApi.class);
    }
}
