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
import lt.vitalis.notes.repository.NoteDao;


public class NoteDetails extends BaseActivity {


    private ActivityNoteDetailsBinding binding;
    private Note note;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
    private String demoResult;
    private final String SAVE_INSTANCE_KEY = "note_details_save_instance_key";
    private NoteDao noteDao;


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
            note = noteDao.getById(noteId);
            if (note == null) note = new Note();
        } else {
            note = new Note();

        }
        print("onCreate demoResult:" + demoResult);


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

    private void displayNoteDetails() {
        binding.noteIdTextView.setText(String.valueOf(note.getId()));
        binding.noteNameEditText.setText(note.getTitle());
        binding.noteContentEditText.setText(note.getDescription());
        binding.noteCreationDateTextView.setText(note.getCreationDate() != null ? note.getCreationDate().format(formatter) : "no data");
        binding.noteUpdateDateTextView.setText(note.getUpdateDate() != null ? note.getUpdateDate().format(formatter) : "no data");
    }


    private void setUpSaveButton() {
        binding.saveButton.setOnClickListener(v -> {
                        saveNote();
                        finish();
        });
    }

    private void addValuesToNote() {
        note.setTitle(
                binding.noteNameEditText.getText().toString()
        );
        note.setDescription(
                binding.noteContentEditText.getText().toString()
        );
    }
}