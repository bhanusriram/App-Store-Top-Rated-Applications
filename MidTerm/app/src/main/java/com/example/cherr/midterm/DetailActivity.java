package com.example.cherr.midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView app_d,date_d,genre_d,artist_d,copy;
    ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        app_d=findViewById(R.id.textViewApp_D);
        date_d=findViewById(R.id.textViewDate_D);
        genre_d=findViewById(R.id.textViewGenre_D);
        artist_d=findViewById(R.id.textViewDeveloper);
        copy=findViewById(R.id.textViewCopyRight);
        iv1=findViewById(R.id.imageView2);

        Music music= (Music) getIntent().getSerializableExtra(MainActivity.det_key);
        app_d.setText(music.getAppname());
        String genretext="";
        for(int i=0;i<music.getGenres().size();i++){
            genretext=genretext+music.getGenres().get(i);
            if(i<music.getGenres().size()-1){
                genretext=genretext+", ";
            }

        }
        genre_d.setText(genretext);
        artist_d.setText(music.getArtistname());
        date_d.setText(music.getReleasedate());
        copy.setText(music.getCopyright());

        Log.d("image", "onCreate: "+music.getArtworkurl());
        Picasso.with(DetailActivity.this).load(music.getArtworkurl()).into(iv1);
    }
}
