package com.example.notes_app;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class}, version = 1)
public abstract class NotesDataBase extends RoomDatabase {

    public static NotesDataBase instance;
    public abstract NotesDao notesDao();

    static NotesDataBase getDataBase(final Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                           NotesDataBase.class, "notes_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
