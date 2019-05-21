package com.example.vlad.cityinfo.db;

import android.content.Context;
import android.os.AsyncTask;

import com.example.vlad.cityinfo.network.DataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Database(entities = {Country.class, City.class}, version = 2)
public abstract class CityDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "city_database.db";
    private static volatile  CityDatabase INSTANCE;
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
    private static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static CityDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CityDatabase.class) {
                if (INSTANCE == null) {
                    /*
                    * 1) copyAttachedDatabase(...) - copy db from assets into data folder
                    *       addMigration(...) - is needed
                    * 2) addCallback(...) - populate db after creation
                    *       addMigration(...) - is not needed*/
                    copyAttachedDatabase(context);
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CityDatabase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2)
//                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract CountryDao countryDao();
    public abstract CityDao cityDao();

    private static void copyAttachedDatabase(final Context context) {
        final File dbPath = context.getDatabasePath(DATABASE_NAME);
        if (dbPath.exists()) {
            return;
        }
        dbPath.getParentFile().mkdirs();

        try {
            final InputStream inputStream = context.getAssets().open("database/" + DATABASE_NAME);
            final OutputStream outputStream = new FileOutputStream(dbPath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, buffer.length)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            return null;
        }
    }
}
