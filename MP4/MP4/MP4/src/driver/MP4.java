package driver;

import java.util.Scanner;
import utilities.UtilityFunctions;

/**
 * @author Meghashree M Ramachandra
 * 
 * Driver class that displays command menu which can be selectable by the user 
 * to perform CRUD operations (create, retrieve, update and delete) related to database.
 */
public class MP4 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            //Continue thorugh the loop until application exits.
            
            while (true) {
                //Create recurring command line menu...
                //Display menu to perform CRUD operation from which
                //user has to select an option to perform required operation...
                UtilityFunctions.displayMenu();
                //Read the option selected by the user and perform respective operation
                // such as to create, retrieve, update delete data from database...
                UtilityFunctions.getUserSelection(new Scanner(System.in));
            }
        

    }
}
