/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import domain.DaylightRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * @author Meghashree M Ramachandra All the database related operation functions
 * are present in this class.
 */
public class JDBCUtilities {
    //This function is called whenever the user wants to create an daylight record
    //All the input of the user is passed to this function to create the record. 
    //If the inputs are correct the record is inserted with the values.

    public static String createRecord(List recordValues) {
        DateFormat df = new SimpleDateFormat("HHmm");
        String result = "";
        //connect to database.
        Connection conn = getConnection();
        //checks if connection is established.
        if (conn != null) {
            try {
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                //Executes the SQL statement. This is to check whether the daylight record for the day and month value
                //already exists. This check is done to avoid duplicate entry.
                ResultSet rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day=" + Integer.parseInt(recordValues.get(0).toString()) + " and month='" + recordValues.get(1) + "';");
                //if the record not exists then insert the record.
                if (!rs.next()) {
                    DaylightRecord rec = null;
                    try {
                        //create the respective daylight record checking for sunrise and sunset time.
                        if (recordValues.get(2).toString().trim().equals("") && recordValues.get(3).toString().trim().equals("")) {
                            rec = new DaylightRecord(recordValues.get(0).toString(), recordValues.get(1).toString(), df.parse("0000"), df.parse("0000"));
                        } else if (!recordValues.get(2).toString().trim().equals("") && recordValues.get(3).toString().trim().equals("")) {
                            rec = new DaylightRecord(recordValues.get(0).toString(), recordValues.get(1).toString(), df.parse(recordValues.get(2).toString()), df.parse("0000"));
                        } else if (recordValues.get(2).toString().trim().equals("") && !recordValues.get(3).toString().trim().equals("")) {
                            rec = new DaylightRecord(recordValues.get(0).toString(), recordValues.get(1).toString(), df.parse("0000"), df.parse(recordValues.get(3).toString()));
                        } else {
                            rec = new DaylightRecord(recordValues.get(0).toString(), recordValues.get(1).toString(), df.parse(recordValues.get(2).toString()), df.parse(recordValues.get(3).toString()));
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Execute theSQL insert statement to insert the data into the database.
                    //On success set result message.
                    stmt.execute("INSERT INTO DAYLIGHTRECORD ("
                            + "day, month, sunRise, sunSet)"
                            + " VALUES("
                            + Integer.parseInt(rec.getDay()) + ",'"
                            + rec.getMonth() + "','"
                            + df.format(rec.getSunRise()) + "','"
                            + df.format(rec.getSunSet()) + "');");
                    result = "Record created";
                } else {
                    //If record exists display respective data.
                    result = "Record already exists.";
                }
            } catch (NumberFormatException nfe) {
                result = "Error in entered data type. Please follow the datatype while entering the inputs";
            } //catch exception if occurs while inserting the data.
            catch (SQLException ex) {
                result = "Error while inserting data into database.";
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
        } else {//if any error occurs while connecting to database.
            result = "Error while connecting to database. Please check the MySQL server connection.";
        }
        //returns respective message.
        return result;
    }

    //This function is called whenever the user wants to retrieve an daylight record for a day or month or for both.
    //day value and month value taken as input from the user is passed to this function. If the entered day and month value
    //is valid appropriate record is retrieved and displayed.
    public static List retrieveRecordForDay(String dayValue, String monthValue) {
        DateFormat df = new SimpleDateFormat("HHmm");
        //connect to database. 
        Connection conn = null;
        //to store the list of daylight records.
        List<DaylightRecord> recList = null;
        try {
            //connect to database.
            conn = getConnection();
            if (conn != null) {
                recList = new ArrayList();
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                ResultSet rs = null;
                if (monthValue.equals("None")) {
                    //Executes the SQL statement to retrieve the respective daylight record. 
                    rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day = " + Integer.parseInt(dayValue));
                } else {
                    //Executes the SQL statement to retrieve the respective daylight record. 
                    rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day =" + Integer.parseInt(dayValue) + " and month='" + monthValue + "';");
                }
                // Retrieved record values are wrapped into an record object
                while (rs.next()) {
                    //get the value from the result set obtained from database.
                    String day = Integer.toString(rs.getInt("day"));
                    String month = rs.getString("month");
                    String sunRise = rs.getString("sunRise");
                    String sunSet = rs.getString("sunSet");
                    try {
                        //create record and add into the list.
                        DaylightRecord rec = new DaylightRecord(day, month, df.parse(sunRise), df.parse(sunSet));
                        recList.add(rec);
                    } catch (ParseException ex) {
                        Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } //catch exception if occurs while retrieving the data.
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
        //retrieved record value is returned
        return recList;
    }

    //This function is called whenever the user wants to retrieve daylight record between two day or month or for both range values.
    //day value and month value taken as input from the user is passed to this function. If the entered day and month value
    //is valid appropriate record is retrieved and displayed.    
    public static List retrieveRecordBetweenDayRange(String beginDayValue, String endDayValue, String monthValue) {
        DateFormat df = new SimpleDateFormat("HHmm");
        //connect to database. 
        Connection conn = null;
        //to store the list of daylight records.
        List<DaylightRecord> recList = null;
        try {
            //connect to database.
            conn = getConnection();
            if (conn != null) {
                recList = new ArrayList();
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                ResultSet rs = null;
                if (monthValue.equals("None")) {
                    //Executes the SQL statement to retrieve the respective daylight record. 
                    rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day >= " + Integer.parseInt(beginDayValue) + " and day <= " + Integer.parseInt(endDayValue) + ";");
                } else {
                    //Executes the SQL statement to retrieve the respective daylight record.  
                    rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day >= " + Integer.parseInt(beginDayValue) + " and day <= " + Integer.parseInt(endDayValue) + " and month='" + monthValue + "';");
                }
                // Retrieved record values are wrapped into an record object
                while (rs.next()) {
                    //get the value from the result set obtained from database.
                    String day = Integer.toString(rs.getInt("day"));
                    String month = rs.getString("month");
                    String sunRise = rs.getString("sunRise");
                    String sunSet = rs.getString("sunSet");
                    try {
                        //create record and add into the list.
                        DaylightRecord rec = new DaylightRecord(day, month, df.parse(sunRise), df.parse(sunSet));
                        recList.add(rec);
                    } catch (ParseException ex) {
                        Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } //catch exception if occurs while retrieving the data.
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
        //retrieved record value is returned
        return recList;
    }

    //This function is called whenever the user wants to retrieve all the daylight records from the database.
    public static List retrieveAllRecords() {
        DateFormat df = new SimpleDateFormat("HHmm");
        //connect to database.
        Connection conn = getConnection();
        List<DaylightRecord> recList = null;
        if (conn != null) {
            try {
                recList = new ArrayList();
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                //Executes the SQL statement to retrieve the all daylight records present in the database.
                ResultSet rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD;");
                // Retrieved record values are wrapped into an record objects and stored in the arraylist.
                while (rs.next()) {
                    //get the value from the result set obtained from database.
                    String day = Integer.toString(rs.getInt("day"));
                    String month = rs.getString("month");
                    String sunRise = rs.getString("sunRise");
                    String sunSet = rs.getString("sunSet");
                    DaylightRecord rec;
                    try {
                        //create record and add into the list.
                        rec = new DaylightRecord(day, month, df.parse(sunRise), df.parse(sunSet));
                        recList.add(rec);
                    } catch (ParseException ex) {
                        Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
        }
        //retrieved record values arraylist is returned.
        return recList;
    }

    //This function is called whenever the user wants to update an daylight record
    //All the input of the user is passed to this function to update the record. 
    //If the inputs are correct the database is updated with the values.
    public static String updateRecord(List updateRecordValues) {
        DateFormat df = new SimpleDateFormat("HHmm");
        Connection conn = null;
        String result = "";
        try {
            //connect to database.
            conn = getConnection();
            if (conn != null) {
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                //Executes the SQL statement. This is to check whether the daylight record for the day and month value
                //already exists. 
                ResultSet rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day=" + Integer.parseInt(updateRecordValues.get(0).toString()) + " and month='" + updateRecordValues.get(1) + "';");
                if (rs.next()) {
                    //updates daylight record using the values entered by the user.
                    DaylightRecord rec = null;
                    try {
                        //create the respective daylight record checking for sunrise and sunset time.
                        if (updateRecordValues.get(2).toString().trim().equals("") && updateRecordValues.get(3).toString().trim().equals("")) {
                            rec = new DaylightRecord(updateRecordValues.get(0).toString(), updateRecordValues.get(1).toString(), df.parse("0000"), df.parse("0000"));
                        } else if (!updateRecordValues.get(2).toString().trim().equals("") && updateRecordValues.get(3).toString().trim().equals("")) {
                            rec = new DaylightRecord(updateRecordValues.get(0).toString(), updateRecordValues.get(1).toString(), df.parse(updateRecordValues.get(2).toString()), df.parse("0000"));
                        } else if (updateRecordValues.get(2).toString().trim().equals("") && !updateRecordValues.get(3).toString().trim().equals("")) {
                            rec = new DaylightRecord(updateRecordValues.get(0).toString(), updateRecordValues.get(1).toString(), df.parse("0000"), df.parse(updateRecordValues.get(3).toString()));
                        } else {
                            rec = new DaylightRecord(updateRecordValues.get(0).toString(), updateRecordValues.get(1).toString(), df.parse(updateRecordValues.get(2).toString()), df.parse(updateRecordValues.get(3).toString()));
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // update the respective employee record with the changed data.
                    stmt.execute("UPDATE DAYLIGHTRECORD SET "
                            + "day=" + Integer.parseInt(rec.getDay()) + ", "
                            + "month='" + rec.getMonth() + "', "
                            + "sunRise='" + df.format(rec.getSunRise()) + "', "
                            + "sunSet='" + df.format(rec.getSunSet())
                            + "' WHERE day=" + Integer.parseInt(rec.getDay()) + " and month='" + rec.getMonth() + "';");
                    result = "Record Updated Succesfully";

                } else {
                    //If record exists display respective message.
                    result = "Record for the day " + updateRecordValues.get(0) + " and month " + updateRecordValues.get(1) + " does not exists. Record not updated.";
                }
            } else {
                result = "Error while connecting to database. Please check the MySQL server connection.";
            }
        } //catch exception if occurs while updating the data.
        catch (SQLException ex) {
            result = "Error while updating data into database.";
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
        //return message.
        return result;
    }

    //This function is called whenever the user wants to delete daylight record(s).
    //day value and month value of the record is asked as input by the user. 
    //If the entered day and month value is valid appropriate record is deleted.
    public static String deleteRecord(String day, String month) {
        //connect to database.
        Connection conn = null;
        String result = "";
        boolean isValidday = false;
        try {
            //check for valid day value.
            if (!day.equals("")) {
                Integer.parseInt(day);
                isValidday = true;
            } else {
                isValidday = true;
            }
        } catch (NumberFormatException nfe) {
            result = "Error in entered day data type.";
        }
        if (isValidday) {
            conn = getConnection();
            if (conn != null) {
                try {
                    isValidday = false;
                    //creates a statement object by sending SQL statements to the database.
                    Statement stmt = conn.createStatement();
                    if (!day.equals("") && !month.equals("None")) {
                        //Executes the SQL statement. This is to check whether the daylight record
                        //exists. This check is done to avoid deleting the data which is not present.
                        ResultSet rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day = " + Integer.parseInt(day) + " and month='" + month + "';");
                        //After checking data whether present or not do the appropriate task.
                        if (rs.next()) {
                            //Executes the SQL statement to delete the respective daylight record. 
                            stmt.execute("DELETE FROM DAYLIGHTRECORD WHERE day = " + Integer.parseInt(day) + " and month='" + month + "';");
                            isValidday = true;
                        } else {
                            result = "Record for day " + day + " and month " + month + " not exists.";
                        }
                    } else if (day.equals("") && !month.equals("None")) {

                        ResultSet rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE month='" + month + "';");
                        //After checking data whether present or not do the appropriate task.
                        if (rs.next()) {
                            //Executes the SQL statement to delete the respective employee record using employee number ID. 
                            stmt.execute("DELETE FROM DAYLIGHTRECORD WHERE month='" + month + "';");
                            isValidday = true;
                        } else {
                            result = "Record for month " + month + " not exists.";
                        }
                    } else if (!day.equals("") && month.equals("None")) {
                        ResultSet rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day = " + Integer.parseInt(day) + ";");
                        //After checking data whether present or not do the appropriate task.
                        if (rs.next()) {
                            //Executes the SQL statement to delete the respective employee record using employee number ID. 
                            stmt.execute("DELETE FROM DAYLIGHTRECORD WHERE day = " + Integer.parseInt(day) + ";");
                            isValidday = true;
                        } else {
                            result = "Record for day " + day + " not exists.";
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                }
                //if the delete is done enter into the loop.
                if (isValidday) {
                    ResultSet rs = null;
                    try {
                        //creates a statement object by sending SQL statements to the database.
                        Statement stmt = conn.createStatement();
                        if (!day.equals("") && !month.equals("None")) {
                            //Executes the SQL statement. This is to check whether the data is deleted successfully or not.
                            rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day = " + Integer.parseInt(day) + " and month='" + month + "';");
                        } else if (day.equals("") && !month.equals("None")) {
                            //Executes the SQL statement. This is to check whether the data is deleted successfully or not.
                            rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE month='" + month + "';");
                        } else if (!day.equals("") && month.equals("None")) {
                            //Executes the SQL statement. This is to check whether the data is deleted successfully or not.
                            rs = stmt.executeQuery("SELECT * FROM DAYLIGHTRECORD WHERE day=" + Integer.parseInt(day) + ";");
                        }
                    }//catches exception if error occurs while executing the query.
                    catch (SQLException ex) {
                        Logger.getLogger(JDBCUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        //if data is deleted from database then set successful result message else failure message.
                        if (!rs.next()) {
                            result = "Record Deletion Successful.";
                        } else {
                            result = "Record not deleted.";
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
            } else {
                result = "Error while connecting to database. Please check the MySQL server connection.";
            }
        }// returns message result.
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
        }
        return conn;
    }

    //this function is called at the start up to create the database table 
    //and insert csv file data into database at startup.
    public static boolean initByCreatingandPopulatingTableInDatabase(List<DaylightRecord> dayLightRecords) {
        //connect to database.
        Connection conn = null;
        DateFormat df = new SimpleDateFormat("HHmm");
        boolean result = false;
        try {
            conn = getConnection();
            //if connection successful then only continue
            if (conn != null) {
                //creates a statement object by sending SQL statements to the database.
                Statement stmt = conn.createStatement();
                try {
                    //drop the table if any record exists.
                    stmt.execute("DROP TABLE DAYLIGHTRECORD;");
                } catch (SQLException ex) {
                    System.out.println("No such table exists... ");
                }
                //creates table in the database with columns mentioned.
                stmt.execute("CREATE TABLE DAYLIGHTRECORD (day integer,month VARCHAR(10),sunRise VARCHAR(4),sunSet VARCHAR(4), PRIMARY KEY(day,month));");
                for (DaylightRecord dlr : dayLightRecords) {
                    if (dlr != null) {
                        //inserts daylight records into database.
                        stmt.execute("INSERT INTO DAYLIGHTRECORD ("
                                + "day, month, sunRise, sunSet)"
                                + " VALUES('"
                                + Integer.parseInt(dlr.getDay()) + "','"
                                + dlr.getMonth() + "','"
                                + df.format(dlr.getSunRise()) + "','"
                                + df.format(dlr.getSunSet()) + "');");
                    }
                }
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println("SQL statement execution fail !");
            System.out.println(ex.getMessage());
        } finally {
            try {
                //Close the connection with database at last when the required operation is finished.
                if (conn != null) {
                    conn.close();
                }
            }//catches exception if error occurs while closing the database connection.  
            catch (SQLException ex) {
                Logger.getLogger(JDBCUtilities.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        //returns boolean result.
        return result;
    }
}
