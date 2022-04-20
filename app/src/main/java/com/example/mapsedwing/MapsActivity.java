package com.example.mapsedwing;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mapsedwing.ModelFlickr.DataPhoto;
import com.example.mapsedwing.ModelFlickr.PhotoApiCall;
import com.example.mapsedwing.Threads.ApiThread;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mapsedwing.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "mapsedwing";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private String addressLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        enableMyLocation();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "Latitude: " + latLng.latitude + "   Longitude: " + latLng.longitude);
                LatLng newAddress = new LatLng(latLng.latitude, latLng.longitude);
                getAddress(latLng.latitude, latLng.longitude);
                setMarker(newAddress);

                callAPi(latLng.latitude, latLng.longitude);

                ApiThread apiThread = new ApiThread(latLng.latitude, latLng.longitude);
                apiThread.execute();
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int reason) {
                if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                    Log.d(TAG, "onCameraMoveStarted");
                }
            }
        });

    }

    public void setMarker(LatLng address){
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(address).title(addressLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
    }

    public void getAddress(double lat, double lng) {
        try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lng, 1);
            if (addresses.isEmpty()) {
                Toast.makeText(this, "No s’ha trobat informació", Toast.LENGTH_LONG).show();
            } else {
                if (addresses.size() > 0) {
                    addressLocation = addresses.get(0).getLocality();
                    String msg =addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();

                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "No Location Name Found", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode != LOCATION_PERMISSION_REQUEST_CODE){
            return;
        }

        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callAPi(Double latitude, Double logitude){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/services/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PhotoApiCall apiCall = retrofit.create(PhotoApiCall.class);
        Log.i("testApi", "he llegado 1");
        //Call<ModelApi> call = apiCall.getData(latitude.toString(), logitude.toString());
        Call<DataPhoto> call = apiCall.getData(latitude.toString(), logitude.toString());
        //Call<DataPhoto> call = apiCall.getData();
        call.enqueue(new Callback<DataPhoto>(){
            @Override
            public void onResponse(Call<DataPhoto> call, Response<DataPhoto> response) {
                Log.i("testApi", "he llegado 2");
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }
                ArrayList<String> links = new ArrayList<>();

                for(int i=0; i < 5; i++){

                    String serverId = response.body().getPhotos().getPhoto().get(i).getServer();
                    String id = response.body().getPhotos().getPhoto().get(i).getId();
                    String secret = response.body().getPhotos().getPhoto().get(i).getSecret();
                    String url = "https://live.staticflickr.com/" + serverId + "/" + id + "/" + secret + ".jpg";
                    links.add(url);
                    Log.i("testApi", url);
                }

            }

            @Override
            public void onFailure(Call<DataPhoto> call, Throwable t) {
                Log.i("testApi", call.toString() + " " + t.getMessage());
            }
        });

    }

}