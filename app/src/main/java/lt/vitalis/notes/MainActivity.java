package lt.vitalis.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
                    Log.i(TAG, "OnListeItemClicked: " + adapterView.getItemIdAtPosition(position));
                    Log.i(TAG, "OnListeItemClicked: " + position);
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

    private void showItemRemoveAlertDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("Do you realy want to remove thid item?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
            removeNoteFromList(note);
        })
                .setNegativeButton("No", null)
                .show();
    }

    private void removeNoteFromList (Note note) {
        notes.remove(note);
        adapter.notifyDataSetChanged();

    }
}


