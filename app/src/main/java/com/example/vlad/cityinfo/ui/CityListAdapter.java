package com.example.vlad.cityinfo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vlad.cityinfo.R;
import com.example.vlad.cityinfo.db.City;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<City> cities;

    public CityListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;

        private CityViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.city_title);
        }

        private void bind(City city) {
            titleTextView.setText(city.getTitle());
        }
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        if (cities != null) {
            City city = cities.get(position);
            holder.bind(city);
        } else {
            holder.titleTextView.setText("No City");
        }
    }

    @Override
    public int getItemCount() {
        if (cities != null) {
            return cities.size();
        }
        return 0;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }
}
