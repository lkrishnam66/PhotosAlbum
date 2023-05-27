package com.example.photosandroid;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
/**
 * @author Hritish Mehta
 * @author Likhit Krishnam
 * This class controls the actions of the album page
 */
public class Tag implements Serializable{
    /**
     * serialVersionUID for data persistence
     */
    private static final long serialVersionUID = 1L;
    /**
     * String instance of key of tag
     */
    public ArrayList<String> lVals = new ArrayList<String>();
    /**
     * String array of values associated with key
     */
    public ArrayList<String> pVals = new ArrayList<String>();

    public Tag(ArrayList<String> lVals, ArrayList<String> pVals){
        this.lVals.add(0,"");
        this.pVals.add(0,"");
        this.lVals = lVals;
        this.pVals = pVals;
    }

    public ArrayList<String> getLVals(){
        return lVals;
    }

    public ArrayList<String> getPVals(){
        return pVals;
    }

    public void setLVals(ArrayList<String> lVals){
        this.lVals = lVals;
    }

    public void setPVals(ArrayList<String> pVals){
        this.pVals = pVals;
    }

    public void addLocation(String s){
        lVals.add(s);
    }

    public void addPerson(String s){
        pVals.add(s);
    }

    public void removeLocation(String s){
        lVals.remove(s);
    }

    public void removePerson(String s){
        pVals.remove(s);
    }
    /**
     * Equals method implementation overriding object equals
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(!(obj instanceof Tag))
            return false;

        Tag other = (Tag) obj;

        for(String s: lVals){
            if(!other.getLVals().contains(s)){
                return false;
            }
        for(String x: pVals){
                if(!other.getPVals().contains(x)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * toString implementation
     * @return
     */
    public String toString(){
        String result = "";
        result += "Location: ";
        for(String s: lVals){
            result += s + ", ";
        }

        result += "\nPerson: ";
        for(String p: pVals){
            result += p + ", ";
        }
        return result;
    }
}
