/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import domain.EmployeeRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Meghashree M Ramachandra All the database related operation functions
 * are present in this class.
 */
public class JDBCUtilities {
    //This function is called whenever the user wants to create an employee record
    //All the columns values are asked as input by the user. If the inputs are correct the database is updated 
    //with the values.

    public static boolean createRecord() {
        //columns present in the database
        String[] fields = {"employeeNumber", "employeeLastName", "employeeFirstName", "employeePhoneExtension", "employeeEmailID", "employeeOfficeCode", "employeeReportsTo", "employeeJobTitle"};
        String[] types = {"int", "String", "String", "String", "String", "String", "int", "String"};
        // to store the values entered by the user for respective columns.
        String[] fieldValues = new String[fields.length];
        //to determine success or failure of the record addition into database.
        boolean result = false;
        //iterate through the field values to accept all required column values.
        for (int i = 0; i < fields.length; i++) {
            UtilityFunctions.printOnConsoleandToFile("Please enter value for field " + fields[i] + " using data type as " + types[i] + ": ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                //read the data entered by the user.
                String dataEntered = br.readLine();
                UtilityFunctions.printToFile(dataEntered);
                // to check data entered is valid or else perform desired operation.
                if (dataEntered != null) {
                    if (dataEntered.trim().equals("")) {
                        fieldValues[i] = "-1";
                    } else {
                        fieldValues[i] = dataEntered;
                    }
                }
            }// catch if anu exceptiopn occurs while performing above operations.
            catch (IOException ex) {
                Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //If the required primary key employee number entered by the user is empty 
        //display the error message.
        if (fieldValues[0].equals("-1")) {
            UtilityFunctions.printOnConsoleandToFile("Error: Empty Employee number");
        } else {
            //connect to database.
            Connection conn = getConnection();
            try {
                //create employee record using the values entered by the user.
                EmployeeRecord rec = new EmployeeRecord(Integer.parseInt(fieldValues[0]), fieldValues[1], fieldValues[2], fieldValues[3], fieldValues[4], fieldValues[5], Integer.parseInt(fieldValues[6]), fieldValues[7]);

                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                //Executes the SQL statement. This is to check whether the employee record for the employee number
                //already exists. This check is done to avoid duplicate entry.
                ResultSet rs = stmt.executeQuery("SELECT * FROM Employees WHERE employeeNumber = " + rec.getEmployeeNumber());
                //if the record not exists then insert the record.
                if (!rs.next()) {
                    //Execute theSQL insert statement to insert the data into the database.
                    //On success set result true.
                    stmt.execute("INSERT INTO employees ("
                            + "employeeNumber, lastName, firstName, extension, "
                            + "email, officeCode, reportsTo, jobTitle)"
                            + " VALUES("
                            + ((rec.getEmployeeNumber() == -1) ? null : rec.getEmployeeNumber()) + ",'"
                            + ((rec.getEmployeeLastName().equals("-1")) ? null : rec.getEmployeeLastName()) + "','"
                            + ((rec.getEmployeeFirstName().equals("-1")) ? null : rec.getEmployeeFirstName()) + "','"
                            + ((rec.getEmployeePhoneExtention().equals("-1")) ? null : rec.getEmployeePhoneExtention()) + "','"
                            + ((rec.getEmployeeEmail().equals("-1")) ? null : rec.getEmployeeEmail()) + "','"
                            + ((rec.getEmployeeOfficeCode().equals("-1")) ? null : rec.getEmployeeOfficeCode()) + "',"
                            + ((rec.getEmployeeReportsTo() == -1) ? null : rec.getEmployeeReportsTo()) + ",'"
                            + ((rec.getEmployeeJobTitle().equals("-1")) ? null : rec.getEmployeeJobTitle()) + "');");
                    result = true;
                } else {
                    //If record exists display respective data.
                    UtilityFunctions.printOnConsoleandToFile("Record already exists.");
                }
            } catch (NumberFormatException nfe) {
                UtilityFunctions.printOnConsoleandToFile("Error in entered data type. Please follow the datatype while entering the inputs");
            } //catch exception if occurs while inserting the data.
            catch (SQLException ex) {
                UtilityFunctions.printOnConsoleandToFile("Error while inserting data into database.");
            } finally {
                try {
                    //Close the connection with database at last when the required operation is finished.
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    //This function is called whenever the user wants to retrieve an employee record using employee number ID
    //Emplyee number of the record is asked as input by the user. If the entered employee number is valid appropriate record  
    //is retrieved and displayed.
    public static EmployeeRecord retrieveRecord() {
        //connect to database. 
        Connection conn = null;
        EmployeeRecord rec = null;
        UtilityFunctions.printOnConsoleandToFile("To view record, please enter the Employee number: ");
        Scanner s = new Scanner(System.in);
        //Read the employee number input by the user.
        String id = s.next();
        UtilityFunctions.printToFile(id);
        boolean isValidEmpId = false;
        try {
            //check for valid data tyoe of employee number.
            Integer.parseInt(id);
            isValidEmpId = true;
        } catch (NumberFormatException nfe) {
            UtilityFunctions.printOnConsoleandToFile("Error in entered Employee number data type.");
        }
        if (isValidEmpId) {
            try {
                //connect to database.
                conn = getConnection();
                if (conn != null) {
                    //creates a statement object by sending SQL statements to the database.
                    Statement stmt = conn.createStatement();
                    //Executes the SQL statement to retrieve the respective employee record using employee number ID. 
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Employees WHERE employeeNumber = " + id);
                    // Retrieved record values are wrapped into an record object
                    while (rs.next()) {
                        int employeeNumber = rs.getInt("employeeNumber");
                        String employeeLastName = rs.getString("lastName");
                        String employeeFirstName = rs.getString("firstName");
                        String employeeExtensionNumber = rs.getString("extension");
                        String employeeEmailID = rs.getString("email");
                        String employeeOfficeCode = rs.getString("officeCode");
                        int employeeReportsTo = rs.getInt("reportsTo");
                        String employeeJobTitle = rs.getString("jobTitle");
                        rec = new EmployeeRecord(employeeNumber, employeeLastName, employeeFirstName, employeeExtensionNumber,
                                employeeEmailID, employeeOfficeCode, employeeReportsTo, employeeJobTitle);
                    }
                }
            }//catch exception if occurs while retrieving the data.
            catch (SQLException ex) {
                //Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    //Close the connection with database at last when the required operation is finished.
                    if (conn != null) {
                        conn.close();
                    }
                }//catches exception if error occurs while closing the database connection. 
                catch (SQLException ex) {
                    // Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //retrieved record value is returned
        return rec;
    }

    //This function is called whenever the user wants to retrieve all the employee records from the database.
    public static List retrieveAllRecords() {
        //connect to database.
        Connection conn = getConnection();
        List<EmployeeRecord> recList = null;
        try {
            recList = new ArrayList();
            //creates a statement object by sending SQL statements to the database.
            Statement stmt = conn.createStatement();
            //Executes the SQL statement to retrieve the all employee records present in the database.
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employees;");
            // Retrieved record values are wrapped into an record objects and stored in the arraylist.
            while (rs.next()) {
                int employeeNumber = rs.getInt("employeeNumber");
                String employeeLastName = rs.getString("lastName");
                String employeeFirstName = rs.getString("firstName");
                String employeeExtensionNumber = rs.getString("extension");
                String employeeEmailID = rs.getString("email");
                String employeeOfficeCode = rs.getString("officeCode");
                int employeeReportsTo = rs.getInt("reportsTo");
                String employeeJobTitle = rs.getString("jobTitle");

                EmployeeRecord rec = new EmployeeRecord(employeeNumber, employeeLastName, employeeFirstName, employeeExtensionNumber,
                        employeeEmailID, employeeOfficeCode, employeeReportsTo, employeeJobTitle);
                recList.add(rec);
            }
        }//catch exception if occurs while retrieving the data.
        catch (SQLException ex) {
            Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //Close the connection with database at last when the required operation is finished.
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //retrieved record values arraylist is returned.
        return recList;
    }

    public static boolean updateRecord() {

        UtilityFunctions.printOnConsoleandToFile("Please enter the Employee number ID of which the Employee record need to be updated:  \n");
        Connection conn = null;
        Scanner s = new Scanner(System.in);
        //Read the employee number input by the user.
        String empIDentered = s.next();
        UtilityFunctions.printToFile(empIDentered);
        boolean result = false;
        boolean isValidEmpId = false;

        try {
            //check for valid data type of employee number.
            Integer.parseInt(empIDentered);
            isValidEmpId = true;
        } catch (NumberFormatException nfe) {
            UtilityFunctions.printOnConsoleandToFile("Error in entered Employee number data type.");
        }
        //If valid data typr continue.
        if (isValidEmpId) {
            try {
                 //connect to database.
                conn = getConnection();
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                //Executes the SQL statement. This is to check whether the employee record for the employee number
                //already exists. This check is done to avoid duplicate entry.
                ResultSet rs = stmt.executeQuery("SELECT * FROM Employees WHERE employeeNumber = " + empIDentered);
                if (rs.next()) {
                    //columns present in the database
                    String[] fields = {"employeeNumber", "employeeLastName", "employeeFirstName", "employeePhoneExtension", "employeeEmailID", "employeeOfficeCode", "employeeReportsTo", "employeeJobTitle"};
                    // columns name present in the database.
                    String[] databaseFields = {"employeeNumber", "lastName", "firstName", "extension", "email", "officeCode", "reportsTo", "jobTitle"};
                    String[] types = {"int", "String", "String", "String", "String", "String", "int", "String"};
                    String[] fieldValues = new String[fields.length];
                    fieldValues[0] = empIDentered;
                    int noCount, defaultCount;
                    noCount = defaultCount = 0;
                     //iterate through the field values to accept the required column values to update record.
                    for (int i = 1; i < fields.length; i++) {
                        UtilityFunctions.printOnConsoleandToFile("Want to update " + fields[i] + " [y/n]?");
                        s = new Scanner(System.in);
                        String decisionEntered = s.next();
                        UtilityFunctions.printToFile(decisionEntered);
                        switch (decisionEntered) {
                            case "y":
                            case "Y":
                                UtilityFunctions.printOnConsoleandToFile("Please enter value for field " + fields[i] + " using data type as " + types[i] + ": ");
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                try {
                                    //read the data entered by the user.
                                    String dataEntered = br.readLine();
                                    UtilityFunctions.printToFile(dataEntered);
                                    fieldValues[i] = dataEntered.trim();
                                }// catch if anu exceptiopn occurs while performing above operations.
                                catch (IOException ex) {
                                    Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;

                            case "n":
                            case "N":
                                noCount++;
                                if (i == 6) {
                                    fieldValues[i] = Integer.toString(rs.getInt(databaseFields[i]));
                                } else {
                                    fieldValues[i] = rs.getString(databaseFields[i]);
                                }
                                break;
                            default:
                                defaultCount++;
                                if (i == 6) {
                                    fieldValues[i] = Integer.toString(rs.getInt(databaseFields[i]));
                                } else {
                                    fieldValues[i] = rs.getString(databaseFields[i]);
                                }
                                break;
                        }
                    }
                    //check if values are entered to any field to update if yes continue.
                    if (noCount != 7 && defaultCount != 7 && (noCount + defaultCount) != 7) {
                        //create employee record using the values entered by the user.
                        EmployeeRecord rec = new EmployeeRecord(Integer.parseInt(fieldValues[0]), fieldValues[1], fieldValues[2], fieldValues[3], fieldValues[4], fieldValues[5], Integer.parseInt(fieldValues[6]), fieldValues[7]);
                        // update the respective employee record with the changed data.
                        stmt.execute("UPDATE employees SET "
                                + databaseFields[1] + "='" + rec.getEmployeeLastName() + "', "
                                + databaseFields[2] + "='" + rec.getEmployeeFirstName() + "', "
                                + databaseFields[3] + "='" + rec.getEmployeePhoneExtention() + "', "
                                + databaseFields[4] + "='" + rec.getEmployeeEmail() + "', "
                                + databaseFields[5] + "='" + rec.getEmployeeOfficeCode() + "', "
                                + databaseFields[6] + "=" + rec.getEmployeeReportsTo() + ", "
                                + databaseFields[7] + "='" + rec.getEmployeeJobTitle() + "'"
                                + "WHERE " + databaseFields[0] + "=" + fieldValues[0] + ";");
                        result = true;
                    } else {
                        //if no data entered for any columns display error message.
                        UtilityFunctions.printOnConsoleandToFile("No data was entered to any columns to update the employee number " + empIDentered + " record.");
                    }
                } else {
                    //If record exists display respective data.
                    UtilityFunctions.printOnConsoleandToFile("Record for the employee number " + empIDentered + " does not exists.");
                }
            } catch (NumberFormatException nfe) {
                UtilityFunctions.printOnConsoleandToFile("Error in entered data type. Please follow the datatype while entering the inputs");
            } //catch exception if occurs while inserting the data.
            catch (SQLException ex) {
                UtilityFunctions.printOnConsoleandToFile("Error while inserting data into database.");
            } finally {
                try {
                    //Close the connection with database at last when the required operation is finished.
                    if (conn != null) {
                        conn.close();
                    }
                }//catches exception if error occurs while closing the database connection. 
                catch (SQLException ex) {
                    Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    //This function is called whenever the user wants to delete an employee record.
    //Emplyee number of the record is asked as input by the user. If the entered employee number is valid appropriate record  
    //is deleted .
    public static boolean deleteRecord() {
        //connect to database.
        Connection conn = null;
        UtilityFunctions.printOnConsoleandToFile("To delete record, please enter the employee number: ");
        Scanner s = new Scanner(System.in);
        //Read the employee number input by the user.
        String id = s.next();
        UtilityFunctions.printToFile(id);
        boolean result = false;
        boolean isValidEmpId = false;
        try {
            Integer.parseInt(id);
            isValidEmpId = true;
        } catch (NumberFormatException nfe) {
            UtilityFunctions.printOnConsoleandToFile("Error in entered Employee number data type.");
        }
        if (isValidEmpId) {
            try {
                conn = getConnection();
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                //Executes the SQL statement. This is to check whether the employee record for the employee number
                //exists. This check is done to avoid deleting the data which is not present.
                ResultSet rs = stmt.executeQuery("SELECT * FROM Employees WHERE employeeNumber = " + id);
                //After checking data whether present or not do the appropriate task.
                if (rs.next()) {
                    //Executes the SQL statement to delete the respective employee record using employee number ID. 
                    stmt.execute("DELETE FROM Employees WHERE employeeNumber = " + id);
                    result = true;
                } else {
                    UtilityFunctions.printOnConsoleandToFile("Record for Employee number " + id + " not exists.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
            //if the delete is done enter into the loop.
            if (result) {
                ResultSet rs = null;
                try {
                    //creates a statement object by sending SQL statements to the database.
                    Statement stmt = conn.createStatement();
                    //Executes the SQL statement. This is to check whether the data is deleted successfully or not.
                    rs = stmt.executeQuery("SELECT * FROM Employees WHERE employeeNumber = " + id);
                }//catches exception if error occurs while executing the query.
                catch (SQLException ex) {
                    Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    //if data is deleted from database then set boolean result to true else false
                    if (!rs.next()) {
                        result = true;
                    } else {
                        result = false;
                    }
                }//catches exception while reading data from the result returned from the database query . 
                catch (SQLException ex) {
                    Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        //Close the connection with database at last when the required operation is finished.
                        if (conn != null) {
                            conn.close();
                        }
                    }//catches exception if error occurs while closing the database connection.  
                    catch (SQLException ex) {
                        Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                try {
                    //Close the connection with database at last when the required operation is finished.
                    if (conn != null) {
                        conn.close();
                    }
                }//catches exception if error occurs while closing the database connection. 
                catch (SQLException ex) {
                    Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }// returns boolean result.
        return result;
    }

    //This function is called to connect to the database itm411db.
    private static Connection getConnection() {
        Connection conn = null;
        try {
            //Establishes a connection to the given database URL. 
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/itm411DB", "root", "admin");
        }//catches exception if error in connection to database occurs.
        catch (SQLException ex) {
            UtilityFunctions.printOnConsoleandToFile("Error:Connection to database couldn't be established");
            UtilityFunctions.printOnConsoleandToFile("Please check the database connection and try again.");
            System.exit(0);
            // Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
