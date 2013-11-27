/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.util;

import domain.Product;
import domain.ProductConsumer;
import domain.ProductMessage;
import domain.ProductProducer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meghashree M Ramachandra
 */
public class Utilities {

    //read text file data into list for further computation.
    public static List<Product> readTextDataIntoArray() {
        //Fields required are declared and initialized.
        String line;
        String DESCRIPTION = "";
        String[] splitData = null;
        List<Product> productList = new ArrayList();
        try {
            // pointer to read data from text file using buffered streams
            BufferedReader br = new BufferedReader(new FileReader("data/PRODUCT_data.txt"));
            //read line until the EOF and do required task.
            while ((line = br.readLine()) != null) {
                try {
                    //after reading single line, split the data and storing into an array
                    splitData = line.split("\\s+");
                    //Since the description sentence is split and stored in different indexes
                    //using '\\s+' its aggregated to get the complete description.
                    DESCRIPTION = "";
                    for (int i = 7; i < splitData.length; i++) {
                        DESCRIPTION = DESCRIPTION + " " + splitData[i];
                    }
                    //Product object created using data stored in splitData list which is the result of reading
                    //data from text file and stored into List of Product class.
                    productList.add(new Product(Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1]), splitData[2], Float.parseFloat(splitData[3]),
                            Integer.parseInt(splitData[4]), Float.parseFloat(splitData[5]), splitData[6], DESCRIPTION));
                } catch (Exception ex) {
                    //Do nothing, but continue
                }
            }
        }//Handle the error if found while reading file  
        catch (FileNotFoundException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }

    public static void exitAndPrintRequiredData(ProductProducer Producer, ProductConsumer NConsumer, ProductConsumer SConsumer, ProductConsumer EConsumer, ProductConsumer WConsumer, Date startTime) {
        //Pointer to read the return input key data when pressed.
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            //reads the return key pressed to stop the simulation
            in.read();
            System.out.println("Simulation Halted....\n");
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //To avoid all running threads to change the respective Producer and consumer threads
            Producer.stopThreadGracefully(false);
            NConsumer.stopThreadGracefully(false);
            SConsumer.stopThreadGracefully(false);
            EConsumer.stopThreadGracefully(false);
            WConsumer.stopThreadGracefully(false);
            //get the end time of simulation.
            Date endTime = new Date();
            long diff = endTime.getTime() - startTime.getTime();
            //Compute hours, minutes, seconds  of the total simulation time executed
            long Hours = diff / (60 * 60 * 1000);
            long Minutes = diff / (60 * 1000) % 60;
            long Seconds = diff / 1000 % 60;
            //To display all Product objects per ProductConsumer on completion.
            System.out.println("Displaying all product objects per product consumer\n");
            printAllConsumedObjects(NConsumer.getConsumedProducts());
            printAllConsumedObjects(SConsumer.getConsumedProducts());
            printAllConsumedObjects(EConsumer.getConsumedProducts());
            printAllConsumedObjects(WConsumer.getConsumedProducts());
            //To write all Product objects per ProductConsumer consumed to a file based on region. 
            System.out.println("\nWriting respective product objects per product consumer based on region to a file.");
            writeConsumedDataToFile('N', NConsumer.getConsumedProducts());
            writeConsumedDataToFile('S', SConsumer.getConsumedProducts());
            writeConsumedDataToFile('E', EConsumer.getConsumedProducts());
            writeConsumedDataToFile('W', WConsumer.getConsumedProducts());
            //To display the total elapsedtime of the simulation.
            System.out.println("Total Elapsed Time :   " + Hours + "Hours " + Minutes + "Minutes " + Seconds + "Seconds.\n");
            System.out.println("Total Products Produced : " + Producer.getProductProducedCount());
            System.out.println("Consumed products count in 'N' region : " + NConsumer.getConsumedProductsListSize()
                    + "\nConsumed products count in 'S' region : " + SConsumer.getConsumedProductsListSize()
                    + "\nConsumed products count in 'E' region : " + EConsumer.getConsumedProductsListSize()
                    + "\nConsumed products count in 'W' region : " + WConsumer.getConsumedProductsListSize()
                    + "\nTotal Products Consumed By All Regions(N, S, E, W) : " + (NConsumer.getConsumedProductsListSize() + SConsumer.getConsumedProductsListSize()
                    + EConsumer.getConsumedProductsListSize() + WConsumer.getConsumedProductsListSize()));
            System.exit(0);
        }
    }

    //displays all consumed data by consumers(ie. every region)
    private static void printAllConsumedObjects(List<ProductMessage> consumedProducts) {
        //check if there is products or not
        if (consumedProducts != null) {
            for (ProductMessage pm : consumedProducts) {
                System.out.println(pm);
            }
        }
    }

    public static void writeConsumedDataToFile(char dir, List<ProductMessage> pm) {
        try {
            if (pm.size() != 0) {
                //Pointer to create file.
                File file1 = new File("output/PConsumerBasedOnRegions.txt");
                File file2 = new File("output/" + dir + ".csv");
                //if file not exists, then create it
                if (!file1.exists()) {
                    file1.createNewFile();
                }
                //if file doesnt exists, then create it
                if (!file2.exists()) {
                    file2.createNewFile();
                }

                //Create a buffered file stream
                FileWriter fstream1 = new FileWriter("output/PConsumerBasedOnRegions.txt", true);
                BufferedWriter bufferWriter1 = new BufferedWriter(fstream1);
                FileWriter fstream2 = new FileWriter("output/" + dir + ".csv", true);
                BufferedWriter bufferWriter2 = new BufferedWriter(fstream2);

                //Add regions header into bufferwriter1
                bufferWriter1.write("Products for " + dir + " region : \n");
                bufferWriter2.write("Products for " + dir + " region : \n");
                for (ProductMessage pmsg : pm) {
                    // Write the contents of the product queue to the buffers         
                    bufferWriter1.write(pmsg.toString() + (char) (10));
                    bufferWriter2.write(pmsg.toString() + (char) (10));
                }
                bufferWriter1.close();
                bufferWriter2.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //delete all exiciting files in the output folder to copy new data

    public static void deleteAllOutputFiles() {
        File f = new File("./output/");
        //deletes all files in the output folder if present.
        for (File sf : f.listFiles()) {
            sf.delete();
        }
    }
}
