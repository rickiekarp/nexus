package net.rickiekarp.homeassistant.fragments;


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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import net.rickiekarp.homeassistant.Constants;
import net.rickiekarp.homeassistant.ItemClickSupport;
import net.rickiekarp.homeassistant.R;
import net.rickiekarp.homeassistant.adapter.AdapterCustomRecyclerView;
import net.rickiekarp.homeassistant.adapter.NoteHistoryListViewAdapter;
import net.rickiekarp.homeassistant.communication.vo.VONote;
import net.rickiekarp.homeassistant.db.AppDatabase;
import net.rickiekarp.homeassistant.dialog.NotesDialog;
import net.rickiekarp.homeassistant.interfaces.IOnAddNotesResult;
import net.rickiekarp.homeassistant.interfaces.IOnDialogClick;
import net.rickiekarp.homeassistant.interfaces.IOnGetAllNotesResult;
import net.rickiekarp.homeassistant.interfaces.IOnRemoveNoteResult;
import net.rickiekarp.homeassistant.interfaces.IOnUpdateNotesResult;
import net.rickiekarp.homeassistant.preferences.Token;
import net.rickiekarp.homeassistant.tasks.AddNoteTask;
import net.rickiekarp.homeassistant.tasks.GetNotesHistoryTask;
import net.rickiekarp.homeassistant.tasks.GetNotesTask;
import net.rickiekarp.homeassistant.tasks.RemoveNoteTask;
import net.rickiekarp.homeassistant.tasks.UpdateNoteTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 01.11.17.
 */

public class NotesHistoryFragment extends Fragment implements IOnGetAllNotesResult, IOnAddNotesResult, IOnDialogClick, IOnUpdateNotesResult, IOnRemoveNoteResult {

    private final String TAG = "singleNote";
    private AppDatabase database;
    private ArrayList<VONote> itemList;
    private NoteHistoryListViewAdapter adapter;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    private IOnDialogClick context;
    private int itemId;

    public NotesHistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notes_history, container, false);
        context = this;
        getActivity().setTitle(R.string.action_notes_history);

        sp = getContext().getSharedPreferences(Constants.Preferences.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        getAllNotes();
        database = AppDatabase.getDatabase(getActivity());
        itemList = new ArrayList<>();

        adapter = new NoteHistoryListViewAdapter(getActivity(), itemList);

        ListView myView = (ListView) view.findViewById(R.id.listview);
//        populateList();
        myView.setAdapter(adapter);

//        ItemClickSupport.addTo(myView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//
////                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                fragmentManager.beginTransaction().replace(R.id.main, new SingleNoteFragment()).addToBackStack(TAG).commit();
//
//                itemId = position;
//                NotesDialog notesDialog = NotesDialog.newInstance(context, adapter.getNoteAtIndex(position));
//                notesDialog.show(getActivity().getSupportFragmentManager(), "viewnote");
//            }
//        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

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
    public void onGetAllNotesSuccess(List<VONote> notesList) {
        itemList.addAll(notesList);
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
        progressDialog = ProgressDialog.show(getActivity(), "", "Loading history", true, false);
        GetNotesHistoryTask getNotesTask = new GetNotesHistoryTask(sp, this);
        getNotesTask.execute();
    }

    @Override
    public void onAddNotesSuccess(String title) {
        itemList.add(new VONote(title));
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
    public void onPositiveClick(String title, String type) {
        progressDialog = ProgressDialog.show(getActivity(), "", "Notes werden gespeichert", true, false);
        switch (type) {
            case "add":
                AddNoteTask addNoteTask = new AddNoteTask(sp, this, title, database);
                addNoteTask.execute();
                break;
            case "update":
                UpdateNoteTask updateNoteTask = new UpdateNoteTask(sp.getString(Token.KEY, ""), this, title, database);
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
    public void onUpdateNotesSuccess(String title) {
        itemList.get(itemId).setTitle(title);

        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onUpdateNotesError() {
        Toast.makeText(getContext(), "There was an error when updating the note", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRemoveNoteSuccess(int id) {
        itemList.remove(id);
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onRemoveNoteError() {
        Toast.makeText(getContext(), "There was an error when removing the note", Toast.LENGTH_LONG).show();
    }
}
