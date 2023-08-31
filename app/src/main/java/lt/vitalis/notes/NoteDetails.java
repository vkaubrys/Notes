package lt.vitalis.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.time.format.DateTimeFormatter;

import lt.vitalis.notes.databinding.ActivityMainBinding;
import lt.vitalis.notes.databinding.ActivityNoteDetailsBinding;


public class NoteDetails extends AppCompatActivity {
;
    private ActivityNoteDetailsBinding binding;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
    private Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            note = (Note) intent.getParcelableExtra("note");
            displayNoteDetails(note);
        }
        setUpSaveButtonClick();

    }

    private void setUpSaveButtonClick() {
        binding.saveButton.setOnClickListener(
                v -> {
                    note.setTitle(binding.noteNameEditText.getText().toString());
                    note.setDescription(binding.noteUpdateDateTextView.getText().toString());
                    saveNote();
                    Intent finishIntent = new Intent();
                    finishIntent.putExtra("note_object_return", note);
                    setResult(RESULT_OK, finishIntent);
                    finish();
                }
        );
    }

    private void saveNote() {

    }


    private void displayNoteDetails(Note note) {
        binding.noteIdTextView.setText(note.getId());
        binding.noteNameEditText.setText(note.getTitle());
        binding.noteContentEditText.setText(note.getDescription());

        binding.noteCreationDateTextView.setText(
                note.getCreationDate() != null ? note.getUpdateDate().format(formatter) : "no data"
        );
        binding.noteUpdateDateTextView.setText(
                note.getUpdateDate() != null ? note.getUpdateDate().format(formatter) :"no data"
        );
    }
}