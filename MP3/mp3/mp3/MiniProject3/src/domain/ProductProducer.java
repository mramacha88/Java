/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meghashree M Ramachandra
 */
//produces a Product for consumption:randomly selects a Product for product distribution to the ProductConsumers 
//(i.e. prepares a ProductMessage by populating a ProductMessage with a random Product, the current timestamp and 
//the ProductConsumer’s region)and pushes the ProductMessage object on its internal queue.
public class ProductProducer implements Runnable {
    //Fields for queue size, and queue list of ProductMessage and the list
    //of data of txt file.
    static final int MAXQUEUE = 5;
    private List<ProductMessage> productMessageList = new ArrayList();
    private List<Product> productList = new ArrayList();
    private int productsProducedCount = 0;
    private boolean stopThread = true;
    //No-arg constructor.
    public ProductProducer() {
    }

    //Full-arg constructor
    public ProductProducer(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public void run() {
        while (stopThread) {
            //get random product form the list which holds product_data.
            Product randomProduct = generateRandomProduct(productList);
            //Using randomproduct create producer message which is of ProductMessage
            //encapsulating randomproduct,timestamp, random region ID.
            ProductMessage pMessage = createProductMessage(randomProduct);
            //After creating producer message push the message into queue.
            putMessage(pMessage);
            try {
                //Make thread sleep.
                // Decrease sleep period to meet increasing request / Increase period to avoid over production
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProductProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //set stopThread flag to stop executing the thread.
    public void stopThreadGracefully(boolean flag){
        stopThread = flag;         
    }
    //Method to create random product from list which contains products data.
    private static Product generateRandomProduct(List<Product> productList) {
        Product randomProduct = null;
        //creates a new random number generator.
        Random rnd = new Random();
        //Return the next pseudorandom, uniformly distributed int value from
        //this random number generators sequence.
        int rndIndex = rnd.nextInt(productList.size());
        return randomProduct = productList.get(rndIndex);
    }

    private static ProductMessage createProductMessage(Product randomProduct) {
        ProductMessage productMessage = null;
        //array of region IDs.
        char[] regionIds = {'N', 'S', 'E', 'W'};
        //creates a new random number generator.
        Random rnd = new Random();
        //Return the next pseudorandom, uniformly distributed int value from
        //this random number generators sequence.
        int rndIndex = rnd.nextInt(4);
        return productMessage = new ProductMessage(randomProduct, new Date(), regionIds[rndIndex]);
    }

    private synchronized void putMessage(ProductMessage pMessage) {
        //check queue size : if reached greater or equal to MAXQUEUE 
        //wait until data to read from queue.
        while (productMessageList.size() >= MAXQUEUE) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ProductProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Otherwise add generated ProductMessage into queue.
        productMessageList.add(pMessage);
        productsProducedCount++;
        System.out.println("Product Produced for " + pMessage.getRegionID() + " region.\nQueue has " + 
                            productMessageList.size() + " product(s).");
        notify();
    }

    //retrieve produced ProductMessages from queue(List).
    public int getProductProducedCount() {
        return productsProducedCount;
    }

    //method called by consumer : To get respective message from queue(List)
    //based on regionID.
    public synchronized ProductMessage getMessage(char regionID) {
        ProductMessage pmessage = null;
        //If queue(List) is empty, wait until data is pused into list.
        while (productMessageList.size() == 0) {
            try {
                notify();
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ProductProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Read and remove ProductMessage from queue(List)
        //if its equals to respective regionID.
        if (regionID == productMessageList.get(0).getRegionID()) {
            pmessage = ((ProductMessage) productMessageList.remove(0));
        }
        notify();
        return pmessage;
    }
}
