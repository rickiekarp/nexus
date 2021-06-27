package com.example.sebastian.projektapp.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sebastian.projektapp.Constants;
import com.example.sebastian.projektapp.ItemClickSupport;
import com.example.sebastian.projektapp.R;
import com.example.sebastian.projektapp.adapter.AdapterCustomRecyclerView;
import com.example.sebastian.projektapp.communication.vo.VONotes;
import com.example.sebastian.projektapp.db.AppDatabase;
import com.example.sebastian.projektapp.dialog.NotesDialog;
import com.example.sebastian.projektapp.interfaces.IOnAddNotesResult;
import com.example.sebastian.projektapp.interfaces.IOnDialogClick;
import com.example.sebastian.projektapp.interfaces.IOnGetAllNotesResult;
import com.example.sebastian.projektapp.interfaces.IOnRemoveNoteResult;
import com.example.sebastian.projektapp.interfaces.IOnUpdateNotesResult;
import com.example.sebastian.projektapp.model.CardViewItem;
import com.example.sebastian.projektapp.provider.Notes;
import com.example.sebastian.projektapp.tasks.AddNoteTask;
import com.example.sebastian.projektapp.tasks.GetNotesTask;
import com.example.sebastian.projektapp.tasks.RemoveNoteTask;
import com.example.sebastian.projektapp.tasks.UpdateNoteTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 01.11.17.
 */

public class NotesFragment extends Fragment implements IOnGetAllNotesResult, IOnAddNotesResult, IOnDialogClick, IOnUpdateNotesResult, IOnRemoveNoteResult {

    private final String TAG = "singleNote";
    private AppDatabase database;
    private ArrayList<CardViewItem> itemList;
    private AdapterCustomRecyclerView adapter;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    private List<Notes> notesList = new ArrayList<>();
    private IOnDialogClick context;
    private int itemId;

    public NotesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notes, container, false);

        context = this;
        getActivity().setTitle(R.string.action_notes);

        sp = getContext().getSharedPreferences(Constants.Preferences.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        getAllNotes();
        database = AppDatabase.getDatabase(getActivity());
        itemList = new ArrayList<CardViewItem>();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openSingleNoteFragment();

                NotesDialog notesDialog = NotesDialog.newInstance(context, "", "", 0);
                notesDialog.show(getActivity().getSupportFragmentManager(), "addnote");
            }
        });

        adapter = new AdapterCustomRecyclerView(itemList);
        RecyclerView myView =  (RecyclerView)view.findViewById(R.id.recyclerView_notes);
        ItemClickSupport.addTo(myView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                NotesDialog notesDialog = NotesDialog.newInstance(context, adapter.getItemTitle(position), adapter.getItemBody(position), adapter.getNoteId(position));
                notesDialog.show(getActivity().getSupportFragmentManager(), "updatenotes");
                itemId = position;
            }
        });
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

        return view;
    }

    /*public void openSingleNoteFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.hide(this);
        t.add(R.id.main, new SingleNoteFragment(), "add");
        t.addToBackStack(TAG);
        t.commit();

    }*/

    @Override
    public void onGetAllNotesSuccess(List<VONotes> notesList) {
        for (VONotes note : notesList) {
            CardViewItem item = new CardViewItem(note.getTitle(), note.getContent(), note.getNoteId());
            itemList.add(item);
        }

        adapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }

    @Override
    public void onGetAllNotesError() {
        progressDialog.dismiss();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please check your internet connection");
        alertDialog.show();
    }

    private void getAllNotes(){
        progressDialog = ProgressDialog.show(getActivity(), "", "Notes werden geladen", true, false);
        GetNotesTask getNotesTask = new GetNotesTask(sp, this);
        getNotesTask.execute();
    }

    @Override
    public void onAddNotesSuccess(String title, String body) {
        itemList.add(new CardViewItem(title, body));
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onAddNotesError() {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "Error while creating a Note", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPositiveClick(String title, String body, String type) {
        progressDialog = ProgressDialog.show(getActivity(), "", "Notes werden gespeichert", true, false);
        switch (type) {
            case "add":
                AddNoteTask addNoteTask = new AddNoteTask(sp, this, title, body, database);
                addNoteTask.execute();
                break;
            case "update":
                UpdateNoteTask updateNoteTask = new UpdateNoteTask(sp, this, title, body, database);
                updateNoteTask.execute();
                break;
        }
    }

    @Override
    public void onNegativeClick(int id) {
        progressDialog = ProgressDialog.show(getActivity(), "", "Note wird gelöscht", true, false);
        RemoveNoteTask removeNoteTask = new RemoveNoteTask(sp, this, database, id);
        removeNoteTask.execute();
    }

    @Override
    public void onUpdateNotesSuccess(String title, String body) {
        itemList.get(itemId).setTitle(title);
        itemList.get(itemId).setBody(body);

        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onUpdateNotesError() {

    }

    @Override
    public void onRemoveNoteSuccess(int id) {
        itemList.remove(itemId);
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onRemoveNoteError() {

    }
}
