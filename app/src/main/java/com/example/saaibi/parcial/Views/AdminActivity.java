package com.example.saaibi.parcial.Views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.saaibi.parcial.Controller.EventController;
import com.example.saaibi.parcial.Controller.UserController;
import com.example.saaibi.parcial.Domain.Event;
import com.example.saaibi.parcial.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialSpinner spiTipeEvent;
    private int dia, mes, año, hora, minutos, AmPm;
    private EditText campo_nameEvent, campo_attenEvent, campo_cityEvent, campo_dateEvent, campo_hourEvent, campo_requirementEvent, campo_descriptionEvent;
    private TextInputLayout tilDateEvent, tilHourEvent;
    private EditText campoDateEvent, campoHourEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Id.R Campos Formulario
        campo_nameEvent = (EditText) findViewById(R.id.campo_nameEvent);
        campo_attenEvent = (EditText) findViewById(R.id.campo_attenEvent);
        campo_cityEvent = (EditText) findViewById(R.id.campo_cityEvent);
        campo_dateEvent = (EditText) findViewById(R.id.campo_dateEvent);
        campo_hourEvent = (EditText) findViewById(R.id.campo_hourEvent);
        spiTipeEvent = (MaterialSpinner) findViewById(R.id.spiTipeEvent);
        campo_requirementEvent = (EditText) findViewById(R.id.campo_requirementEvent);
        campo_descriptionEvent = (EditText) findViewById(R.id.campo_descriptionEvent);
        campoDateEvent = (EditText) findViewById(R.id.campo_dateEvent);
        campoHourEvent = (EditText) findViewById(R.id.campo_hourEvent);

        // Referencias TILs
        tilDateEvent = (TextInputLayout) findViewById(R.id.til_dateEvent);
        tilHourEvent = (TextInputLayout) findViewById(R.id.til_hourEvent);


        // Eventos Fecha y Hora
        campoDateEvent.setOnClickListener(this);
        campoHourEvent.setOnClickListener(this);
        tilDateEvent.setOnClickListener(this);
        tilHourEvent.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isDeletedEvent = new EventController(getApplicationContext()).dropTable();
                boolean isDeletedUser = new UserController(getApplicationContext()).dropTable();
                if (isDeletedUser)
                    Toast.makeText(getApplicationContext(), "Tabla Users eliminada ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Tabla Users no eliminada", Toast.LENGTH_SHORT).show();
                if (isDeletedEvent)
                    Toast.makeText(getApplicationContext(), "Tabla Events eliminada ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Tabla Events no eliminada", Toast.LENGTH_SHORT).show();


            }
        });
        //Manejador del Spinner
        spiTipeEvent = (MaterialSpinner) findViewById(R.id.spiTipeEvent);
        List list = new ArrayList();
        list.add("Cultural");
        list.add("Gastronomico");
        list.add("Empresarial");
        list.add("Social");
        list.add("Deportivo");
        list.add("Academico");
        list.add("Religioso");

        ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiTipeEvent.setAdapter(arrayadapter);
    }

    public void GuardarDatosEvento(View v) {

        String nombreEvento = campo_nameEvent.getText().toString();
        String encargado = campo_attenEvent.getText().toString();
        String lugar = campo_cityEvent.getText().toString();
        String tipEvento = spiTipeEvent.getSelectedItem().toString();
        String fecha = campo_dateEvent.getText().toString();
        String hora = campo_hourEvent.getText().toString();
        String requisitos = campo_requirementEvent.getText().toString();
        String descripcion = campo_descriptionEvent.getText().toString();

        Event evento = new Event(nombreEvento, tipEvento, encargado, lugar, fecha, hora, requisitos, descripcion);
        boolean isCreate = new EventController(getApplicationContext()).create(evento);
        if (isCreate)
            Toast.makeText(getApplicationContext(), "Evento creado exitosamente", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "No fue posible crear el Evento", Toast.LENGTH_SHORT).show();
    }

    //Metodos de fechas y horas
    @Override
    public void onClick(View v) {
        if (v == campoDateEvent || v == tilDateEvent) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            año = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    campoDateEvent.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }
                    , dia, mes, año);
            datePickerDialog.updateDate(año, mes, dia);
            datePickerDialog.show();
        }
        if (v == campoHourEvent || v == tilHourEvent) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);
            AmPm = c.get(Calendar.AM_PM);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hora = hourOfDay;
                    minutos = minute;

                    String AM_PM;
                    if (hourOfDay < 12) {
                        AM_PM = "AM";

                    } else {
                        AM_PM = "PM";
                        hora = hora - 12;
                    }
                    campoHourEvent.setText(hora + ":" + minutos + " " + AM_PM);

                }

            }, hora, minutos, false);
            timePickerDialog.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            intent.setAction(LoginActivity.class.getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            getApplicationContext().startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
