package com.example.noeglen.logic;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {

    private static final CurrentDate ourInstance = new CurrentDate();

    public static CurrentDate getInstance() {
        return ourInstance;
    }
    private Date date;
    private SimpleDateFormat format;
    private String dateString;

    private CurrentDate() {
    }

    public String createCurrentDate(){
     this.date= new Date();
     this.format = new SimpleDateFormat("dd/MM/yy");
     this.dateString = format.format(date);
     return dateString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
