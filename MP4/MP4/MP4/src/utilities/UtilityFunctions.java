package utilities;

import domain.EmployeeRecord;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Meghashree M Ramachandra
 *
 */
public class UtilityFunctions {

    private static FileOutputStream fos = null;
    static boolean initialized = true;

    //displays the options at the console to perform database operations.
    public static void displayMenu() {
        // Selected option will invoke an assitive method outside of this driver class...
        printOnConsoleandToFile("Please enter choice for CRUD operations from the following options: ");
        printOnConsoleandToFile("c - Create an Employee record.\n"
                + "r - Retrieve an Employee record by ID.\n"
                + "R - Retrieve all Employee records.\n"
                + "u - Update an Employee record by ID.\n"
                + "d - Delete an Employee record by ID.\n"
                + "q - Quit the application\n");
    }

    //reads the input out of options selected by the user and perform respective operations
    //Key c option - to Create an Employee record, Key r option - Retrieve an Employee record by ID
    //Key R option - Retrieve all Employee records, Key u option - Update an Employee record by ID
    //Key d option - Delete an Employee record by ID,Key q option - Quit the application
    public static void getUserSelection(Scanner sc) {
        if (sc.hasNext()) {
            String optionSelected = sc.next();
            switch (optionSelected) {
                //To create and Insert the employee record into database
                case "c":
                    printToFile(optionSelected + "\n");
                    printOnConsoleandToFile("Enter the following credentials to create Employee record :  \n");
                    //Invokes function that creats and inserts employee record into database.
                    boolean createResult = JDBCUtilities.createRecord();
                    //Print success message- when employee record is created
                    //or failure message - w.hen employee record not created respectively
                    if (createResult) {
                        printOnConsoleandToFile("Employee Record Created. \n");
                    } else {
                        printOnConsoleandToFile("Employee Record not Created. \n");
                    }
                    break;
                //To retrieve an respective employee record based on the ID.
                // Employee number ID should be the input from user
                case "r":
                    printToFile(optionSelected + "\n");
                    //Invokes function that takes employee number input from the user and
                    //retrieves the same from database.
                    EmployeeRecord record = JDBCUtilities.retrieveRecord();
                    //Prints retrieved employee record data on success
                    //else prints failure message
                    if (record != null) {
                        printOnConsoleandToFile(record.toString() + "\n");
                    } else {
                        printOnConsoleandToFile("Record does not exists for the above Employee Number. \n");
                    }
                    break;
                //To retrieve all employee records from database.
                case "R":
                    printToFile(optionSelected + "\n");
                    printOnConsoleandToFile("Retrieving all Employee records :  ");
                    //Invokes function that retrieves all employee records from database.
                    List<EmployeeRecord> records = JDBCUtilities.retrieveAllRecords();
                    //check if records are present or not.
                    if (records.size() != 0) {
                        //Displays all employee records retrieved.
                        for (EmployeeRecord r : records) {
                            printOnConsoleandToFile(r.toString());
                        }
                    } else {
                        printOnConsoleandToFile("No employee records present in employees table in database.");
                    }
                    printOnConsoleandToFile("\n");
                    break;
                //To Update the required tuple of employee record in database.
                //Correct SQL Update statement is required as the input by the user.
                case "u":
                    printToFile(optionSelected + "\n");

                    //Invokes function update record which accepts SQL update statement input from user
                    //updates the database and displays record updated message upon succes else
                    //record not updated message on failure
                    if (JDBCUtilities.updateRecord()) {
                        printOnConsoleandToFile("Record was updated...\n");
                    } else {
                        printOnConsoleandToFile("Record was not updated...\n");
                    }
                    break;
                //To delate an employee record from database.
                // Employee number whose employee record to be deleted is accepted as input from the user.
                //Then based on that employee number respective employee record is deleted.
                case "d":
                    printToFile(optionSelected + "\n");
                    //Invoeks delete function that accepts employee number from the user and
                    //deletes the respective employee record from database and displays respective message
                    // based on success and failure.
                    if (JDBCUtilities.deleteRecord()) {
                        printOnConsoleandToFile("Record was deleted...\n");
                    } else {
                        printOnConsoleandToFile("Record was not deleted...\n");
                    }

                    break;
                //option to exit from the application
                case "q":
                    printToFile(optionSelected + "\n");
                    printOnConsoleandToFile("Exiting.... :  \n");
                    System.exit(0);
                    break;
            }
            sc.reset();
        }
    }

    //Function to display and write respective message onto console and to a file.
    public static void printOnConsoleandToFile(String displayData) {
        //Display the data on console
        System.out.println(displayData);
        //execute the statement and catch exception if error occurs.
        try {
            if (initialized) {
                //creating the pointer to mp4out.txt file
                fos = new FileOutputStream("mp4out.txt", false);
                initialized = false;
            }
            //write the data to file name mp4out.txt
            fos.write(displayData.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(UtilityFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Function to write respective message to file mp4out.txt
    public static void printToFile(String displayData) {
        try {
            //writes data to file name mp4out.txt
            fos.write(displayData.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(UtilityFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
