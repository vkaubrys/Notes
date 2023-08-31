package lt.vitalis.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import lt.vitalis.notes.databinding.ActivityMainBinding;
import lt.vitalis.notes.databinding.ActivityNoteDetailsBinding;


public class NoteDetails extends AppCompatActivity {
//    private ActivityNoteDetailsBinding binding;
    private ActivityNoteDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
//        int id = intent.getIntExtra("id", 0);
//        String title = intent.getStringExtra("title");
//        String description = intent.getStringExtra("description");
//
//        binding.textView.setText(
//                id + "\n"+"Title" + title + "\n" + description
//        );
        if (intent.getExtras() != null) {
            Note note = (Note) intent.getParcelableExtra("note");
            displayNoteDetails(note);
        }

    }

    private void displayNoteDetails(Note note) {
        binding.noteIdTextView.setText(note.getId());
        binding.noteNameEditText.setText(note.getTitle());
        binding.noteContentEditText.setText(note.getDescription());
        binding.noteCreationDateTextView.setText(
                note.getCreationDate() != null ? note.getUpdateDate().format(formatter) : "no data");
    }
}