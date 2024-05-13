package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.WeatherApiClient;
import com.example.weatherapp.WeatherApiService;
import com.example.weatherapp.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText locationEditText;
    private Button getWeatherButton;
    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView weatherConditionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationEditText = findViewById(R.id.locationEditText);
        getWeatherButton = findViewById(R.id.getWeatherButton);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        weatherConditionTextView = findViewById(R.id.weatherConditionTextView);

        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationEditText.getText().toString();
                fetchWeatherData(location);
            }
        });
    }

    private void fetchWeatherData(String location) {
        WeatherApiService apiService = WeatherApiClient.getClient().create(WeatherApiService.class);
        Call<WeatherResponse> call = apiService.getWeather(location, "56d230da4b09a99175990b962ff8924e", "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    updateUI(weatherResponse);
                } else {
                    Log.e(TAG, "Failed to fetch weather data. Response code: " + response.code());
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching weather data", t);
                // Handle failure
            }
        });
    }

    private void updateUI(WeatherResponse weatherResponse) {
        temperatureTextView.setText("Temperature: " + weatherResponse.getMain().getTemp() + "°C");
        humidityTextView.setText("Humidity: " + weatherResponse.getMain().getHumidity() + "%");
        weatherConditionTextView.setText("Weather Condition: " + weatherResponse.getWeather().get(0).getDescription());
    }
}
