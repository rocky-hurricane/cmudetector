package org.opencv.gui.javafx.controller;

public class Guest {

    String guestID;
    String first;
    String last;
    String gender;
    String reason;

    Guest(String id, String first, String last, String gender, String reason){
        this.guestID = id;
        this.first = first;
        this.last = last;
        this.gender = gender;
        this.reason = reason;
    }

    public String toString(){
        return guestID + first + last + gender + reason;
    }
}
