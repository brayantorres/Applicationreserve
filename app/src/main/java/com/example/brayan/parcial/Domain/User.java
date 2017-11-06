package com.example.brayan.parcial.Domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.brayan.parcial.Repository.DBContract;

import java.io.Serializable;

/**
 * Created by brayantorres on 10/9/2017.
 */

public class User  implements Serializable {

    private String userName;
    private String name;
    private String lastName;
    private String password;
    private String rol;
    private int age;


    public User(String userName, String name, String lastName, String password, String rol, int age) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.rol = rol;
        this.age = age;
    }

    public User(Cursor cursor) {
        userName = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.USER_NAME));
        name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.NAME));
        lastName = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.LAST_NAME));
        password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.PASSWORD));
        rol = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.ROL));
        age = cursor.getInt(cursor.getColumnIndex(DBContract.UserEntry.AGE));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.USER_NAME, getUserName());
        values.put(DBContract.UserEntry.NAME, getName());
        values.put(DBContract.UserEntry.LAST_NAME, getLastName());
        values.put(DBContract.UserEntry.PASSWORD, getPassword());
        values.put(DBContract.UserEntry.ROL, getRol());
        values.put(DBContract.UserEntry.AGE, getAge());
        return values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
