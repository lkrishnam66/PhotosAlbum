package com.example.photosandroid;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;
import java.util.*;
/**
 * @author Hritish Mehta
 * @author Likhit Krishnam
 * This class controls the editing of photos
 */
public class Photo implements Serializable{

    /**
     * String that holds the name of the photo
     */
    private String photoName;

    private String image;

    private Tag tag;

    /**
     * serialVersionUID for data persistence application
     */
    private static final long serialVersionUID = 1L;




    /**
     * Constructor
     */
    public Photo() {
        this.image = null;
        this.photoName = null;
        this.tag = new Tag(new ArrayList<String>(),new ArrayList<String>());
    }


    public Photo(Uri uri, String name){
        this.image = uri.toString();
        this.photoName = name;
        this.tag = new Tag(new ArrayList<String>(),new ArrayList<String>());
    }

    /**
     * Getter method for photo name
     * @return photoName
     */
    public String getPhotoName(){
        return photoName;
    }

    /**
     * Setter method to set the photo name
     * @param photoName
     */
    public void setPhotoName(String photoName){
        this.photoName = photoName;
    }

    public Uri getImage(){
        return Uri.parse(image);
    }

    public void setImage(Uri image){
        this.image = image.toString();
    }

    /**
     * Getter method for the tag list
     * @return tags
     */
    public Tag getTag(){
        return tag;
    }

    /**
     * Equals implementation overriding object equals.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(!(obj instanceof Photo))
            return false;

        Photo other = (Photo) obj;

        if(!this.getPhotoName().equals(other.getPhotoName())){
            return false;
        }
        //for each tag in other, check if it exists in this
        //for each tag in this check if it exists in other
        //basically: if a is a subset of b and b is a subset of a, a==b
        for(String s: tag.getLVals()){
            if(!other.getTag().getLVals().contains(s)){
                return false;
            }
        }
        for(String s: tag.getPVals()){
            if(!other.getTag().getPVals().contains(s)){
                return false;
            }
        }
        //actual picture file has to be the same
        if(!this.getImage().equals(other.getImage())){
            return false;
        }


        return true;
    }






}
