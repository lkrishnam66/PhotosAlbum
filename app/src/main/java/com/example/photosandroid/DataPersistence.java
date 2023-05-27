package com.example.photosandroid;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;

public class DataPersistence implements Serializable {
    public static final long serialVersionUID = 1L;
    private static Context context;
    public static final String storeFile = "data.dat";
    private static ArrayList<Album> allAlbums = new ArrayList<Album>();

    public DataPersistence(Context context){
        this.context = context;
    }

    public static void read(){
        File file = new File(context.getFilesDir(), storeFile);
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            MainActivity.user = (User)ois.readObject();
            ois.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void write() throws IOException {
        File file = new File(context.getFilesDir(), storeFile);
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(MainActivity.user);
        }catch(Exception e){
            e.printStackTrace();
        }
        oos.close();
    }
}
