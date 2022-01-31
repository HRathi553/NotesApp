package com.example.notes_app;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {

    private NotesDao notesDao;
    private LiveData<List<Notes>> allNotes;

    NotesRepository(Application application){
        // Creating an instance of database.
        NotesDataBase notesDataBase = NotesDataBase.getDataBase(application);

        // Creating a DAO for the database instance.
        notesDao = notesDataBase.notesDao();

        // Using DAO method to get all the notes.
        allNotes = notesDao.getAllNotes();
    }

    LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }

    /**
     *  To Insert data into database.
     */
    public void insert(Notes notes){
        new insertAsyncTask(notesDao).execute(notes);
    }

    // Room does not allow operations on Main Thread. So we have to use AsyncTask here.
    // Or we could use RxJava, Kotlin Flow or Coroutines.
    public static class insertAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NotesDao myAsyncInsertDao;

        public insertAsyncTask(NotesDao notesDao) {
            myAsyncInsertDao = notesDao;
        }


        @Override
        protected Void doInBackground(Notes... notes) {
            myAsyncInsertDao.insert(notes[0]);
            return null;
        }
    }

    /**
     *  To Update data into database.
     */
    public void update(Notes notes){
        new updateAsyncTask(notesDao).execute(notes);
    }

    public static class updateAsyncTask extends AsyncTask<Notes,Void,Void>{
        private  NotesDao myAsyncUpdateDao;

        public updateAsyncTask(NotesDao notesDao) {
            myAsyncUpdateDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            myAsyncUpdateDao.update(notes[0]);
            return null;
        }
    }

    /**
     *  To Delete data from database
     */

    public void delete(Notes notes){
        new deleteAsyncTask(notesDao).execute(notes);
    }

    public static class deleteAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NotesDao myAsyncDeleteDao;

        public deleteAsyncTask(NotesDao notesDao) {
            myAsyncDeleteDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            myAsyncDeleteDao.delete(notes[0]);
            return null;
        }
    }
}
