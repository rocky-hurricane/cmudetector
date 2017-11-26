package org.opencv.demo.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @Description Created by rocky on 26/11/2017.
 */
public class Record {

    private Date dateVisit;
    private Time timeVisit;
    private String studentId;
    private String reason;

    public Record(){

    }

    public Record(Date dateVisit, Time timeVisit, String studentId, String reason){
        this.dateVisit = dateVisit;
        this.timeVisit = timeVisit;
        this.studentId = studentId;
        this.reason = reason;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public Time getTimeVisit() {
        return timeVisit;
    }

    public void setTimeVisit(Time timeVisit) {
        this.timeVisit = timeVisit;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
