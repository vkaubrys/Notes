package lt.vitalis.notes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import lt.vitalis.notes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "my_notes_main_activity";
    private ActivityMainBinding binding;
    private ArrayAdapter<Note> adapter;
    private ArrayList<Note> notes;

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
        notes = new ArrayList<>();

        notes.addAll(
                UseCaseRepository.generateDummyNotes(25)
        );

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

    private void openNoteDetailsActivity(Note note) {
        Intent intent = new Intent(this, NoteDetails.class);

        intent.putExtra("note", note);

//        startActivity(intent);

        startActivityForReturn.launch(intent);

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

    ActivityResultLauncher<Intent> startActivityForReturn = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Note note = (Note) data.getParcelableExtra("note_object_return");
                        Log.i(TAG, "Reurn note: " + note.toString());
                    }
                }
            }
    );
}


