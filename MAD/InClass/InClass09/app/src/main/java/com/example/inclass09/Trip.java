package com.example.inclass09;

import java.util.ArrayList;

/**
 * Created by akki0923 on 3/26/18.
 */

public class Trip {
    String title;

    @Override
    public String toString() {
        return "Trip{" +
                "title='" + title + '\'' +
                ", points=" + points +
                '}';
    }

    ArrayList<Points> points;
    public static class Points{
        @Override
        public String toString() {
            return "Points{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }



        double latitude,longitude;
    }
}
