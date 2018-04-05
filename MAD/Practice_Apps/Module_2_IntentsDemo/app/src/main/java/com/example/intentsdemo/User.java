package com.example.intentsdemo;

import java.io.Serializable;

/**
 * Created by akki0923 on 1/29/18.
 */

public class User implements Serializable {
    String name;
    double age;

    public User(String name, double age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
