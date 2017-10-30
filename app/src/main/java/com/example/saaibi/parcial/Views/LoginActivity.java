package com.example.saaibi.parcial.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.saaibi.parcial.Controller.ApplicationController;
import com.example.saaibi.parcial.Controller.UserController;
import com.example.saaibi.parcial.Domain.User;
import com.example.saaibi.parcial.R;
import com.example.saaibi.parcial.Views.Maps.MapsActivity;
import com.example.saaibi.parcial.Views.Others.CreateUsersOnClickListener;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilNombre;
    private TextInputLayout tilPassword;
    ApplicationController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appController = (ApplicationController) getApplication();
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilPassword = (TextInputLayout) findViewById(R.id.til_password);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setOnClickListener(new CreateUsersOnClickListener());
            }
        });

        Button botonAceptar = (Button) findViewById(R.id.btnLogin);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = tilNombre.getEditText().getText().toString();
                String password = tilPassword.getEditText().getText().toString();
                appController.authenticate(nombre, password);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_action_settings) {
        /*   User person3 = new User("admin", "Admin", "Admin", "1234", "admin", 30);
            boolean usersCreate = new UserController(this).create(person3);
            if (usersCreate)
                Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No fue posible crear Usuario", Toast.LENGTH_SHORT).show();
            return usersCreate;
*/
            Intent intent = new Intent();

            intent.setClass(getApplicationContext(), MapsActivity.class);
            intent.setAction(MapsActivity.class.getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            getApplicationContext().startActivity(intent);


        } else {
            Intent intent = new Intent();

            intent.setClass(getApplicationContext(), PushNotificationsActivity.class);
            intent.setAction(PushNotificationsActivity.class.getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            getApplicationContext().startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
