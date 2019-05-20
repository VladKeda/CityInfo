package com.example.vlad.cityinfo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "city_table",
        foreignKeys = @ForeignKey(entity = Country.class, parentColumns = "id", childColumns = "country_id"))
public class City {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    @ColumnInfo(name = "country_id")
    private long countryId;

    public City() {}

    public City(String title, long countryId) {
        this.title = title;
        this.countryId = countryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }
}
