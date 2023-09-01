package lt.vitalis.notes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import lt.vitalis.notes.databinding.ActivityMainBinding;
import lt.vitalis.notes.repository.MainDataBase;
import lt.vitalis.notes.repository.NoteDao;

public class MainActivity extends BaseActivity {

    private static final String TAG = "my_notes_main_activity";
    public static final String INTENT_MAIN_KEY = "main_activity_intent_key";
    private ActivityMainBinding binding;
    private ArrayAdapter<Note> adapter;
    private List<Note> notes;
    private NoteDao noteDao;

    public MainActivity() {
        super("MainActivity", "tst_lfc_main_activity");

    }


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

    @Override
    protected void onStart() {
        super.onStart();
        setUpListView();
    }


    private void setUpListView() {
        noteDao = MainDataBase
                .getInstance(getApplicationContext())
                .noteDao();





        if (noteDao.getAll().isEmpty()){
            UseCaseRepository.generateDummyNotes(25);
            noteDao.insertNotes(UseCaseRepository.notes); // castina
            notes = noteDao.getAll();

        }


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
        intent.putExtra(INTENT_MAIN_KEY, note.getId());
        startActivity(intent);
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

}


