package com.example.mapsedwing.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public class ModelResults {
    public String sunrise;
    public String sunset;

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}


