package org.opencv.gui;

import java.util.Date;

public class Student {

    char studentID;
    String first;
    String last;
    Date dob;
    String gender;
    String program;
    String reason;

    public char getStudentID() {
        return studentID;
    }

    public void setStudentID(char studentID) {
        this.studentID = studentID;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
