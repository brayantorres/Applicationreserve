package com.example.saaibi.parcial.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.saaibi.parcial.Domain.Event;
import com.example.saaibi.parcial.Domain.User;
import com.example.saaibi.parcial.Repository.DBContract;
import com.example.saaibi.parcial.Repository.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAAIBI on 13/9/2017.
 */

public class EventController extends DBHelper {
    List<Event> listEvents = new ArrayList<>();
    public EventController(Context context) {super(context);}

    public boolean create(Event event) {
        System.out.println("Event: " + event.toContentValues());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean isCreate = db.insert(DBContract.EventEntry.TABLE_NAME, null, event.toContentValues()) > 0;
        db.close();
        return isCreate ;
    }

    public Cursor getAllEvents() {
        return getReadableDatabase()
                .query(
                        DBContract.EventEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    public boolean dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,2);
        return true;
    }

    public List<Event> fillEvents (){

        Cursor cursor =  getAllEvents();
        cursor.moveToFirst();
        Event userAux;
        while (!cursor.isAfterLast()) {
            userAux = new Event(cursor);
            listEvents.add(userAux);
            cursor.moveToNext();
        }
        System.out.println(" Termino de traer los datos");
        for (Event event : listEvents) {
            System.out.println("event: " + event.get_id());
        }
        return listEvents;
    }

    public Event showEvent(int ceventID){
        Event contato = new Event();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM contato WHERE id = " + ceventID;
        Cursor cursor = db.rawQuery(sql , null);
        if(cursor.moveToFirst()) {
            contato = new Event(cursor);
        }
        return contato;
    }

    public List<Event> filtrarForDate(String fecha) {
        Cursor cursor = getAllEvents();
        cursor.moveToFirst();
        Event eventAux;
        while (!cursor.isAfterLast()) {
            eventAux = new Event(cursor);
            String date = eventAux.getDateEvent().toString();
            if (fecha.equals(date)) {
                listEvents.add(eventAux);
            }
            cursor.moveToNext();
        }
        return listEvents;
    }

    public List<Event> filtrarporTipo(String tipo) {
        List<Event> listEventos = new ArrayList<>();
        Cursor cursor = getAllEvents();
        cursor.moveToFirst();
        Event eventAux;
        while (!cursor.isAfterLast()) {
            eventAux = new Event(cursor);
            String type = eventAux.getTipeEvent().toString();
            if (tipo.equals(type)) {
                listEventos.add(eventAux);
            }
            cursor.moveToNext();
        }
        return listEventos;
    }

    public boolean update(User user){
        ContentValues values = new ContentValues();
        String where = "username = ?";
        String[] whereArgs = {user.getUserName()};
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isUpdate = db.update(DBContract.EventEntry.TABLE_NAME, values,
                where, whereArgs) > 0;

        db.close();
        return isUpdate;

    }

    public boolean delete(int contatoID){

        boolean isDeleted = false;

        SQLiteDatabase db = this.getWritableDatabase();
        isDeleted = db.delete(DBContract.EventEntry.TABLE_NAME, "id ='" + contatoID + "'", null) > 0;
        db.close();

        return isDeleted;

    }
}
