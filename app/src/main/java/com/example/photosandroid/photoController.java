package com.example.photosandroid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;


public class photoController extends AppCompatActivity {
    private ListView photoListView;
    private Button addButton, slideshowButton, moveAlbumButton, tagButton, deletePButton,backButton;
    private ImageView imageView;
    private TextView textView;
    private Photo selected;

    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_page);
        addButton = findViewById(R.id.addButton);
        deletePButton = findViewById(R.id.deletePButton);
        tagButton = findViewById(R.id.tagButton);
        moveAlbumButton = findViewById(R.id.moveAlbumButton);
        slideshowButton = findViewById(R.id.slideshowButton);
        backButton = findViewById(R.id.backButton2);
        textView = findViewById(R.id.textView3);
        photoListView = findViewById(R.id.photoListView);
        imageView = findViewById(R.id.imageView);




        myAdapter = new MyAdapter(this,R.layout.list_item,MainActivity.user.getCurrAlbum().getPhotoArrayList());
        photoListView.setAdapter(myAdapter);

        if(!myAdapter.isEmpty()){
            selected=myAdapter.getItem(0);
        }
        photoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (Photo)parent.getItemAtPosition(position);
                photoListView.setSelection(position);
                imageView.setImageURI(selected.getImage());
                if(!selected.getTag().getLVals().isEmpty() || !selected.getTag().getPVals().isEmpty())
                 textView.setText(selected.getTag().toString());
                else{
                    textView.setText(selected.getPhotoName());
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 21);

           }
        });

        deletePButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(MainActivity.user.getCurrAlbum().getPhotoArrayList().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please add a photo first", Toast.LENGTH_SHORT).show();
                }
                else{
                    MainActivity.user.getCurrAlbum().removePhoto(selected);
                    photoListView.setAdapter(myAdapter);
                    imageView.setImageURI(null);
                    textView.setText("");
                    try {
                        DataPersistence.write();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.user.getCurrAlbum().getPhotoArrayList().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please add a photo first", Toast.LENGTH_SHORT).show();
                }
                else{
                    View custView = getLayoutInflater().inflate(R.layout.add_delete, null);
                    Button add_Button = custView.findViewById(R.id.add_Button);
                    Button deleteTagButton2 = custView.findViewById(R.id.deleteTagButton2);
                    AlertDialog.Builder builder = new AlertDialog.Builder(photoController.this);
                    builder.setView(custView);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    add_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                            // Inflate the custom layout
                            View customView = getLayoutInflater().inflate(R.layout.tag_dialog, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(photoController.this);
                            builder.setView(customView);

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            TextView locationTextView = customView.findViewById(R.id.location_textview);
                            TextView personTextView = customView.findViewById(R.id.person_textview);

                            EditText locationEditText = customView.findViewById(R.id.location_edittext);
                            EditText personEditText = customView.findViewById(R.id.person_edittext);

                            Button confirmButton = customView.findViewById(R.id.confirmButton);
                            confirmButton.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    if(!locationEditText.getText().toString().isEmpty() || !personEditText.getText().toString().isEmpty()){
                                        String[] lValsString = locationEditText.getText().toString().split(",\\s*");

                                        String[] pValsString = personEditText.getText().toString().split(",\\s*");


                                        for(String s: lValsString){
                                            if(!s.equals(""))
                                                selected.getTag().addLocation(s);
                                        }
                                        for(String s: pValsString){
                                            if(!s.equals(""))
                                                selected.getTag().addPerson(s);
                                        }

                                        try {
                                            DataPersistence.write();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        alertDialog.dismiss();
                                    }
                                }
                            });
                            Button closeButton = customView.findViewById(R.id.closeButton);
                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    });

                    deleteTagButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            View customView3 = getLayoutInflater().inflate(R.layout.tag_dropdown, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(photoController.this);
                            builder.setView(customView3);

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            Spinner locationSpinner = customView3.findViewById(R.id.location_spinner);
                            Spinner personSpinner = customView3.findViewById(R.id.person_spinner);
                            Button deleteButton = customView3.findViewById(R.id.deleteTagButton2);
                            Button cancelButton = customView3.findViewById(R.id.cancelButton);
                            ArrayList<String> lVals = selected.getTag().getLVals();;
                            ArrayList<String> pVals = selected.getTag().getPVals();

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(photoController.this, android.R.layout.simple_spinner_item, lVals);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            locationSpinner.setAdapter(adapter);

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(photoController.this, android.R.layout.simple_spinner_item, pVals);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            personSpinner.setAdapter(adapter2);

                            final String[] locationItem = {""};
                            final String[] personItem = {""};
                            locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    locationItem[0] = parent.getItemAtPosition(position).toString();
                                    // Do something with the selected item
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // Do nothing
                                }
                            });

                            personSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    personItem[0] = parent.getItemAtPosition(position).toString();
                                    // Do something with the selected item
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // Do nothing
                                }
                            });

                            deleteButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!locationItem[0].isEmpty() && personItem[0].isEmpty()){
                                        selected.getTag().removeLocation(locationItem[0]);
                                    }
                                    else if(locationItem[0].isEmpty() && !personItem[0].isEmpty()){
                                        selected.getTag().removePerson(personItem[0]);
                                    }
                                    else if(!locationItem[0].isEmpty() && !personItem[0].isEmpty()){
                                        selected.getTag().removeLocation(locationItem[0]);
                                        selected.getTag().removePerson(personItem[0]);
                                    }
                                    try {
                                        DataPersistence.write();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    alertDialog.dismiss();
                                }

                            });

                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });



                        }
                    });
                }
                }


                });

            moveAlbumButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if(MainActivity.user.getAllAlbums().size() == 1){
                     Toast.makeText(getApplicationContext(), "Please create more than one album,", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     View customView4 = getLayoutInflater().inflate(R.layout.album_dropdown, null);

                     AlertDialog.Builder builder = new AlertDialog.Builder(photoController.this);
                     builder.setView(customView4);

                     AlertDialog alertDialog = builder.create();
                     alertDialog.show();

                     Button moveToButton = customView4.findViewById(R.id.moveToButton);
                     Button cancelAlbumButton = customView4.findViewById(R.id.cancelAlbumButton);
                     Spinner albumSpinner = customView4.findViewById(R.id.albumSpinner);

                     ArrayList<String> albumStrings = new ArrayList<String>();
                     final String[] albumItem = {""};
                     for(Album a: MainActivity.user.getAllAlbums()){
                         albumStrings.add(a.getAlbumName());
                         System.out.println(a.getAlbumName() + " ");
                     }
                     ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(photoController.this, android.R.layout.simple_spinner_item, albumStrings);
                     adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     albumSpinner.setAdapter(adapter4);


                     ;
                     albumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                             albumItem[0] = parent.getItemAtPosition(position).toString();
                             // Do something with the selected item
                         }

                         @Override
                         public void onNothingSelected(AdapterView<?> parent) {
                             // Do nothing
                         }
                     });

                     moveToButton.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             if(albumItem[0].isEmpty()){
                                 Toast.makeText(getApplicationContext(), "Please select an album", Toast.LENGTH_SHORT).show();
                             }
                             else{
                                 for(Album a: MainActivity.user.getAllAlbums()){
                                     if(albumItem[0].equals(a.getAlbumName())){
                                         a.addPhoto(selected);
                                         MainActivity.user.getCurrAlbum().removePhoto(selected);
                                         myAdapter.notifyDataSetChanged();
                                         try {
                                             DataPersistence.write();
                                         } catch (IOException e) {
                                             throw new RuntimeException(e);
                                         }
                                         alertDialog.dismiss();
                                     }
                                 }
                             }
                         }
                     });

                     cancelAlbumButton.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             alertDialog.dismiss();
                         }
                     });
                 }
                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent change = new Intent(photoController.this, MainActivity.class);
                    startActivity(change);
                }
            });

            slideshowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent change = new Intent(photoController.this, slideshowController.class);
                    startActivity(change);
                }
            });


        }



    private static final int PICK_IMAGE_REQUEST = 21;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            String name = getFileName(imageUri);
            Photo p = new Photo(imageUri, name);
            MainActivity.user.getCurrAlbum().addPhoto(p);
            MainActivity.user.setCurrAlbum(MainActivity.user.getCurrAlbum());
            photoListView.setAdapter(myAdapter);
            try {
                DataPersistence.write();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}

