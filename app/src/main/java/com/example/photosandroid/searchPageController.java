package com.example.photosandroid;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;


import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;


public class searchPageController extends AppCompatActivity {
    private Button searchButton;
    private ListView resultListView;
    private AutoCompleteTextView tag1Autocomplete;
    private AutoCompleteTextView tag2Autocomplete;

    private ArrayAdapter<String> autoCompleteAdapter;

    private ArrayAdapter<String> autoCompleteAdapter2;
    private RadioButton andButton;
    private RadioButton orButton;

    private Button backSearchButton;
    private MyAdapter resultAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        searchButton = findViewById(R.id.searchButton);
        resultListView = findViewById(R.id.resultListView);
        tag1Autocomplete = findViewById(R.id.tag1Autocomplete);
        tag2Autocomplete = findViewById(R.id.tag2Autocomplete);
        andButton = findViewById(R.id.andButton);
        orButton = findViewById(R.id.orButton);
        backSearchButton = findViewById(R.id.backSearchButton);

        ArrayList<Photo> allPhotos = new ArrayList<Photo>();
        for(Album a: MainActivity.user.albumList){
            for(Photo p: a.getPhotoArrayList()){
                allPhotos.add(p);
            }
        }

        ArrayList<String> locationValues = new ArrayList<String>();
        ArrayList<String> personValues = new ArrayList<String>();

        for(Photo p: allPhotos){
            ArrayList<String> temp = p.getTag().getLVals();
            ArrayList<String> temp2 = p.getTag().getPVals();
            for(String s: temp){
                locationValues.add(s);
            }
            for(String s: temp2){
                personValues.add(s);
            }
        }

        HashSet<String> setWithoutDuplicates = new HashSet<>();
        for (String element : locationValues) {
            setWithoutDuplicates.add(element.toLowerCase());
        }

        locationValues.clear();
        locationValues.addAll(setWithoutDuplicates);

        HashSet<String> setWithoutDuplicates2 = new HashSet<>();
        for (String element : personValues) {
            setWithoutDuplicates2.add(element.toLowerCase());
        }

        personValues.clear();
        personValues.addAll(setWithoutDuplicates);


        autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, locationValues);
        tag1Autocomplete.setAdapter(autoCompleteAdapter);
        tag1Autocomplete.setThreshold(1);

        autoCompleteAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, personValues);
        tag2Autocomplete.setAdapter(autoCompleteAdapter2);
        tag2Autocomplete.setThreshold(1);

        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(tag1Autocomplete.getText()!=null && !tag1Autocomplete.getText().toString().equals("tag1Value") && !andButton.isChecked() && !orButton.isChecked()){
                    ArrayList<Photo> result = new ArrayList<Photo>();
                    for(Photo p: allPhotos){
                        for(String loc: p.getTag().getLVals()){
                            if(loc.equalsIgnoreCase(tag1Autocomplete.getText().toString())){
                                result.add(p);
                                break;
                            }
                        }
                    }
                    resultAdapter = new MyAdapter(getApplicationContext(),R.layout.list_item,result);
                    resultListView.setAdapter(resultAdapter);
                }

                if(tag2Autocomplete.getText()!=null && !tag2Autocomplete.getText().toString().equals("tag2Value") && !andButton.isChecked() && !orButton.isChecked()){
                    ArrayList<Photo> result = new ArrayList<Photo>();
                    for(Photo p: allPhotos){
                        for(String person: p.getTag().getPVals()){
                            if(person.equalsIgnoreCase(tag2Autocomplete.getText().toString())){
                                result.add(p);
                                break;
                            }
                        }
                    }
                    resultAdapter = new MyAdapter(getApplicationContext(),R.layout.list_item,result);
                    resultListView.setAdapter(resultAdapter);
                }
                if(tag2Autocomplete.getText()!=null && !tag2Autocomplete.getText().toString().equals("tag2Value") && tag1Autocomplete.getText()!=null && !tag1Autocomplete.getText().toString().equals("tag1Value") && andButton.isChecked()){
                    ArrayList<Photo> result = new ArrayList<Photo>();
                    for(Photo p: allPhotos){
                        ArrayList<String> locations = p.getTag().getLVals();
                        ArrayList<String> persons = p.getTag().getPVals();
                        int inLocations = 0;
                        int inPersons = 0;
                        for(String person: persons){
                            if(person.equalsIgnoreCase(tag2Autocomplete.getText().toString())){
                                inPersons=1;
                            }
                        }
                        for(String loc: locations){
                            if(loc.equalsIgnoreCase(tag1Autocomplete.getText().toString())){
                                inLocations=1;
                            }
                        }
                        if(inLocations==1 && inPersons==1){
                            result.add(p);
                        }

                    }
                    resultAdapter = new MyAdapter(getApplicationContext(),R.layout.list_item,result);
                    resultListView.setAdapter(resultAdapter);
                }

                if(tag2Autocomplete.getText()!=null && !tag2Autocomplete.getText().toString().equals("tag2Value") && tag1Autocomplete.getText()!=null && !tag1Autocomplete.getText().toString().equals("tag1Value") && orButton.isChecked()){
                    ArrayList<Photo> result = new ArrayList<Photo>();
                    for(Photo p: allPhotos){
                        ArrayList<String> locations = p.getTag().getLVals();
                        ArrayList<String> persons = p.getTag().getPVals();
                        int inLocations = 0;
                        int inPersons = 0;
                        for(String person: persons){
                            if(person.equalsIgnoreCase(tag2Autocomplete.getText().toString())){
                                inPersons=1;
                            }
                        }
                        for(String loc: locations){
                            if(loc.equalsIgnoreCase(tag1Autocomplete.getText().toString())){
                                inLocations=1;
                            }
                        }
                        if(inLocations==1 || inPersons==1){
                            result.add(p);
                        }

                    }
                    resultAdapter = new MyAdapter(getApplicationContext(),R.layout.list_item,result);
                    resultListView.setAdapter(resultAdapter);
                }



            }
        });

        backSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(searchPageController.this, MainActivity.class);
                startActivity(change);
            }
        });




    }
}

