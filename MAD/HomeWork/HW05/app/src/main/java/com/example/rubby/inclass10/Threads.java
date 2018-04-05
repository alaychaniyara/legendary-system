package com.example.rubby.inclass10;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rubby on 4/4/2018.
 */

public class Threads {

    public ArrayList<Thread> threads;

    public static class Thread implements Serializable{
        String user_fname, user_lname, user_id, id, title, created_at;

        public String getUser_fname() {
            return user_fname;
        }

        public String getUser_lname() {
            return user_lname;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCreated_at() {
            return created_at;
        }
    }
}
