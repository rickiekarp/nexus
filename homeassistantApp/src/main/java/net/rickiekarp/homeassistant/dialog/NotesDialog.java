package net.rickiekarp.homeassistant.dialog;

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
import android.widget.Toast;

import net.rickiekarp.homeassistant.R;
import net.rickiekarp.homeassistant.communication.vo.VONote;
import net.rickiekarp.homeassistant.interfaces.IOnDialogClick;

/**
 * Created by sebastian on 06.12.17.
 */

public class NotesDialog extends DialogFragment {

    private IOnDialogClick listener;
    private VONote note;

    public static NotesDialog newInstance(IOnDialogClick listener, VONote noteItem) {
        NotesDialog fragment = new NotesDialog();
        fragment.listener = listener;
        fragment.note = noteItem;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_single_note, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        EditText editTitle = v.findViewById(R.id.single_note_title);
        editTitle.setHint("New title");

        switch (getTag()) {
            case "viewnote":
                builder.setTitle("Item details");
                break;

            case "addnote":
                builder.setTitle("Create item");
                builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String noteTitle = editTitle.getText().toString();
                        if (noteTitle.isEmpty()) {
                            Toast.makeText(getActivity(), "Title can not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        listener.onPositiveClick(noteTitle, "add");
                        dialogInterface.dismiss();
                    }
                });
                break;
            case "updatenotes":
                builder.setTitle("Update item");
                editTitle.setText(note.getTitle());

                EditText price = v.findViewById(R.id.note_detail_price);
                price.setText(String.valueOf(note.getPrice()));

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onPositiveClick(editTitle.getText().toString(),"update");
                        dialogInterface.dismiss();

                    }
                });
                builder.setNegativeButton("Löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onNegativeClick(note.getId());
                        dismiss();
                    }
                });
                break;
            default:
                throw new RuntimeException("invalid parameter");
        }

        return builder.create();
    }
}
