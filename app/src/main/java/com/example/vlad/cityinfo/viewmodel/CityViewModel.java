package com.example.vlad.cityinfo.viewmodel;

import android.app.Application;

import com.example.vlad.cityinfo.db.City;
import com.example.vlad.cityinfo.db.Country;
import com.example.vlad.cityinfo.repository.DataRepository;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class CityViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<Country>> allCountries;
    private LiveData<List<City>> citiesByCountry;
    private MutableLiveData<Country> filterCountry;


    public CityViewModel(Application application) {
        super(application);
        this.dataRepository = DataRepository.getInstance(application);
        this.allCountries = dataRepository.getAllCountries();
        this.filterCountry = new MutableLiveData<>();
        this.citiesByCountry = Transformations.switchMap(filterCountry,
                new Function<Country, LiveData<List<City>>>() {
                    @Override
                    public LiveData<List<City>> apply(Country country) {
                        return dataRepository.getCitiesByCountry(country.getId());
                    }
                });
    }

    public LiveData<List<Country>> getAllCountry() {
        return this.allCountries;
    }

    public void setFilterCountry(Country country) {
        this.filterCountry.setValue(country);
    }

    public LiveData<Country> getFilterCountry() {
        return filterCountry;
    }

    public LiveData<List<City>> getCitiesByFilter() {
        return this.citiesByCountry;
    }
}
