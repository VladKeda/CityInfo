package com.example.vlad.cityinfo.network;

import retrofit2.Retrofit;

public class DataService {
    private static DataService INSTANCE;
    private static final String BASE_URL = "https://raw.githubusercontent.com";
    private Retrofit retrofit;

    private DataService() {
        this.retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .build();
    }

    public static DataService getInstance() {
        if (INSTANCE == null) {
            synchronized (DataService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataService();
                }
            }
        }
        return INSTANCE;
    }

    public CitiesByCountriesApi getDataApi() {
        return retrofit.create(CitiesByCountriesApi.class);
    }
}
