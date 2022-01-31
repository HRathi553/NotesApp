package com.example.notes_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private Context context;
    private List<Notes> notesList;
    public OnItemClickListener itemClickListener;

    public NotesAdapter(Context context) {
        this.context = context;
    }

    /**
     *  For setting LiveData from UI to RoomDatabase
     */
    public void setNotesList(List<Notes> notesList){
        this.notesList = notesList;
        //notifyItemInserted(getItemCount() + 1);
        notifyDataSetChanged();
    }

    public List<Notes> getNotesAt(){
        return notesList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notes_cardlayout, parent, false);

        NotesViewHolder notesViewHolder = new NotesViewHolder(view);

        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes currentNote = notesList.get(position);
        holder.title.setText(currentNote.getTitle());
        holder.description.setText(currentNote.getDescription());
    }

    @Override
    public int getItemCount() {
        int i =0;

        try {
            i = notesList.size();
        }
        catch (NullPointerException error){
            Log.i("SEE AGAin", error.toString());
        }
        return i;
    }

    public void setOnClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notesTitle);
            description = itemView.findViewById(R.id.notesDescription);
            //itemView.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && itemClickListener != null)
                        itemClickListener.onClick(notesList.get(position));
                }
            });
        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//
//            if(position != RecyclerView.NO_POSITION && this.itemClickListener != null)
//            this.itemClickListener.onClick(notesList.get(position));
//        }
    }

    // Interface for onClick event.
    public interface OnItemClickListener {
        public void onClick(Notes notes);
    }
}


