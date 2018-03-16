package com.example.cherr.midterm;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Music> {
    public MyAdapter(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music movies = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.artist = (TextView) convertView.findViewById(R.id.textView3Artist);
            viewHolder.genre = (TextView) convertView.findViewById(R.id.textViewGenre);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViewApp);
            viewHolder.date = (TextView) convertView.findViewById(R.id.textViewDate);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.artist.setText(movies.getArtistname());
        viewHolder.name.setText(movies.getAppname());
        viewHolder.date.setText(movies.getReleasedate());
        String genretext="";
        for(int i=0;i<movies.getGenres().size();i++) {
            genretext=genretext+movies.getGenres().get(i);
            if(i<movies.getGenres().size()-1){
                genretext=genretext+", ";
            }

        }
        viewHolder.genre.setText(genretext);
        Picasso.with(getContext()).load(movies.getArtworkurl()).into(viewHolder.iv);
        return convertView;
    }

    //View Holder to cache the views
    private static class ViewHolder{

        TextView artist,genre,date,name;
        ImageView iv;
    }
}
