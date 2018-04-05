package com.example.inclass08;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by akki0923 on 1/29/18.
 */

public class Student implements Serializable{

    String name;
    String email;
    String department;
    double mood;

    protected Student(Parcel in) {
        name = in.readString();
        email = in.readString();
        department = in.readString();
        mood = in.readDouble();
    }

    public Student(String name, String email, String department, double mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "com.example.inclass03.Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", mood=" + mood +
                '}';
    }


}
