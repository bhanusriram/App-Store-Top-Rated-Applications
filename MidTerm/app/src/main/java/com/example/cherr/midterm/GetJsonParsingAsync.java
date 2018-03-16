package com.example.cherr.midterm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by cherr on 12-03-2018.
 */

public class GetJsonParsingAsync extends AsyncTask<String,Void,ArrayList<Music>> {
    iData idata;
    ProgressDialog dialog;
    Context con;

    public GetJsonParsingAsync(iData idata,Context iContext) {
        this.idata = idata;
        con=iContext;
        dialog=new ProgressDialog(con);
    }

    @Override
    protected void onPreExecute() {
        dialog.setTitle("Loading Movies");
        dialog.show();
    }

    @Override

    protected ArrayList<Music> doInBackground(String[] params) {
        HttpURLConnection con = null;
        ArrayList<Music> result = new ArrayList<>();

        //String[] genres=new String[20];
        BufferedReader br=null;
        StringBuilder sb=new StringBuilder();
        try {
            URL url=new URL(params[0]);
            con= (HttpURLConnection) url.openConnection();
            InputStream is=con.getInputStream();
            br=new BufferedReader(new InputStreamReader(is));
            String line="";
            while ((line=br.readLine())!=null){
                sb.append(line);
            }
            String json= sb.toString();

            JSONObject root = new JSONObject(json);
            JSONObject feed=root.getJSONObject("feed");

            JSONArray articles = feed.getJSONArray("results");
           // Log.d("demo", String.valueOf(articles.length()));
            for (int i = 0; i < articles.length(); i++) {
                ArrayList<String> genres=new ArrayList<>();
                JSONObject articleJson = articles.getJSONObject(i);
                Music article = new Music();
                article.appname = articleJson.getString("name");
                article.releasedate = articleJson.getString("releaseDate");
                article.artistname = articleJson.getString("artistName");
                article.artworkurl=articleJson.getString("artworkUrl100");
                article.copyright=articleJson.getString("copyright");
                JSONArray genresJson = articleJson.getJSONArray("genres");
                String genre=null;
                genres.clear();
                for (int j = 0; j < genresJson.length(); j++){
                    JSONObject genreJson = genresJson.getJSONObject(j);
                    genre=genreJson.getString("name");
                    genres.add(genre);

                 }
                Collections.sort(genres);
                article.genres=genres;
                //Log.d("demo", "doInBackground: "+article.getGenres().size());
                result.add(article);
                Log.d("json", article.getGenres().get(0));


            }
        } catch (IOException e1) {
            Log.d("demo", "IOE");
            e1.printStackTrace();
        } catch (JSONException e1) {
            Log.d("demo", "JSOE");
            e1.printStackTrace();

        } finally {
            if(con!=null){
                con.disconnect();
            }
        }
//        Log.d("demo", "doInBackground: "+result.get(0).toString());
        return result;
    } public interface iData{
        void processFinish(ArrayList<Music> output);
    }

    @Override
    protected void onPostExecute(ArrayList<Music> arrayList) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        idata.processFinish(arrayList);

    }
}