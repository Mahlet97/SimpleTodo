package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    //ListView lvItems;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        //mock data
        //items.add("First item");
        //items.add("Second item");
        setupListViewListener();
    }

    public void onAddItem(View v){
        EditText etNewItems = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItems.getText().toString();
        itemsAdapter.add(itemText);
        etNewItems.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Items Added to List", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener(){
        Log.i("MainActivity", "Setting up Listener on list view");

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Items removed from list" + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();

                return true;
            }
        });

    }

    private File getDataFile(){
        return new File(getFilesDir(), "todo.txt");

    }

    private void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity" , "Error reading files" , e);
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        try
        {
            FileUtils.writeLines(getDataFile(), items);
        }

        catch (IOException e) {
            //e.printStackTrace();
            Log.e("MainActivity" , "Error writing files" , e);
            //items = new ArrayList<>();
        }

    }



}
