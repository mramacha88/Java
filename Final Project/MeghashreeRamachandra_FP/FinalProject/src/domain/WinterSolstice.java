/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Comparator;

/**
 * @author Meghashree M Ramachandra
 */
//comparator class which will be used to retrieve shortest day in winter.
public class WinterSolstice implements Comparator {

    public int compare(Object o1, Object o2) {
        DataAnalyticsResult ws1 = (DataAnalyticsResult) o1;
        DataAnalyticsResult ws2 = (DataAnalyticsResult) o2;
        //Compare computationData of objects, If first is greater then return 1
        //if second is greater then return -1, else return 0
        if (ws1.getComputationData() > ws2.getComputationData()) {
            return 1;
        }
        if (ws1.getComputationData() < ws2.getComputationData()) {
            return -1;
        } else {
            return 0;
        }

    }
}
