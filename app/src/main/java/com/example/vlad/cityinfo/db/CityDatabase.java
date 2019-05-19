package com.example.vlad.cityinfo.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Country.class, City.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    private static volatile  CityDatabase INSTANCE;
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static CityDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CityDatabase.class, "city_database")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract CountryDao countryDao();
    public abstract CityDao cityDao();

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final CountryDao countryDao;
        private final CityDao cityDao;

        public PopulateDbAsync(CityDatabase db) {
            this.countryDao = db.countryDao();
            this.cityDao = db.cityDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 5; i++) {
                Country country = new Country();
                country.setTitle("country #" + (i+1));
                long countryId = countryDao.insert(country);
                for (int j = 0; j < 15; j++) {
                    City city = new City();
                    city.setTitle("city #" + (j+1) + "from country #" + (i+1));
                    city.setCountryId(countryId);
                    cityDao.insert(city);
                }
            }
            return null;
        }
    }
}
