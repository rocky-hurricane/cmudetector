package org.opencv.demo.model;

import java.sql.Date;

/**
 * @Description Created by rocky on 11/11/2017.
 */
public class Student {

    String studentId;
    String firstName;
    String lastName;
    String gender;
    String program;
    java.sql.Date dateBirth;
    String dateEnrollment;
    String profile;

    public String getProfile(){return  profile;}

    public void setProfile(String profile){this.profile = profile;}

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public java.sql.Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(java.sql.Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(String year) {
        this.dateEnrollment = year;
    }
}
