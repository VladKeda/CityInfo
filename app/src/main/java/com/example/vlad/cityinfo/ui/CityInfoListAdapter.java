package com.example.vlad.cityinfo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vlad.cityinfo.R;
import com.example.vlad.cityinfo.network.CityInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityInfoListAdapter extends RecyclerView.Adapter<CityInfoListAdapter.CityInfoViewHolder> {
    private final LayoutInflater layoutInflater;
    private Context context;
    private List<CityInfo> cityInfos;

    public CityInfoListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CityInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_city_info_item,parent,false);
        return new CityInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityInfoViewHolder holder, int position) {
        if (cityInfos != null) {
            CityInfo info = cityInfos.get(position);
            holder.bind(info);
        } else {
            holder.titleTextView.setText("No Info");
        }
    }

    @Override
    public int getItemCount() {
        if (cityInfos != null) {
            return cityInfos.size();
        }
        return 0;
    }

    public void setCityInfos(List<CityInfo> cityInfos) {
        this.cityInfos = cityInfos;
        notifyDataSetChanged();
    }

    class CityInfoViewHolder extends RecyclerView.ViewHolder
                            implements View.OnClickListener{
        private CityInfo info;
        private TextView titleTextView;
        private TextView summaryTextView;
        private TextView urlTextView;
        private ImageView imageView;

        private CityInfoViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            this.titleTextView = view.findViewById(R.id.info_title);
            this.summaryTextView = view.findViewById(R.id.info_summary);
            this.urlTextView = view.findViewById(R.id.info_url);
            this.imageView = view.findViewById(R.id.info_image);
        }

        private void bind(CityInfo info) {
            this.info = info;
            this.titleTextView.setText(info.getTitle());
            this.summaryTextView.setText(info.getSummary());
            this.urlTextView.setText(info.getWikipediaUrl());
            Picasso.get()
                    .load(info.getThumbnailImg())
                    .fit()
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://" + info.getWikipediaUrl()));
            context.startActivity(intent);
        }
    }
}
