package lt.vitalis.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lt.vitalis.notes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> list = new ArrayList<>();

        List<String> newList = Arrays.asList(
                "Pirmadienis",
                "Antradienis",
                "Treciadienis",
                "Ketvirtedienis",
                "Penktadienis",
                "Sestadienis",
                "Sekmadienis"
        );


        list.addAll(newList);
        list.addAll(newList);
        list.addAll(newList);
        list.addAll(newList);
        list.addAll(newList);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );
        binding.notesListView.setAdapter(adapter);


    }
}