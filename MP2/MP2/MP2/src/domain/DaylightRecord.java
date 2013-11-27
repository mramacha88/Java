/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Meghashree M Ramachandra
 */
public class DaylightRecord extends Record implements Serializable {

    //No argument Constructor
    public DaylightRecord() {
        super();
    }

    //full argument constructor
    public DaylightRecord(String day, Date sunRise, Date sunSet) {
        super(day, sunRise, sunSet);
    }

    @Override
    public String toString() {
        return "DayLightRecord{" + super.toString() + '}';
    }
}
