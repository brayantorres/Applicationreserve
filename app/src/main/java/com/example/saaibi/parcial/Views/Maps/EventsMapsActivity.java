package com.example.saaibi.parcial.Views.Maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.saaibi.parcial.Domain.Event;
import com.example.saaibi.parcial.Views.AdminActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.saaibi.parcial.R;

import java.util.Locale;

public class EventsMapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener{
    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    Bundle parmetros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Controles UI
        setMyLocationEnabled();

        // Configuración UI
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Eventos
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        // Marcadores
        // Agregar Marcador
        LatLng unac = new LatLng(6.2401624, -75.6104552);

        //Mover la camara a una posicion
        mMap.moveCamera(CameraUpdateFactory.newLatLng(unac));

        //Mover la camara con CameraPosition que nos permite manejar el zoom.
        CameraPosition cameraPosition=CameraPosition.builder()
                .target(unac)
                .zoom(12)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        parmetros = new Bundle();
        parmetros.putString("latitude", String.valueOf(latLng.latitude));
        parmetros.putString("longitude", String.valueOf(latLng.longitude));

        // Añadir marker en la posición
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));

        // Cambiar color del marker
        float hue = BitmapDescriptorFactory.HUE_RED;
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(hue));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        String formatLatLng = String.format(Locale.getDefault(),
                "Lat/Lng = (%f,%f)", latLng.latitude, latLng.longitude);
        Point screentPoint = mMap.getProjection().toScreenLocation(latLng);
        String formatScreenPoint = String.format(Locale.getDefault(),
                "\nPoint = (%d,%d)", screentPoint.x, screentPoint.y);
        Toast.makeText(this, formatLatLng + formatScreenPoint, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add("Siguiente").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();

        if (title != null && title.equals("Siguiente")) {
            Intent i = new Intent(this, AdminActivity.class);
            i.putExtras(parmetros);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        System.out.println("Permisos de mi ubicacion ");
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


    public void setMyLocationEnabled() {
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
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }
}
