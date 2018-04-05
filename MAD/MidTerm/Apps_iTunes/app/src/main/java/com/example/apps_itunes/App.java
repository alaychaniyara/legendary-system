package com.example.apps_itunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by akki0923 on 3/12/18.
 */

public class App implements Serializable {
    String appName,artistName,releaseDate,imageUrl,copyRight;
    ArrayList<String> genre=new ArrayList<String>();

    @Override
    public String toString() {
        return "App{" +
                "appName='" + appName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", copyRight='" + copyRight + '\'' +
                ", genre=" + genre +
                '}';
    }
}
