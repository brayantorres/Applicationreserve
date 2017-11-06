package com.example.brayan.parcial.Repository;

import android.provider.BaseColumns;

/**
 * Created by brayantorres on 5/09/16.
 * Esquema de la base de datos de usuarios
 */
public class DBContract {

    public DBContract() {

    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";

        public static final String USER_NAME = "username";
        public static final String NAME = "name";
        public static final String LAST_NAME = "lastname";
        public static final String PASSWORD = "password";
        public static final String ROL = "rol";
        public static final String AGE = "age";

        public static final String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + USER_NAME + " PRIMARY KEY NOT NULL,"
                + NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + PASSWORD + " TEXT,"
                + ROL + " TEXT,"
                + AGE + " INTEGER " + ")";
    }

    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";

        public static final String _ID = "_id";
        public static final String NAME_EVENT = "nameEvent";
        public static final String TIPE_EVENT = "tipeEvent";
        public static final String ATTEN_EVENT = "attenEvent";
        public static final String CITY_EVENT = "cityEvent";
        public static final String DATE_EVENT = "dateEvent";
        public static final String HOUR_EVENT = "hourEvent";
        public static final String REQUIREMENT_EVENT = "requirementEvent";
        public static final String DESCRIPTION_EVENT = "descriptionEvent";
        public static final String LATITUDE = "latitudeEvent";
        public static final String LONGITUDE = "longitudeEvent";

        public static final String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + " _id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_EVENT + " TEXT,"
                + TIPE_EVENT + " TEXT,"
                + ATTEN_EVENT + " TEXT,"
                + CITY_EVENT + " TEXT,"
                + DATE_EVENT + " TEXT,"
                + HOUR_EVENT + " TEXT,"
                + REQUIREMENT_EVENT + " TEXT,"
                + LATITUDE + " TEXT,"
                + LONGITUDE + " TEXT,"
                + DESCRIPTION_EVENT + " TEXT " + ")";
    }
}

