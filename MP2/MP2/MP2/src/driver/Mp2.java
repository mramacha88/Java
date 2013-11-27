/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package driver;

import domain.PersistentObject;
import domain.util.Utilities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Meghashree M Ramachandra
 */
public class Mp2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Read CSV file from filesystem...
        System.out.println("Read CSV file from filesystem");
        List daylightRecords = Utilities.readCSVfile();       

        //Instance of PersistentObject with the current timestamp and the ArrayList object
        System.out.println("Creating the Instance of PersistentObject with the current timestamp and the ArrayList object.");
        PersistentObject pObj = new PersistentObject(new Date(), daylightRecords);

        //Serialize persistentobject pObj as a aggregate...
        System.out.println("Serialize records as a aggregate");
        Utilities.serializeListObject(pObj);

        //Wait 10 secs...
        Utilities.makeAppSleep();

        // Deserialize records back into app...
        System.out.println("Deserialize records back into app");
        PersistentObject pObject = Utilities.deserializeListObject();

        // Print time difference
        System.out.print("Time between serialization and deserialization is: " + Math.abs((pObj.getDateObj().getTime() - System.currentTimeMillis()) / 1000) + " secs." + "\n");

        // Create CSV file from records...
        System.out.println("Create CSV file from records");
        StringBuilder sb = Utilities.createCSVfile(pObject);

        // Write CSV file to filesystem...
        System.out.println("Write CSV file to filesystem");
        Utilities.writeCSVfile(sb);

          //write all daylightRecords to text file daylight-Record.txt
        Utilities.writeListtoFile(daylightRecords);       
        
        System.out.println("\nBy processing the given data :");
        //Compute Shortest day => winter solstice      
        List computationListForShortestDay = Utilities.findCorrespondingData(daylightRecords, "01/01/2013", "12/31/2013", true);
        ArrayList computationData = Utilities.retrieveDataFromComputation(computationListForShortestDay, daylightRecords);
        System.out.print("Shortest Day -> Winter Solstice\nFound ");
        Utilities.printData(computationData);
        System.out.println(" with same day length as the shortest days of Winter.\n" + computationData.get(0) + " is the first shortest day of Winter.\n");

        //Equal day and night => vernal equinox (Spring)
        List computationListEqualDayNightForSpring = Utilities.findRelevantData(daylightRecords, "03/01/2013", "06/30/2013");
        computationData = Utilities.retrieveDataFromComputation(computationListEqualDayNightForSpring, daylightRecords);
        System.out.print("Equal Day and night -> Vernal Equinox(Spring)\nNo days have equal day and night.\nFound ");
        Utilities.printData(computationData);
        System.out.println(" as the days closest to be an Equinox with difference of 1 min.\n" + computationData.get(0) + " is the first Vernal Equinox.\n");

        //Longest day => summer solstice
        List computationListForLongestDay = Utilities.findCorrespondingData(daylightRecords, "01/01/2013", "12/31/2013", false);
        computationData = Utilities.retrieveDataFromComputation(computationListForLongestDay, daylightRecords);        
        System.out.print("Longest Day -> Summer Solstice\nFound ");
        Utilities.printData(computationData);
        System.out.println(" with same day length as the longest days of Summer.\n" + computationData.get(0) + " is the first longest day of Summer.\n");
        
        //compute Equal day and night => autumnal equinox(Fall)
        List computationListEqualDayNightForFall = Utilities.findRelevantData(daylightRecords, "09/01/2013", "12/30/2013");
        System.out.println("Equal Day and night -> Autumnal Equinox(Fall)");
        computationData = Utilities.retrieveDataFromComputation(computationListEqualDayNightForFall, daylightRecords);
        System.out.println(computationData.get(0) + " is the day which have Equal day and night for Autumnal Equinox\n");

    }
}
