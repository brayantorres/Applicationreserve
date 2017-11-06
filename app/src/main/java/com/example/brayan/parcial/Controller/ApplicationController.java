package com.example.brayan.parcial.Controller;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.brayan.parcial.Domain.User;
import com.example.brayan.parcial.Repository.DBHelper;
import com.example.brayan.parcial.Views.ContainerActivity;
import com.example.brayan.parcial.Views.AdminActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brayantorres on 10/9/2017.
 */

public class ApplicationController extends Application {
    private List<User> users;
    private DBHelper DBHelper;
    private String rol;
    public ApplicationController() {
        users = new ArrayList<User>();
    }

    public void authenticate(String username, String password) {
        DBHelper = new DBHelper(this.getApplicationContext());
        new AuthenticTask().execute(username, password);
    }


    private boolean isValidUser(String username, String password) {
        Boolean resp = false;
        for (User person : users) {
            System.out.println("users: " + person.getUserName());
            if (username.equals(person.getUserName())) {
                if (password.equals(person.getPassword())) {
                    rol = person.getRol();
                    resp = true;
                }
            }
        }
        return resp;
    }

    private class AuthenticTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            List<User> listUsers = new ArrayList<>();
            Cursor cursor =  new UserController(getApplicationContext()).getAllUsers();
            cursor.moveToFirst();
            User userAux;
            while (!cursor.isAfterLast()) {
                userAux = new User(cursor);
                listUsers.add(userAux);
                cursor.moveToNext();
            }
            System.out.println("Termino de traer los datos");
            users = listUsers;

            return isValidUser(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean isAuthenticate) {
            super.onPostExecute(isAuthenticate);
            if (isAuthenticate) {
                Intent intent = new Intent();
                if (rol.equalsIgnoreCase("admin")) {
                    intent.setClass(getApplicationContext(), AdminActivity.class);
                    intent.setAction(AdminActivity.class.getName());
                }else{
                    intent.setClass(getApplicationContext(), ContainerActivity.class);
                    intent.setAction(ContainerActivity.class.getName());
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                getApplicationContext().startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "El usuario o contraseña no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
