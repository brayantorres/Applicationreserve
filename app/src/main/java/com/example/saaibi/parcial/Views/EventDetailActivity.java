package com.example.saaibi.parcial.Views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.saaibi.parcial.Domain.Event;
import com.example.saaibi.parcial.R;
import com.example.saaibi.parcial.Model.EventDetailViewModel;
import com.example.saaibi.parcial.Views.Maps.EventsMapsActivity;
import com.example.saaibi.parcial.databinding.EventDetailActivityBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String EXTRA_EVENT = "EXTRA_EVENT";

    private EventDetailActivityBinding eventDetailActivityBinding;

    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    String latitudeDetail ,longitudeDetail,nameDetail,typeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventDetailActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.event_detail_activity);

        setSupportActionBar(eventDetailActivityBinding.toolbar);
        displayHomeAsUpEnabled();
        getExtrasFromIntent();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_LONG).show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public static Intent launchDetail(Context context, Event event) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getExtrasFromIntent() {
        Event event = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);
        infoDetailEvents(event);
        EventDetailViewModel eventDetailViewModel = new EventDetailViewModel(event);
        eventDetailActivityBinding.setEventDetailViewModel(eventDetailViewModel);
    }

    private void infoDetailEvents(Event event) {
        latitudeDetail = event.getLatitude();
        longitudeDetail = event.getLongitude();
        nameDetail = event.getNameEvent();
        typeDetail = event.getTipeEvent();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Controles UI

        setMyLocationEnabled();

        // Configuración UI
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Marcadores
        LatLng latlng= new LatLng(Double.parseDouble(latitudeDetail),Double.parseDouble(longitudeDetail));
        mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title(nameDetail)
                .snippet(typeDetail)
                .draggable(true));

        //Mover la camara a una posicion
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        //Mover la camara con CameraPosition que nos permite manejar el zoom.
        CameraPosition cameraPosition=CameraPosition.builder()
                .target(latlng)
                .zoom(12)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setMyLocationEnabled() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                 == PackageManager.PERMISSION_GRANTED) {
             mMap.setMyLocationEnabled(true);
         } else {
             if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                     Manifest.permission.ACCESS_FINE_LOCATION)) {
                 // Mostrar diálogo explicativo
             } else {
                 // Solicitar permiso
                 ActivityCompat.requestPermissions(
                         this,
                         new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                         LOCATION_REQUEST_CODE);
             }
         }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }


}
