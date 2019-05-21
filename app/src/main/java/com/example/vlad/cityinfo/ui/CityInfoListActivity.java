package com.example.vlad.cityinfo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.vlad.cityinfo.R;
import com.example.vlad.cityinfo.network.CityInfo;
import com.example.vlad.cityinfo.viewmodel.CityInfoViewModel;

import java.util.List;

public class CityInfoListActivity extends AppCompatActivity {
    private static final String EXTRA_SEARCH = "com.example.vlad.cityinfo.ui.search";
    private CityInfoViewModel infoViewModel;
    private RecyclerView recyclerView;

    public static Intent newIntent(Context context, String searchTitle) {
        Intent intent = new Intent(context, CityInfoListActivity.class);
        intent.putExtra(EXTRA_SEARCH, searchTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info_list);

        recyclerView = findViewById(R.id.city_info_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CityInfoListAdapter adapter = new CityInfoListAdapter(this);
        recyclerView.setAdapter(adapter);

        infoViewModel = ViewModelProviders.of(this).get(CityInfoViewModel.class);
        String searchTitle = infoViewModel.getSearchTitle().getValue();
        if (searchTitle == null || searchTitle.isEmpty()) {
            searchTitle = getIntent().getStringExtra(EXTRA_SEARCH);
            infoViewModel.setSearchTitle(searchTitle);
        }

        infoViewModel.getCityInfo().observe(this, new Observer<List<CityInfo>>() {
            @Override
            public void onChanged(List<CityInfo> cityInfos) {
                adapter.setCityInfos(cityInfos);
            }
        });
    }
}
