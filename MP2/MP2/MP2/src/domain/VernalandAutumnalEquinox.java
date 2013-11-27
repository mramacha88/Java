/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Comparator;

/**
 *
 * @author Meghashree M Ramachandra
 */
public class VernalandAutumnalEquinox implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        DataAnalyticsResult vae1 = (DataAnalyticsResult) o1;
        DataAnalyticsResult vae2 = (DataAnalyticsResult) o2;
        //Compare computationData of objects, If first is greater then retun 1
        //if second is greater then return -1, else return 0
        if (vae1.getComputationData() > vae2.getComputationData()) {
            return 1;
        }
        if (vae1.getComputationData() < vae2.getComputationData()) {
            return -1;
        } else {
            return 0;
        }
    }
}
