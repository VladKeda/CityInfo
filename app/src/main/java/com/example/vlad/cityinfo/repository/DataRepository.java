package com.example.vlad.cityinfo.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.vlad.cityinfo.db.City;
import com.example.vlad.cityinfo.db.CityDao;
import com.example.vlad.cityinfo.db.CityDatabase;
import com.example.vlad.cityinfo.db.Country;
import com.example.vlad.cityinfo.db.CountryDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DataRepository {
    private CountryDao countryDao;
    private CityDao cityDao;

    public DataRepository(Application application) {
        CityDatabase db = CityDatabase.getInstance(application);
        this.countryDao = db.countryDao();
        this.cityDao = db.cityDao();
    }

    public LiveData<List<Country>> getAllCountry() {
        return countryDao.getAll();
    }

    public LiveData<List<City>> getCitiesByCountry(long countryId) {
        return cityDao.getByCountryId(countryId);
    }

    public void insertCountry(Country country) {
        new InsertCountryAsyncTask(countryDao).execute(country);
    }

    public void insertCity(City city) {
        new InsertCityAsyncTask(cityDao).execute(city);
    }

    private static class InsertCountryAsyncTask extends AsyncTask<Country, Void, Void> {
        private CountryDao asyncTaskDao;

        public InsertCountryAsyncTask(CountryDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Country... countries) {
            asyncTaskDao.insert(countries[0]);
            return null;
        }
    }

    private static class InsertCityAsyncTask extends AsyncTask<City, Void, Void> {
        private CityDao asyncTaskDao;

        public InsertCityAsyncTask(CityDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(City... cities) {
            asyncTaskDao.insert(cities[0]);
            return null;
        }
    }
}
