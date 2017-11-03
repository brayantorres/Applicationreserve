package com.example.saaibi.parcial.Views.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saaibi.parcial.Domain.Event;
import com.example.saaibi.parcial.Model.EventDetailViewModel;
import com.example.saaibi.parcial.Model.EventViewModel;
import com.example.saaibi.parcial.R;
import com.example.saaibi.parcial.Views.Adapter.EventAdapter;
import com.example.saaibi.parcial.Views.EventDetailActivity;
import com.example.saaibi.parcial.databinding.FragmentEventBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private FragmentEventBinding fragmentEventBinding;
    private EventViewModel eventViewModel;
    private static final String EXTRA_EVENT = "EXTRA_EVENT";

    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    String latitudeDetail ,longitudeDetail,nameDetail,typeDetail;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        showToolbar(getResources().getString(R.string.tab_maps), false, view);

        initDataBinding();
       // setupListEventView(fragmentEventBinding.listEvent);
        eventViewModel.fetchEventList();
        //eventViewModel.getEventList();


            SupportMapFragment mapFragment  = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        return view;
    }

    private void initDataBinding() {
       // fragmentEventBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_event);
        eventViewModel = new EventViewModel(getContext());
        //fragmentEventBinding.setMainViewModel(eventViewModel);

    }
    private void setupListEventView(RecyclerView listPeople) {
        EventAdapter adapter = new EventAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void infoDetailEvents(Event event) {
        latitudeDetail = event.getLatitude();
        longitudeDetail = event.getLongitude();
        nameDetail = event.getNameEvent();
        typeDetail = event.getTipeEvent();
    }

    public void showToolbar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
  //      ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
     //   ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }
    private void getExtrasFromIntent() {

        List<Event> eventList = eventViewModel.getEventList();
        for (Event event :eventList) {
            // Añadir marker en la posición
            LatLng latLng = new LatLng(Double.parseDouble(event.getLatitude()), Double.parseDouble(event.getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));

            // Cambiar color del marker
            float hue = BitmapDescriptorFactory.HUE_RED;
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(hue));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Controles UI

        // Configuración UI
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Eventos
       // mMap.setOnMapClickListener(this);
        //mMap.setOnMapLongClickListener(this);
        getExtrasFromIntent();

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$"+ latitudeDetail);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$"+ longitudeDetail);
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
                Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }
    public void setMyLocationEnabled() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }
}
