package com.example.vlad.cityinfo.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vlad.cityinfo.R;
import com.example.vlad.cityinfo.db.City;
import com.example.vlad.cityinfo.db.Country;
import com.example.vlad.cityinfo.viewmodel.CityViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CityListActivity extends AppCompatActivity {
    private CityViewModel cityViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        recyclerView = findViewById(R.id.city_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CityListAdapter cityListAdapter = new CityListAdapter(this);
        recyclerView.setAdapter(cityListAdapter);

        cityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        cityViewModel.getCitiesByFilter().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                cityListAdapter.setCities(cities);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_city_list, menu);
        MenuItem menuItem = menu.findItem(R.id.country_spinner);

        final Spinner spinner = (Spinner) menuItem.getActionView();
        final ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(CityListActivity.this,
                R.layout.layout_drop_title);
        adapter.setDropDownViewResource(R.layout.layout_drop_list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country selectedCountry = (Country) spinner.getSelectedItem();
                cityViewModel.setFilterCountry(selectedCountry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cityViewModel.getAllCountry().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                adapter.clear();
                adapter.addAll(countries);
                adapter.notifyDataSetChanged();
            }
        });

        Country selectedCountry = cityViewModel.getFilterCountry().getValue();
        if (selectedCountry != null) {
            int position = cityViewModel.getAllCountry().getValue().indexOf(selectedCountry);
            spinner.setSelection(position);
        }
        return true;
    }
}
