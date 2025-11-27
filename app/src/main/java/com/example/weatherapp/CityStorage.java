package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.ArrayList;

public class CityStorage {

    private static final String PREF = "cities";

    public static void saveCities(Context ctx, ArrayList<String> cities) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        JSONArray arr = new JSONArray(cities);
        ed.putString("list", arr.toString());
        ed.apply();
    }

    public static ArrayList<String> loadCities(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        ArrayList<String> list = new ArrayList<>();

        try {
            String json = sp.getString("list", "[]");
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.getString(i));
            }
        } catch (Exception ignore) {}

        return list;
    }
}
