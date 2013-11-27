/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Date;
/**
 *
 * @author Meghashree M Ramachandra
 */
//This class encapsulates a Product object, the current timestamp and a 
//random region identifier of a ProductConsumer.
public class ProductMessage {
    //fields for product object, timestamp and region identifier
    private Product productObject;
    private Date timestamp;
    private char regionID;

    //No-arg constructor
    public ProductMessage() {
    }

    //Full-arg constructor
    public ProductMessage(Product product, Date timestamp, char regionID) {
        this.productObject = product;
        this.timestamp = timestamp;
        this.regionID = regionID;
    }

    //retrieve product object.
    public Product getProductObject() {
        return productObject;
    }

    //set product object
    public void setProduct(Product product) {
        this.productObject = product;
    }

    //retrieve timestamp
    public Date getTimestamp() {
        return timestamp;
    }

    //set timestamp
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    //get region ID.
    public char getRegionID() {
        return regionID;
    }

    //set region ID
    public void setRegionID(char regionID) {
        this.regionID = regionID;
    }

    //overridden toString()
    @Override
    public String toString() {
        return "ProductMessage{" + "product=" + productObject + ", timestamp=" + timestamp + ", regionID=" + regionID + '}';
    }
}
