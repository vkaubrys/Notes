package lt.vitalis.notes;

import static lt.vitalis.notes.MainActivity.INTENT_MAIN_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import lt.vitalis.notes.databinding.ActivityNoteDetailsBinding;


public class NoteDetails extends BaseActivity {


    private ActivityNoteDetailsBinding binding;
    private Note note;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
    private String demoResult;
    private final String SAVE_INSTANCE_KEY = "note_details_save_instance_key";

    public NoteDetails(String message, String tag) {
        super("NoteDetails", "tst_lfc_main_activity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int noteId = 0;

        if (intent.getExtras() != null) {
            noteId = intent.getIntExtra(INTENT_MAIN_KEY, 0);
        }

        displayNoteDetails(noteId);
        setUpSaveButton();
//        if(savedInstanceState != null){
//
//        }



        binding.noteNameEditText.setOnFocusChangeListener(
                (view, b) -> {
                    demoResult = binding.noteNameEditText.getText().toString();
                    print("demoResult: " + demoResult);
                }
        );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        demoResult = savedInstanceState.getString(SAVE_INSTANCE_KEY);
        print("onRestore demoResult:" + demoResult);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_INSTANCE_KEY, demoResult);
    }

    private void displayNoteDetails(int noteId) {
        if (noteId == 0) {
            note = new Note();
        } else {
            getNoteFromRepository(noteId);
        }
        binding.noteIdTextView.setText(String.valueOf(note.getId()));
        binding.noteNameEditText.setText(note.getTitle());
        binding.noteContentEditText.setText(note.getDescription());
        binding.noteCreationDateTextView.setText(note.getCreationDate() != null ? note.getCreationDate().format(formatter) : "no data");
        binding.noteUpdateDateTextView.setText(note.getUpdateDate() != null ? note.getUpdateDate().format(formatter) : "no data");
    }

    private void getNoteFromRepository(int noteId) {
        note = UseCaseRepository.notes.stream().filter(note -> note.getId() == noteId).findFirst().get();

    }

    private void setUpSaveButton() {
        binding.saveButton.setOnClickListener(v -> {
                    addValuesToNote();
                    if (note.getId() == 0) {
                        saveNewNote();
                    } else {
                        updateNote();
                    }
                    finish();
                }
        );
    }


    private void addValuesToNote() {
        note.setTitle(
                binding.noteNameEditText.getText().toString()
        );
        note.setDescription(
                binding.noteContentEditText.getText().toString()
        );
    }

    private void saveNewNote() {
        int maxId = UseCaseRepository.notes.stream()
                .max(Comparator.comparing(Note::getId))
                .get()
                .getId();

        UseCaseRepository.notes.add(
                new Note(
                        maxId + 1,
                        note.getTitle(),
                        note.getDescription()
                )
        );
    }

    private void updateNote() {
        Note newNote = UseCaseRepository.notes.stream()
                .filter(onNote -> onNote.getId() == note.getId())
                .findFirst()
                .get();

        newNote.setTitle(note.getTitle());
        newNote.setDescription(note.getDescription());


    }
}