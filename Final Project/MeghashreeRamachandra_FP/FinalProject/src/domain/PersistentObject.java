/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @author Meghashree M Ramachandra
 */
//PersistentObject class which Encapsulates a java.util.Date object and the ArrayList object into a serializable 
public class PersistentObject implements Serializable {
    private Date dateObj;
    private List<DaylightRecord> dayLightRecord;

    //No argument constructor
    public PersistentObject() {
        dateObj = null;
        dayLightRecord = null;
    }

    //Full argument constructor
    public PersistentObject(Date dateObj, List<DaylightRecord> dayLightRecord) {
        this.dateObj = dateObj;
        this.dayLightRecord = dayLightRecord;

    }

    public Date getDateObj() {
        return dateObj;
    }

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }

    public List<DaylightRecord> getDayLightRecord() {
        return dayLightRecord;
    }

    public void setDayLightRecord(List<DaylightRecord> dayLightRecord) {
        this.dayLightRecord = dayLightRecord;
    }

    //overridden toString method.
    @Override
    public String toString() {
        return "PersistentObject{" + "dateObj=" + dateObj + ", dayLightRecord=" + dayLightRecord + '}';
    }
}
