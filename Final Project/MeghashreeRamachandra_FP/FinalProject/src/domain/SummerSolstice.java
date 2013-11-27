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
//comparator class which will be used to retrieve longest day in summer.
public class SummerSolstice implements Comparator {

    public int compare(Object o1, Object o2) {
        DataAnalyticsResult ss1 = (DataAnalyticsResult) o1;
        DataAnalyticsResult ss2 = (DataAnalyticsResult) o2;
        //Compare computationData of objects, If first is greater then retun -1
        //if second is greater then return 1, else return 0
        if (ss1.getComputationData() > ss2.getComputationData()) {
            return -1;
        }
        if (ss1.getComputationData() < ss2.getComputationData()) {
            return 1;
        } else {
            return 0;
        }

    }
}
