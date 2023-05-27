package com.example.photosandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;


public class MainActivity extends AppCompatActivity {
    private Button searchButton, openButton, addAlbumButton, deleteAlbumButton, editAlbumButton;
    private TextView editAlbumTextView, addAlbumTextView;
    private ListView albumListView;

    private ArrayAdapter<Album> arrayAdapter;
    public static User user = new User("Main");
    public Object selected;
    public Album selectedAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataPersistence data = new DataPersistence(MainActivity.this);
        data.read();

        openButton = findViewById(R.id.searchButton);
        searchButton = findViewById(R.id.searchButton2);
        deleteAlbumButton = findViewById(R.id.deleteAlbumButton);
        addAlbumButton = findViewById(R.id.photoListView);
        arrayAdapter = new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1,MainActivity.user.getAllAlbums());
        albumListView = findViewById(R.id.albumListView);
        albumListView.setAdapter(arrayAdapter);
        editAlbumButton = findViewById(R.id.editAlbumButton);
        addAlbumTextView = findViewById(R.id.addAlbumTextView);
        editAlbumTextView = findViewById(R.id.editAlbumTextView);

        if(!arrayAdapter.isEmpty()){
            selectedAlbum = arrayAdapter.getItem(0);
        }

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position);
                albumListView.setSelection(position);
                selectedAlbum = (Album) selected;
            }

        });


        addAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String albumName = addAlbumTextView.getText().toString().trim();
                if (!albumName.isEmpty()) {
                    int exists=0;
                    for(int i=0; i< arrayAdapter.getCount(); i++){
                        Album temp = arrayAdapter.getItem(i);
                        if(temp.getAlbumName().equals(albumName)){
                            exists=1;
                        }
                    }
                    if(exists==0) {
                        Album addition = new Album(albumName);
                        MainActivity.user.addAlbum(addition);
                        try {
                            data.write();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Album already exists")
                                .setMessage("Please rename your album")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked OK button
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                }
            }
        });

        deleteAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAlbum != null) {
                    arrayAdapter.remove(selectedAlbum);
                    MainActivity.user.deleteAlbum(selectedAlbum);
                    try {
                        data.write();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        editAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAlbum != null) {
                    String albumName = editAlbumTextView.getText().toString();
                    int exists=0;
                    for(int i=0; i< arrayAdapter.getCount(); i++){
                        Album temp = arrayAdapter.getItem(i);
                        if(temp.getAlbumName().equals(albumName)){
                            exists=1;
                        }
                    }
                    if(exists==0) {
                        selectedAlbum.setAlbumName(albumName);
                        try {
                            data.write();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Album already exists")
                                .setMessage("Please rename your album")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked OK button
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent change = new Intent(MainActivity.this, searchPageController.class);
                startActivity(change);
            }
        });

        openButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(arrayAdapter.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please create an album first", Toast.LENGTH_SHORT).show();
                }
                else{
                    MainActivity.user.setCurrAlbum(selectedAlbum);
                    Intent change = new Intent(MainActivity.this, photoController.class);
                    startActivity(change);
                }
            }
        });
    }
}

