package com.example.vlad.cityinfo.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CityDao {
    @Query("SELECT * FROM city_table WHERE country_id = :countryId ORDER BY title ASC")
    LiveData<List<City>> getByCountryId(long countryId);

    @Insert
    long insert(City city);
}
