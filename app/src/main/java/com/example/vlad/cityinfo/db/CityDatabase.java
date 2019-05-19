package com.example.vlad.cityinfo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class, City.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    private static volatile  CityDatabase INSTANCE;

    public static CityDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CityDatabase.class, "city_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract CountryDao countryDao();
    public abstract CityDao cityDao();
}
