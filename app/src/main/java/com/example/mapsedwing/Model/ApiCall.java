package com.example.mapsedwing.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {
    /*@GET("json?lat=36.7201600&lng=-4.4203400")
    Call<ModelApi> getData();*/

    @GET("json?lat=3.4&long=3.5")
    Call<ModelApi> getData(@Query("lat") String lat, @Query("lng") String lng);

    /*@GET("json/{lat}/{lng}")
    Call<ModelApi> getData(@Path("lat") String lat, @Path("lng") String lng);*/

}