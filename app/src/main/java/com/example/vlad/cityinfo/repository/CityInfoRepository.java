package com.example.vlad.cityinfo.repository;

import com.example.vlad.cityinfo.network.CityInfo;
import com.example.vlad.cityinfo.network.CityInfoService;
import com.example.vlad.cityinfo.network.SearchApi;
import com.example.vlad.cityinfo.network.SearchResponse;

import retrofit2.Call;

public class CityInfoRepository {
    private static final String USERNAME = "vladkeda";
    private static CityInfoRepository INSTANCE;
    private SearchApi searchApi;

    private CityInfoRepository() {
        this.searchApi = CityInfoService.getInstance().getSearchApi();
    }

    public static CityInfoRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (CityInfoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CityInfoRepository();
                }
            }
        }
        return INSTANCE;
    }

    public Call<SearchResponse> search(String cityTitle) {
        return searchApi.search(cityTitle, 10, USERNAME);
    }

    public Call<SearchResponse> search(String cityTitle, int maxCount) {
        return searchApi.search(cityTitle, maxCount, USERNAME);
    }
}
