package com.example.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akki0923 on 1/29/18.
 */

public class Student implements Parcelable{

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

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(department);
        parcel.writeDouble(mood);
    }
}
