package com.example.vlad.cityinfo.ui;

import android.content.Context;
import android.content.Intent;
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
    private Context context;

    public CityListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    class CityViewHolder extends RecyclerView.ViewHolder
                        implements View.OnClickListener{
        private final TextView titleTextView;
        private City city;

        private CityViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            titleTextView = view.findViewById(R.id.city_title);
        }

        private void bind(City city) {
            this.city = city;
            titleTextView.setText(city.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CityInfoListActivity.newIntent(context, city.getTitle());
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_city_item, parent, false);
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
