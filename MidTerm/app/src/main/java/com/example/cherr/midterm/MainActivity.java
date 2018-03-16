package com.example.cherr.midterm;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements GetJsonParsingAsync.iData{
    ArrayList<Music> musicresult=new ArrayList<>();
    ArrayList<Music> selectedkey=new ArrayList<>();
    TextView all;
    Button filter;
    ListView lv;
    static String det_key;
    ArrayList<String> Genres=new ArrayList<>();
    ArrayList<String> Genres1=new ArrayList<>();
    String selectedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        all=findViewById(R.id.textView2);
        filter=findViewById(R.id.buttonFilter);
        lv=findViewById(R.id.listview);
        if(isConnected()) {
            new GetJsonParsingAsync(MainActivity.this, MainActivity.this).execute("https://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json");
        }
        else{
            Toast.makeText(MainActivity.this,"No Intenet Connection",Toast.LENGTH_SHORT).show();
        }



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder passwordsList = new AlertDialog.Builder(MainActivity.this);
                passwordsList.setCancelable(false);
                passwordsList.setTitle("Choose Genres");

                final ArrayAdapter<String> passwordsAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, Genres);
                passwordsList.setAdapter(passwordsAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedPassword = Genres.get(which);
                        all.setText(selectedPassword);
                        if(selectedPassword=="All"){
                            MyAdapter adapter = new MyAdapter(MainActivity.this, R.layout.layout_item, musicresult);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent det_intent=new Intent(MainActivity.this,DetailActivity.class);
                                    det_key = "200";
                                    det_intent.putExtra(det_key,musicresult.get(position));
                                    startActivity(det_intent);
                                }
                            });
                        }
                        else{
                            Music m2=new Music();
                            for(int i=0;i<musicresult.size();i++){
                                m2=musicresult.get(i);
                                for(int j=0;j<m2.getGenres().size();j++){
                                    if(selectedPassword.compareTo(m2.getGenres().get(j))==0){
                                        selectedkey.add(m2);
                                        MyAdapter adapter = new MyAdapter(MainActivity.this, R.layout.layout_item, selectedkey);
                                        lv.setAdapter(adapter);
                                        break;
                                    }
                                }
                            }
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent det_intent=new Intent(MainActivity.this,DetailActivity.class);
                                    det_key = "200";
                                    det_intent.putExtra(det_key,selectedkey.get(position));
                                    startActivity(det_intent);
                                }
                            });
                        }

                    }
                });
                passwordsList.show();
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if (nf == null || !nf.isConnected()) {
            return false;
        }
        return true;
    }


    @Override
    public void processFinish(ArrayList<Music> output) {
        if(output.size()!=0){

            musicresult=output;
            MyAdapter adapter = new MyAdapter(this, R.layout.layout_item, musicresult);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent det_intent=new Intent(MainActivity.this,DetailActivity.class);
                    det_key = "200";
                    det_intent.putExtra(det_key,musicresult.get(position));
                    startActivity(det_intent);
                }
            });
            Music m1=new Music();
            for(int i=0;i<output.size();i++){
                m1=output.get(i);
                for(int j=0;j<m1.getGenres().size();j++){
                    Genres1.add(m1.getGenres().get(j));
                }
            }
            Collections.sort(Genres1);
            Set<String> set = new HashSet<String>();
            set.addAll(Genres1);
            Genres.addAll(set);
            Collections.sort(Genres);
           // Genres=Genres1;
            Genres.add(0,"All");

        }
        else{
            Toast.makeText(MainActivity.this,"There are no apps this api ",Toast.LENGTH_SHORT).show();
        }

    }
}
