package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;

    public Notes(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Ignore
    public Notes(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
