package lt.vitalis.notes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lt.vitalis.notes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "my_notes_main_activity";
    private ActivityMainBinding binding;
    private ArrayAdapter<Note> adapter;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpListView();
        setUpListViewItemClick();
        setUpListViewItemLongClick();
        setUpFloatingActionButtonClick();
    }

    private void setUpListView() {
        if (UseCaseRepository.notes.isEmpty()) {
            UseCaseRepository.generateDummyNotes(25);
        }

        notes = UseCaseRepository.notes;


        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notes
        );
        binding.notesListView.setAdapter(adapter);
    }

    private void setUpListViewItemClick() {
        binding.notesListView.setOnItemClickListener(
                (adapterView, view, position, l) -> {
//                    Log.i(TAG, "OnListeItemClicked: " + adapterView.getItemAtPosition(position));
//                    Log.i(TAG, "OnListeItemClicked: " + position);
                    Note note = (Note) adapterView.getItemAtPosition(position);
                    openNoteDetailsActivity(note);
                }
        );
    }

    private void setUpListViewItemLongClick() {
        binding.notesListView.setOnItemLongClickListener(
                (adapterView, view, position, l) -> {
                    Log.i(TAG, "OnListItem_LONG_Clicked: " + adapterView.getItemAtPosition(position));
                    Note note = (Note) adapterView.getItemAtPosition(position);
                    showItemRemoveAlertDialog(note);
                    return true;
                }
        );
    }

    private void setUpFloatingActionButtonClick() {
        binding.floatingActionButton.setOnClickListener(
                view -> {
                    openNoteDetailsActivity(new Note());
                }
        );
    }

    private void updateNoteToList(Note note) {
        Note newNote = notes.stream()
                .filter(oldNote -> oldNote.getId() == note.getId())
                .findFirst().get();
        newNote.setTitle(note.getTitle());
        newNote.setDescription(note.getDescription());
        adapter.notifyDataSetChanged();


    }

    private void openNoteDetailsActivity(Note note) {
        Intent intent = new Intent(this, NoteDetails.class);

        intent.putExtra("noteId", note.getId());

//        startActivityForReturn.launch(intent);

    }


    private void showItemRemoveAlertDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("Do you realy want to remove this item?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    removeNoteFromList(note);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showSnackbar(String message) {

        Snackbar
                .make(
                        binding.notesListView,
                        message,
                        Snackbar.LENGTH_LONG
                )
                .show();
    }

    private void removeNoteFromList(Note note) {
        notes.remove(note);
        adapter.notifyDataSetChanged();
        showSnackbar("Note with id: " + note.getId() + " was removed");

    }

    private void addNoteToList(Note note) {
        notes.add(note);
        adapter.notifyDataSetChanged();
        showSnackbar("Note with id: " + note.getId() + " was updated");

        ActivityResultLauncher<Intent> startActivityForReturn = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Note note = (Note) data.getParcelableExtra("note_object_return");
                            Log.i(TAG, "Reurn note: " + note.toString());

                            if (note.getId() == 0) {
                                notes
                                        .stream()
                                        .max(Comparator.comparing(Note::getId))
                                        .get().getId();
                                Note newNote = new Note(
                                        maxId + 1,
                                        note.getTitle(),
                                        note.getDescription()

                                );
                                addNoteToList(newNote);

                            } else {
                                updateNoteToList(note);
                            }
                        }
                    }
                }
        );
    }
}


