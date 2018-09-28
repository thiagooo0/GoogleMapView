package com.ksw.googlemapviewsimple;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ksw.googlemapview.been.InfoWindow;
import com.ksw.googlemapviewsimple.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MapListener {
    private static final String TAG = "KwokSiuWangMap";
    private ActivityMainBinding binding;
    private GoogleMap googleMap;
    private Marker homeMaker;
    private Marker place1Maker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //as same as Google MapView
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(googleMap -> {
            MainActivity.this.googleMap = googleMap;
            Log.d(TAG, "地图加载成功");
            Toast.makeText(MainActivity.this, "地图加载成功", Toast.LENGTH_SHORT).show();
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(23, 43)));

            //add some marker.
            homeMaker = googleMap.addMarker(new MarkerOptions().position(new LatLng(23.15, 113.44)));
            place1Maker = googleMap.addMarker(new MarkerOptions().position(new LatLng(50.15, 90.44)));

            googleMap.setOnMarkerClickListener(marker -> false);
            googleMap.setOnCameraMoveListener(() -> binding.mapView.updateInfoWindow());
            googleMap.setOnCameraIdleListener(() -> binding.mapView.updateInfoWindow());
        });
        binding.setVariable(BR.mapListener, this);
        binding.mapView.setAutoUpdate(false);
    }

    private InfoWindow homeInfoWindow;
    private InfoWindow placeInfoWindow;

    @Override
    public void addHome() {
        if (homeInfoWindow == null) {
            homeInfoWindow = new InfoWindow(R.layout.item_home, homeMaker);
        }
        binding.mapView.showInfoWindow(homeInfoWindow);
        TextView tvTitle = homeInfoWindow.getInfoWindow().findViewById(R.id.title);
        tvTitle.setOnClickListener(v -> Toast.makeText(MainActivity.this, "this is my home", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void removeHome() {
        if (homeInfoWindow != null)
            binding.mapView.dismissInfoWindow(homeInfoWindow);
    }

    @Override
    public void addPlace1() {
        if (placeInfoWindow == null) {
            TextView textView = new TextView(this);
            textView.setHeight(300);
            textView.setWidth(209);
            textView.setText("just a normal textview");
            placeInfoWindow = new InfoWindow(textView, place1Maker, 100, 100);
        }
        binding.mapView.showInfoWindow(placeInfoWindow);
    }

    @Override
    public void removePlace1() {
        binding.mapView.dismissInfoWindow(placeInfoWindow);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
}
