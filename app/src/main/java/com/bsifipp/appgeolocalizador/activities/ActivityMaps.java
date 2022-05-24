package com.bsifipp.appgeolocalizador.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bsifipp.appgeolocalizador.R;
import com.bsifipp.appgeolocalizador.services.PermissionsService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback {
    private PermissionsService permissionsService = new PermissionsService(this);
    private MapView mapview;
    private GoogleMap gmap;
    private Marker marker;
    private MarkerOptions markerOptions = new MarkerOptions();
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private FloatingActionButton fab;
    private double lng;
    private double lat;

    private TextView tvSiglaEstado;
    private TextView tvCidade;
    private TextView tvBairro;
    private TextView tvRua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tvSiglaEstado = findViewById(R.id.tvSiglaEstado);
        tvRua = findViewById(R.id.tvRua);
        tvBairro = findViewById(R.id.tvBairro);
        tvCidade = findViewById(R.id.tvCidade);

        Intent i = getIntent();
        lat = i.getDoubleExtra("lat",0);
        lng = i.getDoubleExtra("lng",0);
        tvSiglaEstado.setText(i.getStringExtra("siglaEstado"));
        tvBairro.setText("Bairro: " + i.getStringExtra("bairro"));
        tvCidade.setText("Cidade: " + i.getStringExtra("cidade"));
        tvRua.setText("Rua: " + i.getStringExtra("rua"));

        mapview = findViewById(R.id.mapView);

        fab=findViewById(R.id.floatingActionButton);

        mapview.setClickable(true);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapview.onCreate(mapViewBundle);
        mapview.getMapAsync(this);
        fab.setOnClickListener(e->{
            marker.setPosition(new LatLng(lat, lng));
            gmap.moveCamera
                    (CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        gmap.setIndoorEnabled(true);
        UiSettings ponto =  gmap.getUiSettings();
        ponto.setIndoorLevelPickerEnabled(true);

        ponto.setMyLocationButtonEnabled(true);
        ponto.setMapToolbarEnabled(true);
        ponto.setCompassEnabled(true);
        ponto.setZoomControlsEnabled(true);
        LatLng latLong = new LatLng(lat, lng);
        gmap.moveCamera
                (CameraUpdateFactory.newLatLng(latLong));
        markerOptions.position(latLong);
        marker=gmap.addMarker(markerOptions);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle (MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle (MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapview.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
    }
    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }
}