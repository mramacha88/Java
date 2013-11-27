/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Meghashree M Ramachandra
 */
public abstract class Record implements Serializable {

    DateFormat df = new SimpleDateFormat("HHmm");
    private String day;
    private Date sunRise;
    private Date sunSet;

    //No argument Constructor
    public Record() {
        day = "noday";
        sunRise = null;
        sunSet = null;
    }

    //full argument Constructor
    public Record(String day, Date sunRise, Date sunSet) {
        this.day = day;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
    }

    //Accessors and Mutators
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getSunRise() {
        return sunRise;
    }

    public void setSunRise(Date sunRise) {
        this.sunRise = sunRise;
    }

    public Date getSunSet() {
        return sunSet;
    }

    public void setSunSet(Date sunSet) {
        this.sunSet = sunSet;
    }

    @Override
    public String toString() {
        return "Record{" + "day=" + day + ", sunRise=" + df.format(sunRise) + ", sunSet=" + df.format(sunSet) + '}';
    }
}
