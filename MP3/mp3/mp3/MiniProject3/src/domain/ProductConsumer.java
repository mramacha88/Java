/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Meghashree M Ramachandra
 */
//This class encapsulates a region identifier (N, S, E and W) and a list to collect consumed Product objects.
//and consumes only their respective ProductMessage objects from the ProductProducer 
//and stores in internal list.
public class ProductConsumer implements Runnable {
    //to encapsulate region,store consumed products and producer data
    private char regionID;
    List<ProductMessage> consumedProducts = new ArrayList<ProductMessage>();   
    private ProductProducer producer;  
    private long sleepInterval;
    private boolean stop = true;

    //No-arg constructors.
    public ProductConsumer() {
    }

    //full arg constructors
    public ProductConsumer(char regionID, ProductProducer producer, long sleepInterval) {
        this.regionID = regionID;
        this.producer = producer;
        this.sleepInterval = sleepInterval;
    }

    @Override
    public void run() {
        while (stop) {
            //get message from producer if exists, based on the respective region.
            ProductMessage prodMessage = producer.getMessage(regionID);
            //on successful retrieveing of message
            if (prodMessage != null) {
                //Consumer consumes data and stores into consumedProducts List of ProductMessage class.
                consumedProducts.add(prodMessage);
                //To display the consumption data per ProductConsumer consumed.
                System.out.println("**********************************************************");
                System.out.println(prodMessage.getRegionID() + " region Consumer - Got Product");
                System.out.println("Product info of " + prodMessage.getRegionID()+":");
                System.out.println(prodMessage.toString());
                System.out.println("***********************************************************");                
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProductConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //set stopThread flag to stop executing the thread.
    public void stopThreadGracefully(boolean flag){
        stop = flag;
    }
    //to retrieve the consumed products.
    public List<ProductMessage> getConsumedProducts() {
        return consumedProducts;
    }
    
    public int getConsumedProductsListSize(){
        return consumedProducts.size();
    }
}
