package com.example.saaibi.parcial.Views.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.saaibi.parcial.Domain.Event;
import com.example.saaibi.parcial.Model.EventViewModel;
import com.example.saaibi.parcial.R;
import com.example.saaibi.parcial.Views.Adapter.EventAdapter;
import com.example.saaibi.parcial.Views.ContainerActivity;
import com.example.saaibi.parcial.databinding.FragmentEventBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements Observer , View.OnClickListener  {

    private FragmentEventBinding fragmentEventBinding;
    private EventViewModel eventViewModel;
    private MaterialSpinner spiTipeEvent;
    private int dia, mes, año, hora, minutos, AmPm;
    private TextInputLayout tilDateEvent, tilHourEvent;
    private EditText campoDateEvent, campoHourEvent;
    private LinearLayout area_dateEvent;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        showToolbar(getResources().getString(R.string.tab_event), false, view);

        initDataBinding();
        campoDateEvent = (EditText) view.findViewById(R.id.campo_dateEvent2);

        // Referencias TILs
        tilDateEvent = (TextInputLayout) view.findViewById(R.id.til_dateEvent);
        tilHourEvent = (TextInputLayout) view.findViewById(R.id.til_hourEvent);


        // Eventos Fecha y Hora
        campoDateEvent.setOnClickListener(this);

        spiTipeEvent = (MaterialSpinner) view.findViewById(R.id.spiTipeEvent);
        List list = new ArrayList();
        list.add("Cultural");
        list.add("Gastronomico");
        list.add("Empresarial");
        list.add("Social");
        list.add("Deportivo");
        list.add("Academico");
        list.add("Religioso");

        ArrayAdapter arrayadapter = new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, list);
        arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiTipeEvent.setAdapter(arrayadapter);
        spiTipeEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if (position != -1){
                    eventViewModel.fetchEventListType(adapterView.getItemAtPosition(position).toString());
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setupListEventView(fragmentEventBinding.listEvent);
        setupObserver(eventViewModel);
        eventViewModel.fetchEventList();

        return view;
    }

    private void initDataBinding() {
        fragmentEventBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_event);
        eventViewModel = new EventViewModel(getContext());
        fragmentEventBinding.setMainViewModel(eventViewModel);

    }

    private void setupListEventView(RecyclerView listPeople) {
        EventAdapter adapter = new EventAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }




    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof EventViewModel) {
            EventAdapter eventAdapter = (EventAdapter) fragmentEventBinding.listEvent.getAdapter();
            EventViewModel eventViewModel = (EventViewModel) observable;
            eventAdapter.setEventList(eventViewModel.getEventList());
        }
    }
    public void showToolbar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }
    @Override
    public void onClick(View v) {
        if (v == campoDateEvent) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            año = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    campoDateEvent.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }
                    , dia, mes, año);
            datePickerDialog.updateDate(año, mes, dia);
            datePickerDialog.show();

            eventViewModel.fetchEventListDate(campoDateEvent.getText().toString());

        }
    }
}
