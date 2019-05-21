package com.example.vlad.cityinfo.viewmodel;

import com.example.vlad.cityinfo.network.CityInfo;
import com.example.vlad.cityinfo.network.SearchResponse;
import com.example.vlad.cityinfo.repository.CityInfoRepository;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityInfoViewModel extends ViewModel {
    private CityInfoRepository infoRepository;
    private MutableLiveData<String> searchTitle;
    private MutableLiveData<List<CityInfo>> cityInfoResult;

    public CityInfoViewModel() {
        this.infoRepository = CityInfoRepository.getInstance();
        this.searchTitle = new MutableLiveData<>();
        this.cityInfoResult = new MutableLiveData<>();
    }

    public LiveData<String> getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle.setValue(searchTitle);
        loadCityInfo();

    }

    public LiveData<List<CityInfo>> getCityInfo() {
        return cityInfoResult;
    }

    private void loadCityInfo() {
        Call<SearchResponse> responseCall = infoRepository.search(searchTitle.getValue());
        responseCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.code() == 200) {
                    SearchResponse wikiResponse = response.body();
                    List<CityInfo> cityInfoList = wikiResponse.getGeonames();
                    cityInfoResult.postValue(cityInfoList);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }
}
