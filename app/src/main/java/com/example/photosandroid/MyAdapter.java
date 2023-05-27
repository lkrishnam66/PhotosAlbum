package com.example.photosandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Photo> {

    private ArrayList<Photo> photos;
    private Context context;
    private int layoutResource;

    public MyAdapter(Context context, int layoutResource, ArrayList<Photo> photos) {
        super(context, layoutResource, photos);
        this.context = context;
        this.photos = photos;
        this.layoutResource = layoutResource;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        LayoutInflater layIn = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tempView;
        if (convertView == null) {
            tempView = new View(context);
            tempView= layIn.inflate(R.layout.list_item, null);
            ImageView im = (ImageView) tempView.findViewById(R.id.imageView4);
            TextView tv = (TextView) tempView.findViewById(R.id.textView);
            im.setImageURI(photos.get(position).getImage());
            tv.setText(photos.get(position).getPhotoName());
            return tempView;
        }
        return convertView;
    }
}
