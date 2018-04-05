package com.example.intentsdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akki0923 on 1/29/18.
 */

public class Person implements Parcelable {

    String name;
    double age;
    String city;
    protected Person(Parcel in) {
        this.name=in.readString();
        this.age=in.readDouble();
        this.city=in.readString();
    }


    public Person(String name, double age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.name);
        parcel.writeDouble(this.age);
        parcel.writeString(this.city);
    }
}
