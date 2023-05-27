package com.example.photosandroid;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Hritish Mehta
 * @author Likhit Krishnam
 * This class is responsible for functionalities regarding adding and deleting pictures.
 */
public class Album implements Serializable, Adapter {
    /**
     * String instance that holds the album name
     */
    private String albumName;
    /**
     * Arraylist of photos which holds the photos in the album.
     */
    public ArrayList<Photo> photoArrayList;
    /**
     * serialVersionUID responsible for data persistence application.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Photo instance of the current photo the user is on.
     */
    public Photo currentPhoto;

    /**
     * Constructor
     * @param name
     */
    public Album(String name){
        this.albumName = name;
        photoArrayList = new ArrayList<Photo>();
        currentPhoto = null;
    }


    /**
     * Getter method to get the Arraylist of photos in the album
     * @return photoArrayList
     */
    public ArrayList<Photo> getPhotoArrayList(){
        return photoArrayList;
    }

    /**
     * Getter method to get the album name
     * @return albumName
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Setter method to set the album name
     * @param name
     */
    public void setAlbumName(String name){
        this.albumName = name;
    }

    /**
     * Getter method to get the size of the album
     * @return
     */
    public int getAlbumSize(){
        return photoArrayList.size();
    }

    /**
     * Setter method to set the Arraylist of photos to a new arraylist of photos.
     * @param photos
     */
    public void setPhotosArrayList(ArrayList<Photo> photos){
        photoArrayList = photos;
    }

    /**
     * Adds photo the photo array list
     * @param p
     */
    public void addPhoto(Photo p){
        if(canAddPhoto(p)){
            photoArrayList.add(p);
        }
    }

    /**
     * Checks if user can add photo to an album and makes sure there aren't duplicates
     * @param p
     * @return
     */
    public boolean canAddPhoto(Photo p){
        if(p==null){
            return false;
        }
        else{
            for(Photo photo: photoArrayList){
                if(photo.equals(p)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Removes a photo from the photo array list
     * @param p
     */
    public void removePhoto(Photo p){
        if(p==null){
            return;
        }
        else{
            photoArrayList.remove(p);
        }
    }


    /**
     * toString method
     * @return
     */
    public String toString(){
        String start="";
        String end= "";
        return "Name: " + albumName + " Size:" + getAlbumSize();


    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
