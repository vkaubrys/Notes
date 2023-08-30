package lt.vitalis.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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
                    openNoteDeailsActivity(note);
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
        binding.floatActionButton.setOnClickListener(
                view -> {
                   showSnackbar("FFAB was clicked");
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

    private void showSnackbar(String message) {

        Snackbar
                .make(
                        binding.notesListView,
                        "FAB was clicked",
                        Snackbar.LENGTH_LONG
                )
                .show();
    }

    private void removeNoteFromList(Note note) {
        notes.remove(note);
        adapter.notifyDataSetChanged();
        showSnackbar("Note with id: " + note.getId() + "was removed");

    }
    private void openNoteDeailsActivity(Note note) {
        Intent intent = new Intent(this, NoteDetails.class);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("description", note.getDescription());
        intent.putExtra("creationDate", note.getCreationDate());
        intent.putExtra("updateDate", note.getUpdateDate());
        startActivity(intent);



//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, note.toString());
//        sendIntent.setType("text/plain");
//
//        startActivity(shareintent);


//        String videoId = "0xB3T4MPEr0";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
//        intent.putExtra("VIDEO_ID", videoId);
//        startActivity(intent);


    }
}


