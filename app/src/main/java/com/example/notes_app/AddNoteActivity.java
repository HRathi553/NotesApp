package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button addNotesButton;
    private int id;

    public static final String EXTRA_ID = "com.example.notes_app.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.notes_app.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.notes_app.EXTRA_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.titleEditText);
        editTextDescription = findViewById(R.id.descriptionEditText);
        addNotesButton = findViewById(R.id.addNoteButton);

        Intent intent = getIntent();
        id = intent.getIntExtra(AddNoteActivity.EXTRA_ID, -1);
        String title = intent.getStringExtra(AddNoteActivity.EXTRA_TITLE);
        String description = intent.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Update Note Screen");
            addNotesButton.setText("Update Note");
            editTextTitle.setText(title);
            editTextDescription.setText(description);
        }

        else {
            setTitle("Add Note Screen");
            addNotesButton.setText("Add Note");
        }

            setOnClickListener();

    }

    private void setOnClickListener() {
        addNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if(title.trim().isEmpty() || description.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please insert a title & description!", Toast.LENGTH_SHORT).show();
                }

                // Using Intent to pass the data to the main activity.
                Intent data = new Intent();
                data.putExtra(EXTRA_TITLE, title);
                data.putExtra(EXTRA_DESCRIPTION, description);

                if(id != -1){
                    data.putExtra(AddNoteActivity.EXTRA_ID, id);
                }

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}