package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private NotesAdapter adapter;
    private NotesViewModel notesViewModel;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotesAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        /**
         *      For UPDATING NOTES.
         */
        // Setting up OnClickListener
        adapter.setOnClickListener(new NotesAdapter.OnItemClickListener(){
            @Override
            public void onClick(Notes notes) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);

                intent.putExtra(AddNoteActivity.EXTRA_ID, notes.getId());
                intent.putExtra(AddNoteActivity.EXTRA_TITLE, notes.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION, notes.getDescription());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        /**
         *      For LIVEDATA changes to notes.
         */
        // Fetching Notes from ViewModel and attaching observer object to the LiveData.
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notesList) {
                adapter.setNotesList(notesList);
            }
        });

        /**
         *      For ADDING NOTES.
         */
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        /**
         *      For DElETING NOTES.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                notesViewModel.delete(adapter.getNotesAt().remove(position));

                Toast.makeText(getApplicationContext(), "Note has been deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    // Callback method which gives u some data from the Launched activity(AddNoteActivity) back to the Originating activity(MainActivity).
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);

            Notes notes = new Notes(title, description);
            notesViewModel.insert(notes);

            Toast.makeText(this,"Note Saved Successfully", Toast.LENGTH_SHORT).show();
        }

        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID, -1);
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);

            Notes notes = new Notes(id, title, description);
            notesViewModel.update(notes);

            Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this,"Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu,menu);
//
//        MenuItem searchItem = menu.findItem(R.id.searchNotes);
//        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
//        searchView.setQueryHint("Search Notes Here");
//
//        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        };
//
//        return super.onCreateOptionsMenu(menu);
//    }
}