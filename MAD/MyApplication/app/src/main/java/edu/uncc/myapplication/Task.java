/* Group 34
   Name: Akshay Karai, Naga Poorna Pujitha
   Homework assignment 2
* */

package edu.uncc.myapplication;

import java.io.Serializable;

public class Task implements Serializable {

    private String date;
    private String time;
    private String title;
    private String priority;
    private int priorityRadio;

    @Override
    public String toString() {
        return super.toString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getPriorityRadio() {
        return priorityRadio;
    }

    public void setPriorityRadio(int priorityRadio) {
        this.priorityRadio = priorityRadio;
    }
}
