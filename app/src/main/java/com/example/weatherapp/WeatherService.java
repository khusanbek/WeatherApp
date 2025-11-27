package com.example.weatherapp;

import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService {

    private static final String API_KEY = "YOUR KEY";

    public interface WeatherCallback {
        void onSuccess(String temp, String icon, String wind);
        void onError(String error);
    }

    public static void fetchWeather(String city, WeatherCallback callback) {

        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid=" + API_KEY
                + "&units=metric";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError("Request failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    JSONObject obj = new JSONObject(json);

                    String temp = obj.getJSONObject("main").getString("temp");
                    String icon = obj.getJSONArray("weather")
                            .getJSONObject(0).getString("icon");
                    String wind = obj.getJSONObject("wind").getString("speed");

                    callback.onSuccess(temp, icon, wind);

                } catch (Exception ex) {
                    callback.onError("Parse error");
                }
            }
        });
    }
}

