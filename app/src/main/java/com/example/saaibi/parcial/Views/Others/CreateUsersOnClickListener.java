package com.example.saaibi.parcial.Views.Others;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saaibi.parcial.Controller.UserController;
import com.example.saaibi.parcial.Domain.User;
import com.example.saaibi.parcial.R;


/**
 * Created by saaibi on 22/04/17.
 */

public class CreateUsersOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.user_form, null, false);

        final EditText userName = (EditText) formElementsView.findViewById(R.id.campo_userName);
        final EditText name = (EditText) formElementsView.findViewById(R.id.campo_name);
        final EditText lastName = (EditText) formElementsView.findViewById(R.id.campo_lastName);
        final EditText password = (EditText) formElementsView.findViewById(R.id.campo_password);
        final EditText age = (EditText) formElementsView.findViewById(R.id.campo_age);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Sing up")
                .setPositiveButton("siguiente",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                User person3 = new User(userName.getText().toString(), name.getText().toString(), lastName.getText().toString(), password.getText().toString(), "user", Integer.parseInt(age.getText().toString()));
                                boolean usersCreate = new UserController(context).create(person3);
                                if (usersCreate)
                                    Toast.makeText(context, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context, "No fue posible crear Usuario", Toast.LENGTH_SHORT).show();

                                dialog.cancel();
                            }

                        }).show();

    }
}
