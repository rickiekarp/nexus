package com.example.sebastian.projektapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.sebastian.projektapp.communication.vo.VOData;
import com.example.sebastian.projektapp.db.daos.NotesDAO;
import com.example.sebastian.projektapp.db.daos.UserDAO;
import com.example.sebastian.projektapp.db.entities.Notes;
import com.example.sebastian.projektapp.db.entities.User;

/**
 * Created by sebastian on 17.11.17.
 */

@Database(entities = {Notes.class, User.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    private VOData appData;

    public abstract NotesDAO notesDAO();

    public abstract UserDAO userDAO();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public VOData getAppData() {
        return appData;
    }

    public void setAppData(VOData data) {
        appData = data;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
