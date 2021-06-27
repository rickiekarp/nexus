package com.example.sebastian.projektapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.sebastian.projektapp.R;
import com.example.sebastian.projektapp.interfaces.IOnDialogClick;

/**
 * Created by sebastian on 06.12.17.
 */

public class NotesDialog extends DialogFragment {

    private IOnDialogClick listener;
    private String title, body;
    private int id;

    public static NotesDialog newInstance(IOnDialogClick listener, String title, String body, int id) {
        NotesDialog fragment = new NotesDialog();
        fragment.listener = listener;
        fragment.title = title;
        fragment.body = body;
        fragment.id = id;

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_add_note, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Erstelle Notiz");
        builder.setView(v);

        EditText editTitle = v.findViewById(R.id.dialog_title);
        EditText editBody = v.findViewById(R.id.dialog_body);
        editTitle.setText(title);
        editBody.setText(body);

        if (getTag().equals("addnote")) {
            builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    title = editTitle.getText().toString();
                    body = editBody.getText().toString();

                        listener.onPositiveClick(title, body, "add");
                        dialogInterface.dismiss();

                }
            });
        } else if (getTag().equals("updatenotes")) {
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    title = editTitle.getText().toString();
                    body = editBody.getText().toString();

                    listener.onPositiveClick(title, body, "update");
                    dialogInterface.dismiss();

                }
            });
            builder.setNegativeButton("Löschen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onNegativeClick(id);
                    dismiss();
                }
            });
        }


        return builder.create();
    }
}
