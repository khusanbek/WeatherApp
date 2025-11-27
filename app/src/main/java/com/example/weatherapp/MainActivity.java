package com.example.weatherapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    FloatingActionButton addCityBtn;

    ArrayList<String> savedCities;
    ArrayList<CityWeather> weatherList;
    CityAdapter adapter;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.recyclerView);
        addCityBtn = findViewById(R.id.addCityBtn);

        savedCities = CityStorage.loadCities(this);
        weatherList = new ArrayList<>();

        adapter = new CityAdapter(weatherList, this, position -> {
            savedCities.remove(position);
            weatherList.remove(position);
            CityStorage.saveCities(MainActivity.this, savedCities);
            adapter.notifyDataSetChanged();
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        if (savedCities.isEmpty()) {
            savedCities.add("Tashkent"); // default city
        }

        loadAllWeather();

        addCityBtn.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        EditText input = new EditText(this);
        input.setHint("Enter city name (e.g., Tashkent)");

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String city = input.getText().toString().trim();

                    if (city.isEmpty()) {
                        Toast.makeText(this, "City name required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    savedCities.add(city);
                    CityStorage.saveCities(this, savedCities);
                    loadWeather(city);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void loadAllWeather() {
        for (String city : savedCities) loadWeather(city);
    }

    private void loadWeather(String city) {

        WeatherService.fetchWeather(city, new WeatherService.WeatherCallback() {
            @Override
            public void onSuccess(String temp, String icon, String wind) {
                runOnUiThread(() -> {
                    weatherList.add(new CityWeather(city, temp, icon, wind));
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Error loading " + city, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
