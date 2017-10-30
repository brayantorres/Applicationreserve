package com.example.saaibi.parcial.Views.Maps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saaibi.parcial.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import static android.Manifest.permission.*;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        AdapterView.OnItemSelectedListener,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int REQUEST_LOCATION = 1;
    private GoogleMap mMap;

    private LocationManager mLocationManager;

    private String provider;


    private Spinner mMapTypeSelector;
    private int mMapTypes[] = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN
    };

    private TextView latituteField;
    private TextView longitudeField;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    Marker currLocationMarker;


    public MapsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        latituteField = (TextView) findViewById(R.id.txtLat);
        longitudeField = (TextView) findViewById(R.id.txtLon);


        mMapTypeSelector = (Spinner) findViewById(R.id.map_type_selector);
        mMapTypeSelector.setOnItemSelectedListener(this);

        // Establecer punto de entrada para la API de ubicación
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get the location manager
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        // default
        Criteria criteria = new Criteria();
        provider = mLocationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(provider);

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");

        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Eventos
        mMap.setOnMapClickListener(this);

        // Agregar Marcador
        LatLng unac = new LatLng(6.2401624, -75.6104552);
        mMap.addMarker(new MarkerOptions()
                .position(unac)
                .title("UNAC")
                .snippet("Corporacion Universitaria Adventista "));


        //se agregan otros marcadores
        LatLng unal= new LatLng(4.6381938,-74.1015559);
        mMap.addMarker(new MarkerOptions()
                .position(unal)
                .title("Universidad Nacional")
                .draggable(true));

        LatLng uCaldas= new LatLng(5.055867,-75.4951877);
        Marker markerUCaldas=mMap.addMarker(new MarkerOptions()
                .position(uCaldas)
                .title("Universidad de Caldas")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        markerUCaldas.showInfoWindow();

        //Mover la camara a una posicion
        mMap.moveCamera(CameraUpdateFactory.newLatLng(unac));

        //Mover la camara con CameraPosition que nos permite manejar el zoom.
        CameraPosition cameraPosition=CameraPosition.builder()
                .target(unac)
                .zoom(12)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    //Metodos de la interfaz  OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMap.setMapType(mMapTypes[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Metodos de la interfaz OnMapClickListener
    @Override
    public void onMapClick(LatLng latLng) {
        String format = String.format(Locale.getDefault(),
                "Lat/Lng = (%f,%f)", latLng.latitude, latLng.longitude);
        Toast.makeText(this, format, Toast.LENGTH_LONG).show();
    }


    public void updateLocation(Location location) {
        if(location != null){
            int lat = (int) (location.getLatitude());
            int lng = (int) (location.getLongitude());
            latituteField.setText(String.valueOf(lat));
            longitudeField.setText(String.valueOf(lng));
        } else {
            Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            updateLocation(mLastLocation);
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng position = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            markerOptions.position(position);
            markerOptions.title("Actual Posicion");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        updateLocation(mLastLocation);
    }
}
