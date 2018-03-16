package com.example.cherr.midterm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cherr on 12-03-2018.
 */

public class Music implements Serializable {
    String appname,artistname,releasedate,artworkurl,copyright;
    ArrayList<String> genres=new ArrayList<>();

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getCopyright() {
        return copyright;

    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getArtworkurl() {
        return artworkurl;
    }



    public void setArtworkurl(String artworkurl) {
        this.artworkurl = artworkurl;
    }


}
