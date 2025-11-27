package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.Holder> {

    ArrayList<CityWeather> list;
    Context ctx;

    public interface OnLongClick {
        void remove(int position);
    }

    OnLongClick callback;

    public CityAdapter(ArrayList<CityWeather> list, Context ctx, OnLongClick callback) {
        this.list = list;
        this.ctx = ctx;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_city, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int i) {
        CityWeather city = list.get(i);

        h.cityName.setText(city.cityName);
        h.temp.setText(city.temperature + "°C");
        h.wind.setText("Wind: " + city.windSpeed + " m/s");

        String iconUrl = "https://openweathermap.org/img/wn/" + city.weatherIcon + "@2x.png";
        Glide.with(ctx).load(iconUrl).into(h.icon);

        h.itemView.setOnLongClickListener(v -> {
            callback.remove(i);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView cityName, temp, wind;
        ImageView icon;

        public Holder(@NonNull View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.cityName);
            temp = itemView.findViewById(R.id.cityTemp);
            wind = itemView.findViewById(R.id.cityWind);
            icon = itemView.findViewById(R.id.weatherIcon);
        }
    }
}
