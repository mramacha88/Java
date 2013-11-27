/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package driver;

import domain.Product;
import domain.ProductConsumer;
import domain.ProductProducer;
import domain.util.Utilities;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Meghashree M Ramachandra
 */
public class MiniProject3 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Press RETURN to exit...");
        Utilities.deleteAllOutputFiles();
        //Read product_data.txt data to list
        List<Product> productList = Utilities.readTextDataIntoArray(); 
        
        System.out.println("Simulation Starting...");
        System.out.println("Creating and Starting the ProductProducer and ProductConsumer objects");
        
        //get the simulation starting time.
        Date startTime = new Date();
        
        //Creating producer object and starting the thread.
        ProductProducer producerOne = new ProductProducer(productList);
        Thread p = new Thread(producerOne);
        p.setDaemon(true);
        p.start();
       
        //Creating consumer object with region N.
        ProductConsumer NConsumer = new ProductConsumer('N', producerOne, 600L);
        Thread c1 = new Thread(NConsumer);
        c1.setDaemon(true);
        c1.start();
        
        //Creating consumer object with region S.
        ProductConsumer SConsumer = new ProductConsumer('S', producerOne, 700L);
        Thread c2 = new Thread(SConsumer);
        c2.setDaemon(true);
        c2.start();
        
        //Creating consumer object with region E.
        ProductConsumer EConsumer = new ProductConsumer('E', producerOne, 800L);
        Thread c3 = new Thread(EConsumer);
        c3.setDaemon(true);
        c3.start();
       
        //Creating consumer object with region W.
        ProductConsumer WConsumer = new ProductConsumer('W', producerOne, 900L);
        Thread c4 = new Thread(WConsumer);
        c4.setDaemon(true);
        c4.start();
        
        //Print all the data and exit when return key is pressed.
        Utilities.exitAndPrintRequiredData(producerOne, NConsumer, SConsumer, EConsumer, WConsumer, startTime);
       
    }
    
   
    
}
