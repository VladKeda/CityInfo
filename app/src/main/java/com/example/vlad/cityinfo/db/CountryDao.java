package com.example.vlad.cityinfo.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM country_table ORDER BY title ASC")
    LiveData<List<Country>> getAll();

    @Insert
    long insert(Country country);
}
