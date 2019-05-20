package com.example.vlad.cityinfo.db;

import android.content.Context;
import android.os.AsyncTask;

import com.example.vlad.cityinfo.network.DataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            try {
                Response<ResponseBody> response = DataService.getInstance().getDataApi().getData().execute();
                if (response.code() == 200) {
                    JSONObject fullObject = new JSONObject(response.body().string());
                    JSONArray counties = fullObject.names();
                    for (int i = 0; i < counties.length(); i++) {
                        String countryTitle = counties.getString(i);
                        if (countryTitle == null || countryTitle.isEmpty())
                            continue;
                        Country country = new Country(countryTitle);
                        long countryId = countryDao.insert(country);
                        JSONArray citiesByCountry = fullObject.getJSONArray(countryTitle);
                        for (int j = 0; j < citiesByCountry.length(); j++) {
                            String cityTitle = citiesByCountry.getString(j);
                            if (cityTitle == null || cityTitle.isEmpty()) continue;
                            City city = new City(cityTitle, countryId);
                            cityDao.insert(city);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            for (int i = 0; i < 5; i++) {
//                Country country = new Country();
//                country.setTitle("country #" + (i+1));
//                long countryId = countryDao.insert(country);
//                for (int j = 0; j < 15; j++) {
//                    City city = new City();
//                    city.setTitle("city #" + (j+1) + "from country #" + (i+1));
//                    city.setCountryId(countryId);
//                    cityDao.insert(city);
//                }
//            }
            return null;
        }
    }
}
