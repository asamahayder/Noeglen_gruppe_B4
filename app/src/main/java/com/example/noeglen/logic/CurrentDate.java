package com.example.noeglen.logic;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Currentdate objektet bliver brugt til at holde styr på hvilken date det er i dag
 */

public class CurrentDate {

    /**
     * @variable ourInstance
     * Dette objekt er en singleton, derfor har man en statisk variabel af den samme instans. Bliver brugt til at kun have en current dato
     * @variable date
     * Er en referance til Date objektet som bliver brugt første gang for at lave et objekt
     * @variable format
     * Dette er formatet af hvordan datoen skal laves. f.eks dd/M/yyyy
     * @variable dateString
     * Når dateon er gemt, bliver den gemt som en string. Bliver brugt til at tjekke efter i calenderen senere
     */

    private static final CurrentDate ourInstance = new CurrentDate();

    /**
     * Metoden der henter den nuværende singleton objekt.
     *
     * @return
     * returner det nuværende singleton objekt
     */

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

    /** GETTERS AND SETTERS */

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
