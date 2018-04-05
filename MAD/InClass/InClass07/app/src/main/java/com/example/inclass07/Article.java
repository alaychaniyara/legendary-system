package com.example.inclass07;

import java.io.Serializable;

/**
 * Created by akki0923 on 2/19/18.
 */

public class Article implements Serializable {
    String title,publishedat,description,urltoimage;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", publishedat='" + publishedat + '\'' +
                ", description='" + description + '\'' +
                ", urltoimage='" + urltoimage + '\'' +
                '}';
    }
}
