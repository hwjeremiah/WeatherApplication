package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    private MainInfo mainInfo;

    @SerializedName("weather")
    private List<WeatherInfo> weatherInfoList;

    public MainInfo getMain() {
        return mainInfo;
    }

    public List<WeatherInfo> getWeather() {
        return weatherInfoList;
    }

    // Inner classes representing nested JSON objects
    public static class MainInfo {
        @SerializedName("temp")
        private double temp;

        @SerializedName("humidity")
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class WeatherInfo {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }
}
