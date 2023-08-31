package lt.vitalis.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import lt.vitalis.notes.databinding.ActivityNoteDetailsBinding;


public class NoteDetails extends AppCompatActivity {

    private ActivityNoteDetailsBinding binding;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int noteId;


        if (intent.getExtras() != null) {
            noteId = intent.getIntExtra("note", 0);
            displayNoteDetails(note);
        }


        displayNoteDetails(noteId);
        setUpSaveButton();

    }

    private void setUpSaveButton() {
        binding.saveButton.setOnClickListener(
                addValuesToNote();
                v -> {
                    if (note.getId() == 0) {

                        saveNewNote();
                    } else {
                        updateNote();
                    }
                }
        );

    }

    private View.OnClickListener addValuesToNote() {
        note.setTitle();
        bindingNameEditText.toString()
    }

    private void updateNote() {
        UseCaseRepository.notes.stream()
                .filter(onlNote -> onlNote.getId()) == note.getId())
            .findFirst().get()

    }

    private void saveNewNote() {
        int newId = UseCaseRepository.notes.stream().max(Comparator.comparing(Note::getId))
                .get()
                .getId();

        UseCaseRepository.notes.add(
                new Note(
                        maxId + 1,
                        note.getTitle(),
                        note.getDescription()
                )
        )

    }

    private void displayNoteDetails(int noteId) {
        if (noteId == 0) {
            note = new Note();
        }
        binding.noteIdTextView.setText(String.valueOf(noteId));
    } else

    {
        UseCaseRepository.notes.stream()
                .filter(note -> note.getId() == noteId)
                .findFirst()
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
                note.getUpdateDate() != null ? note.getUpdateDate().format(formatter) : "no data"
        );
    }
}