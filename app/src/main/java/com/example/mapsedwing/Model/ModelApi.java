package com.example.mapsedwing.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public class ModelApi {
    public String status;
    public ModelResults results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelResults getResults() {
        return results;
    }

    public void setResults(ModelResults results) {
        this.results = results;
    }
}