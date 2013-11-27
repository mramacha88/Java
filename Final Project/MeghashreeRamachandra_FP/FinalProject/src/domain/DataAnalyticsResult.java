/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * @author Meghashree M Ramachandra
 * to store data-analytics results for solstice and equinox
 */
public class DataAnalyticsResult {

    private int arrayIndexData;
    private int computationData;

    //No argument constructor
    public DataAnalyticsResult() {
        arrayIndexData = 0;
        computationData = 0;
    }

    //full argument constructor
    public DataAnalyticsResult(int arrayIndexData, int computationData) {
        this.arrayIndexData = arrayIndexData;
        this.computationData = computationData;
    }

    //Accessors and Mutators
    public int getArrayIndexData() {
        return arrayIndexData;
    }

    public void setArrayIndexData(int arrayIndexData) {
        this.arrayIndexData = arrayIndexData;
    }

    public int getComputationData() {
        return computationData;
    }

    public void setComputationData(int computationData) {
        this.computationData = computationData;
    }

    @Override
    public String toString() {
        return "DataAnalyticsResult{" + "arrayIndexData=" + arrayIndexData + ", computationData=" + computationData + '}';
    }
}
