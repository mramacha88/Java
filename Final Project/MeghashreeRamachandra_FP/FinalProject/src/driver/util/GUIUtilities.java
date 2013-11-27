/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package driver.util;

import domain.DaylightRecord;
import domain.PersistentObject;
import domain.util.Utilities;
import driver.MainFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import jdbc.JDBCUtilities;

/**
 *
 * @author MeghaVinay
 */
//This class is used to minimize the main class, 
//which makes main class look neat and easy to understand
public class GUIUtilities {

    //This function is called when user want to create record into database 
    //ie, when create button is pressed from GUI.This function creates the Jframe
    //whic have fields to accept input required to create record.
    public static void createRecordJFrame(final MainFrame mf, final JLabel OperationStatusDisplayValue) {
        final List data = new ArrayList();
        //creates jframe window.
        final JFrame cjf = new JFrame("Enter the data to create record.");
        cjf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //listener when button pressed on window.
        cjf.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                mf.setEnabled(true);
                OperationStatusDisplayValue.setText("Create task cancelled.");
            }
        });
        //creates panel.
        JPanel cjp = new JPanel();
        //sets border for panel.
        cjp.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        // this sets the location on the screen for this window to display.
        cjf.setLocation(mf.getSize().width / 2 - cjf.getSize().width / 2, mf.getSize().height / 2 - cjf.getSize().height / 2);
        //creates label.
        JLabel cjl_day = new JLabel("Enter day(Integer data type):");
        //Textfield to accept input for day.
        final JTextField cj1_dayTextField = new JTextField();
        JLabel cjl_month = new JLabel("Select Month: ");
        //combobox create to display the list of months out of 
        //which user has to select one to insert record for respective month.
        String[] comboBoxContents = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        // creates combo box with comboBoxContents.
        final JComboBox cj1_monthComboBox = new JComboBox(comboBoxContents);
        JLabel cjl_sunRise = new JLabel("Enter sun rise time(HHmm format): ");
        //Textfield to accept the sunRise value.
        final JTextField cj1_sunRiseTextField = new JTextField(4);
        JLabel cjl_sunSet = new JLabel("Enter sun set time(HHmm format): ");
        //Textfield to accept the sunset value
        final JTextField cj1_sunSetTextField = new JTextField(4);
        // create buttons for accpting the values or cancelling the task.
        JButton cok_Button = new JButton("Ok");
        JButton ccancel_Button = new JButton("Cancel");
        //set layout size.
        cjp.setLayout(new GridLayout(5, 2));
        //add all the created components into jframe.
        cjf.add(cjp);
        cjp.add(cjl_day);
        cjp.add(cj1_dayTextField);
        cjp.add(cjl_month);
        cjp.add(cj1_monthComboBox);
        cjp.add(cjl_sunRise);
        cjp.add(cj1_sunRiseTextField);
        cjp.add(cjl_sunSet);
        cjp.add(cj1_sunSetTextField);
        cjp.add(cok_Button);
        cjp.add(ccancel_Button);
        cjf.setVisible(true);
        cjf.setSize(300, 200);
        cjf.pack();
        //when this jframe is popped up disable the main window to avoid performing other operations.
        mf.setEnabled(false);
        // when ok button is pressen from jframe window popped up.
        cok_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                data.add(cj1_dayTextField.getText());
                data.add((String) cj1_monthComboBox.getSelectedItem());
                data.add(cj1_sunRiseTextField.getText());
                data.add(cj1_sunSetTextField.getText());
                cjf.setVisible(false);
                if (!data.get(0).toString().trim().equals("")) {
                    if ((data.get(0).toString().trim().equals("29") || data.get(0).toString().trim().equals("30") || data.get(0).toString().trim().equals("31")) && (data.get(1).toString().trim().equals("February") || data.get(1).toString().trim().equals("April") || data.get(1).toString().trim().equals("June") || data.get(1).toString().trim().equals("September") || data.get(1).toString().trim().equals("November"))) {
                        OperationStatusDisplayValue.setText("Day " + data.get(0) + " for " + data.get(1) + " doesnt exist for year 2013. Record not created.");
                    } else {
                        boolean result = checkIsInteger(data.get(0).toString().trim(), OperationStatusDisplayValue, "Error in entered day data type. Please follow the datatype while entering the inputs");
                        if (result) {
                            if (Integer.parseInt(data.get(0).toString()) > 31) {
                                OperationStatusDisplayValue.setText("Day " + data.get(0) + " for " + data.get(1) + " doesnt exist for year 2013. Record not created.");
                            } else {
                                if ((data.get(2).toString().trim().equals("")) && (data.get(3).toString().trim().equals(""))) {
                                    String message = JDBCUtilities.createRecord(data);
                                    OperationStatusDisplayValue.setText(message);
                                } else if ((!data.get(2).toString().trim().equals("")) && (data.get(3).toString().trim().equals(""))) {
                                    if (!(data.get(2).toString().length() < 4) && !(data.get(2).toString().length() > 4)) {
                                        result = false;
                                        result = checkIsInteger(data.get(2).toString().trim(), OperationStatusDisplayValue, "Error in entered sunRise value. Input is not a time value.");
                                        if (result) {
                                            if (Integer.parseInt(data.get(2).toString().trim()) < 0000 || Integer.parseInt(data.get(2).toString().trim()) > 2359) {
                                                OperationStatusDisplayValue.setText("Error in entered sunRise time. Time value should be between 0000-2359");
                                            } else {
                                                String message = JDBCUtilities.createRecord(data);
                                                OperationStatusDisplayValue.setText(message);
                                            }
                                        } else {
                                            OperationStatusDisplayValue.setText("Error in entered sunRise format. Please follow the datatype while entering the inputs");
                                        }
                                    } else {
                                        OperationStatusDisplayValue.setText("Error in entered sunRise format. Please follow the datatype while entering the inputs");
                                    }

                                } else if ((data.get(2).toString().trim().equals("")) && (!data.get(3).toString().trim().equals(""))) {
                                    if (!(data.get(3).toString().trim().length() < 4) && !(data.get(3).toString().trim().length() > 4)) {
                                        result = false;
                                        result = checkIsInteger(data.get(3).toString().trim(), OperationStatusDisplayValue, "Error in entered sunSet value. Input is not a time value.");
                                        if (result) {
                                            if (Integer.parseInt(data.get(3).toString().trim()) < 0000 || Integer.parseInt(data.get(3).toString().trim()) > 2359) {
                                                OperationStatusDisplayValue.setText("Error in entered sunSet time. Time value should be between 0000-2359");
                                            } else {
                                                String message = JDBCUtilities.createRecord(data);
                                                OperationStatusDisplayValue.setText(message);
                                            }
                                        } else {
                                            OperationStatusDisplayValue.setText("Error in entered sunSet format. Please follow the datatype while entering the inputs");
                                        }
                                    }

                                } else if (!(data.get(2).toString().trim().length() < 4) && !(data.get(2).toString().trim().length() > 4)) {
                                    result = false;
                                    result = checkIsInteger(data.get(2).toString().trim(), OperationStatusDisplayValue, "Error in entered sunRise value. Input is not a time value.");
                                    if (result) {
                                        if (Integer.parseInt(data.get(2).toString().trim()) < 0000 || Integer.parseInt(data.get(2).toString().trim()) > 2359) {
                                            OperationStatusDisplayValue.setText("Error in entered sunRise time. Time value should be between 0000-2359");
                                        } else {
                                            result = false;
                                            if (!(data.get(3).toString().trim().length() < 4) && !(data.get(3).toString().trim().length() > 4)) {
                                                result = checkIsInteger(data.get(3).toString().trim(), OperationStatusDisplayValue, "Error in entered sunSet value. Input is not a time value.");
                                                if (result) {
                                                    if (Integer.parseInt(data.get(3).toString().trim()) < 0000 || Integer.parseInt(data.get(3).toString().trim()) > 2359) {
                                                        OperationStatusDisplayValue.setText("Error in entered sunSet time. Time value should be between 0000-2359");
                                                    } else {
                                                        String message = JDBCUtilities.createRecord(data);
                                                        OperationStatusDisplayValue.setText(message);
                                                    }
                                                }
                                            } else {
                                                OperationStatusDisplayValue.setText("Error in entered sunSet format. Please follow the datatype while entering the inputs");
                                            }
                                        }
                                    }
                                } else {
                                    OperationStatusDisplayValue.setText("Error in entered sunRise format type. Please follow the datatype while entering the inputs");
                                }
                            }
                        }
                    }
                } else {
                    OperationStatusDisplayValue.setText("Empty day value. Record not created. ");
                }
                mf.setEnabled(true);
            }
        });
        // when cancel button is pressen from jframe window popped up.
        ccancel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cjf.setVisible(false);
                OperationStatusDisplayValue.setText("Create task cancelled.");
                mf.setEnabled(true);
            }
        });
    }

    //This function is called when user want to update record into database 
    //ie, when update button is pressed from GUI.This function creates the Jframe
    //which have fields to accept input required to update record.
    public static void updateRecordJFrame(final MainFrame mf, final JLabel OperationStatusDisplayValue) {
        final List data = new ArrayList();
        //creates jframe window.
        final JFrame cjf = new JFrame("Enter the data to update record.");
        cjf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //listener when button pressed on window.
        cjf.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                mf.setEnabled(true);
                OperationStatusDisplayValue.setText("Update task cancelled.");
            }
        });
        //sets border for panel.
        JPanel cjp = new JPanel();
        //sets border for panel.
        cjp.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        // this sets the location on the screen for this window to display.
        cjf.setLocation(mf.getSize().width / 2 - cjf.getSize().width / 2, mf.getSize().height / 2 - cjf.getSize().height / 2);
        //creates label.
        JLabel cjl_day = new JLabel("Enter day(Integer data type):");
        //Textfield to accept input for day.
        final JTextField cj1_dayTextField = new JTextField();
        JLabel cjl_month = new JLabel("Select Month: ");
        //combobox create to display the list of months out of 
        //which user has to select one to insert record for respective month.
        String[] comboBoxContents = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        // creates combo box with comboBoxContents.
        final JComboBox cj1_monthComboBox = new JComboBox(comboBoxContents);
        JLabel cjl_sunRise = new JLabel("Enter sun rise time(HHmm format):");
        //Textfield to accept the sunRise value.
        final JTextField cj1_sunRiseTextField = new JTextField(4);
        JLabel cjl_sunSet = new JLabel("Enter sun set time(HHmm format): ");
        //Textfield to accept the sunset value
        final JTextField cj1_sunSetTextField = new JTextField(4);
        // create buttons for accpting the values or cancelling the task.
        JButton cok_Button = new JButton("Ok");
        JButton ccancel_Button = new JButton("Cancel");
        //set layout size.
        cjp.setLayout(new GridLayout(5, 2));
        //add all the created components into jframe.
        cjf.add(cjp);
        cjp.add(cjl_day);
        cjp.add(cj1_dayTextField);
        cjp.add(cjl_month);
        cjp.add(cj1_monthComboBox);
        cjp.add(cjl_sunRise);
        cjp.add(cj1_sunRiseTextField);
        cjp.add(cjl_sunSet);
        cjp.add(cj1_sunSetTextField);
        cjp.add(cok_Button);
        cjp.add(ccancel_Button);
        cjf.setVisible(true);
        cjf.setSize(300, 200);
        cjf.pack();
        //when this jframe is popped up disable the main window to avoid performing other operations.
        mf.setEnabled(false);
        // when ok button is pressen from jframe window popped up.
        cok_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                data.add(cj1_dayTextField.getText());
                data.add((String) cj1_monthComboBox.getSelectedItem());
                data.add(cj1_sunRiseTextField.getText());
                data.add(cj1_sunSetTextField.getText());
                cjf.setVisible(false);
                if (!data.get(0).equals("")) {
                    boolean result = checkIsInteger(data.get(0).toString(), OperationStatusDisplayValue, "Error in entered day data type. Please follow the datatype while entering the inputs");
                    if (result) {
                        if ((data.get(2).toString().trim().equals("")) && (data.get(3).toString().trim().equals(""))) {
                            String message = JDBCUtilities.updateRecord(data);
                            OperationStatusDisplayValue.setText(message);
                        } else if ((!data.get(2).toString().trim().equals("")) && (data.get(3).toString().trim().equals(""))) {
                            if (!(data.get(2).toString().trim().length() < 4) && !(data.get(2).toString().trim().length() > 4)) {
                                result = false;
                                result = checkIsInteger(data.get(2).toString().trim(), OperationStatusDisplayValue, "Error in entered sunRise value. Input is not a time value.");
                                if (result) {
                                    if (Integer.parseInt(data.get(2).toString().trim()) < 0000 || Integer.parseInt(data.get(2).toString().trim()) > 2359) {
                                        OperationStatusDisplayValue.setText("Error in entered sunRise time value. Time value should be between 0000-2359");
                                    } else {
                                        String message = JDBCUtilities.updateRecord(data);
                                        OperationStatusDisplayValue.setText(message);
                                    }
                                } else {
                                    OperationStatusDisplayValue.setText("Error in entered sunRise format. Please follow the datatype while entering the inputs");
                                }
                            } else {
                                OperationStatusDisplayValue.setText("Error in entered sunRise format. Please follow the datatype while entering the inputs");
                            }

                        } else if ((data.get(2).toString().trim().equals("")) && (!data.get(3).toString().trim().equals(""))) {
                            if (!(data.get(3).toString().trim().length() < 4) && !(data.get(3).toString().trim().length() > 4)) {
                                result = false;
                                result = checkIsInteger(data.get(3).toString().trim(), OperationStatusDisplayValue, "Error in entered sunSet value. Input is not a time value.");
                                if (result) {
                                    if (Integer.parseInt(data.get(3).toString().trim()) < 0000 || Integer.parseInt(data.get(3).toString().trim()) > 2359) {
                                        OperationStatusDisplayValue.setText("Error in entered sunSet time. Time value should be between 0000-2359");
                                    } else {
                                        String message = JDBCUtilities.updateRecord(data);
                                        OperationStatusDisplayValue.setText(message);
                                    }
                                } else {
                                    OperationStatusDisplayValue.setText("Error in entered sunSet format. Please follow the datatype while entering the inputs");
                                }
                            } else {
                                OperationStatusDisplayValue.setText("Error in entered sunSet format. Please follow the datatype while entering the inputs");
                            }
                        } else if (!(data.get(2).toString().trim().length() < 4) && !(data.get(2).toString().trim().length() > 4)) {
                            result = false;
                            result = checkIsInteger(data.get(2).toString().trim(), OperationStatusDisplayValue, "Error in entered sunRise value. Input is not a time value.");
                            if (result) {
                                if (Integer.parseInt(data.get(2).toString().trim()) < 0000 || Integer.parseInt(data.get(2).toString().trim()) > 2359) {
                                    OperationStatusDisplayValue.setText("Error in entered sunRise time. Time value should be between 0000-2359");
                                } else {
                                    result = false;
                                    if (!(data.get(3).toString().trim().length() < 4) && !(data.get(3).toString().trim().length() > 4)) {
                                        result = checkIsInteger(data.get(3).toString().trim(), OperationStatusDisplayValue, "Error in entered sunSet value. Input is not a time value.");
                                        if (result) {
                                            if (Integer.parseInt(data.get(3).toString().trim()) < 0000 || Integer.parseInt(data.get(3).toString().trim()) > 2359) {
                                                OperationStatusDisplayValue.setText("Error in entered sunSet time. Time value should be between 0000-2359");
                                            } else {
                                                String message = JDBCUtilities.updateRecord(data);
                                                OperationStatusDisplayValue.setText(message);
                                            }
                                        }
                                    } else {
                                        OperationStatusDisplayValue.setText("Error in entered sunSet format. Please follow the datatype while entering the inputs");
                                    }
                                }
                            }
                        } else {
                            OperationStatusDisplayValue.setText("Error in entered sunRise format. Please follow the datatype while entering the inputs");
                        }
                    }
                } else {
                    OperationStatusDisplayValue.setText("Empty day value. Record not updated. ");
                }
                mf.setEnabled(true);
            }
        });
        // when cancel button is pressen from jframe window popped up.
        ccancel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cjf.setVisible(false);
                OperationStatusDisplayValue.setText("Update task cancelled.");
                mf.setEnabled(true);
            }
        });
    }

    //This function is used to check whether the string is integer or not.
    public static boolean checkIsInteger(String value, JLabel OperationStatusDisplayValue, String message) {
        boolean result = false;
        try {
            //Parse the string to convert it to int.
            Integer.parseInt(value);
            result = true;
        } catch (NumberFormatException nfe) {
            OperationStatusDisplayValue.setText(message);
        }
        return result;
    }

    //This function is called to serialize the daylightrecords and date of persistent object.
    //This method is invoked when serialize button is pressed from GUI.
    public static boolean serializeandDisplaytheStatusOnGUI(List<DaylightRecord> dayLightRecords, JLabel OperationStatusDisplayValue) {
        boolean result = false;
        //creates persistent object which will be serialized.
        PersistentObject pObj = new PersistentObject(new Date(), dayLightRecords);
        result = Utilities.serializeListObject(pObj);
        //If result is success or failure display respective message.
        if (result) {
            OperationStatusDisplayValue.setText("Serialization successful.");
        } else {
            OperationStatusDisplayValue.setText("Serialization failed.");

        }
        return result;
    }

    //This function is called to write the deserialized persistent object into a file.
    //This method is invoked when deserialize button is pressed from GUI.
    public static boolean writeDeserializedDatatofile(PersistentObject deserializePObj, JLabel OperationStatusDisplayValue) {
        boolean result = false;
        //this function is invoked to write dederialized object data into csv file
        StringBuilder sb = Utilities.createCSVfile(deserializePObj);
        result = Utilities.writeCSVfile(sb);
        //if error occurs then respective error message is displayed.

        return result;
    }

    //This function is used to compute the time difference between serialization and deserialization and displays it.
    //This method is invoked when diplay delta time button is pressed from GUI.
    public static void timeDiffSerandDeser(PersistentObject deserializedObj, JLabel OperationStatusDisplayValue, JLabel timeValue, long currentTime) {
        if (deserializedObj != null) {
            long diff = currentTime - deserializedObj.getDateObj().getTime();
            //Compute hours, minutes, seconds  of the total simulation time executed
            long Hours = diff / (60 * 60 * 1000);
            long Minutes = diff / (60 * 1000) % 60;
            long Seconds = diff / 1000 % 60;
            timeValue.setText(Hours + "hr " + Minutes + "min " + Seconds + "sec.");
            //if success or failure then respective message is displayed.
            OperationStatusDisplayValue.setText("Time difference between serialization and deserialization successfully displayed.");
        } else {
            OperationStatusDisplayValue.setText("Perform deserialization and then try to retrieve the time difference.");
        }
    }

    //This function is used to compute the required data analytics and display the result in appropriate box.
    //This method is invokec when execute button is pressed.
    public static void computeDataAnalytics(List<DaylightRecord> dayLightRecords, JTextField WinterSolsticeValue, JTextField VernalEquinoxValue, JTextField SummerSolsticeValue, JTextField AutumnalEquinoxValue, JLabel OperationStatusDisplayValue) {
        List computationListForShortestDay = Utilities.findCorrespondingData(dayLightRecords, "01/01/2013", "12/31/2013", true);
        ArrayList computationData = Utilities.retrieveDataFromComputation(computationListForShortestDay, dayLightRecords);
        String WinterSolsticeResult = computationData.get(0).toString();
        computationListForShortestDay = Utilities.findRelevantData(dayLightRecords, "03/01/2013", "06/30/2013");
        computationData = Utilities.retrieveDataFromComputation(computationListForShortestDay, dayLightRecords);
        String VernalEquinoxResult = computationData.get(0).toString();
        computationListForShortestDay = Utilities.findCorrespondingData(dayLightRecords, "01/01/2013", "12/31/2013", false);
        computationData = Utilities.retrieveDataFromComputation(computationListForShortestDay, dayLightRecords);
        String SummerSolsticeResult = computationData.get(0).toString();
        computationListForShortestDay = Utilities.findRelevantData(dayLightRecords, "09/01/2013", "12/30/2013");
        computationData = Utilities.retrieveDataFromComputation(computationListForShortestDay, dayLightRecords);
        String AutumnalEquinoxResult = computationData.get(0).toString();
        //display the results of computation results.
        WinterSolsticeValue.setText(WinterSolsticeResult);
        VernalEquinoxValue.setText(VernalEquinoxResult);
        SummerSolsticeValue.setText(SummerSolsticeResult);
        AutumnalEquinoxValue.setText(AutumnalEquinoxResult);
        //After retrieving and displaying the values, display the appropriate message on status label.
        OperationStatusDisplayValue.setText("Analytics result displayed successfully.");
    }

    //This function is used to reset all input text fields on GUI. 
    //This method is invoked when Reselt all input fields button is pressed.
    public static void refreshTextFields(JTextField WinterSolsticeValue, JTextField SummerSolsticeValue, JTextField VernalEquinoxValue, JTextField AutumnalEquinoxValue, JTextField DayInputTextField, JTextField DeleteDayInputTextField, JTextField BeginDayInputTextField, JTextField EndDayInputTextField, JComboBox MonthForDayComboValue, JComboBox MonthforRangeComboValue, JComboBox DeleteMonthForDayComboValue) {
        WinterSolsticeValue.setText("");
        SummerSolsticeValue.setText("");
        VernalEquinoxValue.setText("");
        AutumnalEquinoxValue.setText("");
        DayInputTextField.setText("");
        DeleteDayInputTextField.setText("");
        BeginDayInputTextField.setText("");
        EndDayInputTextField.setText("");
        MonthForDayComboValue.setSelectedItem(MonthForDayComboValue.getItemAt(0));
        MonthforRangeComboValue.setSelectedItem(MonthforRangeComboValue.getItemAt(0));
        DeleteMonthForDayComboValue.setSelectedItem(DeleteMonthForDayComboValue.getItemAt(0));
    }

    //This function is used to retrieve all the records present the database and
    //retrieved result is diplayes in the JTable.This method is invoked when Retrieve all button is pressed from GUI.
    public static void retrieveAllDatabaseRecords(JTable RecordDisplayTable, JLabel OperationStatusDisplayValue) {
        DateFormat df = new SimpleDateFormat("HHmm");
        List<DaylightRecord> retrievedRecordList = JDBCUtilities.retrieveAllRecords();
        if (retrievedRecordList != null) {
            if (retrievedRecordList.size() > 0) {
                Vector data = new Vector();
                Vector columnsName = new Vector();
                //get the columns name of the jtable.
                for (int i = 0; i < RecordDisplayTable.getColumnCount(); i++) {
                    columnsName.addElement(RecordDisplayTable.getColumnName(i));
                }
                //The day light record are retrieved from the list and store in a row
                //which is inturn added into data vector.
                for (DaylightRecord dlr : retrievedRecordList) {
                    Vector row = new Vector(RecordDisplayTable.getColumnCount());
                    row.addElement(dlr.getDay());
                    row.addElement(dlr.getMonth());
                    row.addElement(df.format(dlr.getSunRise()));
                    row.addElement(df.format(dlr.getSunSet()));
                    data.addElement(row);
                }
                //The read and stored row records are bind into a table for respective columns and inserted into Jtable.
                DefaultTableModel model = new DefaultTableModel(data, columnsName);
                RecordDisplayTable.setModel(model);
                OperationStatusDisplayValue.setText("Retrieved all records successfully. Database records displayed in table.");
            } else {
                //If database is empty the appropriate message is displayed in Jtable.
                Vector data = new Vector();
                Vector columnsName = new Vector();
                for (int i = 0; i < RecordDisplayTable.getColumnCount(); i++) {
                    columnsName.addElement(RecordDisplayTable.getColumnName(i));
                }
                Vector row = new Vector(RecordDisplayTable.getColumnCount());
                row.addElement("");
                row.addElement("No Record Exists.");
                data.add(row);
                DefaultTableModel model = new DefaultTableModel(data, columnsName);
                RecordDisplayTable.setModel(model);
                OperationStatusDisplayValue.setText("No Record Exists");
            }
        } else {
            OperationStatusDisplayValue.setText("Error while connecting to database to retrieve records. Please check the MySQL server connection.");
        }
    }

    //This function is used to retrieve records based on day and month present in the database and
    //retrieved result is diplayed in the JTable.This method is invoked when Retrieve by day button is pressed from GUI.
    public static void retrieveRecordByDay(String day, String month, JTable RecordDisplayTable, JLabel OperationStatusDisplayValue) {
        //validation is done for the input text fields.
        if (!day.isEmpty()) {
            DateFormat df = new SimpleDateFormat("HHmm");
            List<DaylightRecord> retrievedRecordList = JDBCUtilities.retrieveRecordForDay(day, month);
            if (retrievedRecordList != null) {
                //If any record exists then that record is displayed on Jtable.
                if (retrievedRecordList.size() > 0) {
                    Vector data = new Vector();
                    Vector columnsName = new Vector();
                    //get the columns name of the jtable.
                    for (int i = 0; i < RecordDisplayTable.getColumnCount(); i++) {
                        columnsName.addElement(RecordDisplayTable.getColumnName(i));
                    }
                    //The day light record are retrieved from the list and store in a row
                    //which is inturn added into data vector.
                    for (DaylightRecord dlr : retrievedRecordList) {
                        Vector row = new Vector(RecordDisplayTable.getColumnCount());
                        row.addElement(dlr.getDay());
                        row.addElement(dlr.getMonth());
                        row.addElement(df.format(dlr.getSunRise()));
                        row.addElement(df.format(dlr.getSunSet()));
                        data.addElement(row);
                    }
                    //The read and stored row records are bind into a table for respective columns and inserted into Jtable.
                    DefaultTableModel model = new DefaultTableModel(data, columnsName);
                    RecordDisplayTable.setModel(model);
                    OperationStatusDisplayValue.setText("Record(s) retrieved.");
                } else {
                    //If database is empty then appropriate message is displayed in Jtable.
                    Vector data = new Vector();
                    Vector columnsName = new Vector();
                    for (int i = 0; i < RecordDisplayTable.getColumnCount(); i++) {
                        columnsName.addElement(RecordDisplayTable.getColumnName(i));
                    }
                    Vector row = new Vector(RecordDisplayTable.getColumnCount());
                    row.addElement("");
                    row.addElement("No Record Exists.");
                    data.add(row);
                    DefaultTableModel model = new DefaultTableModel(data, columnsName);
                    RecordDisplayTable.setModel(model);
                    OperationStatusDisplayValue.setText("No Record Exists");
                }
            } else {
                OperationStatusDisplayValue.setText("Error while connecting to database to retrieve records. Please check the MySQL server connection.");
            }
        }//if any error in input text field then appropriate message is displayed. 
        else {
            OperationStatusDisplayValue.setText("Please enter the day to retrieve the records.");
        }
    }

    //This function is used to retrieve records between two particualr day ranges present in the database and
    //retrieved result is diplayed in the JTable.This method is invoked when Retrieve by day button is pressed from GUI.
    public static void retrieveRecordByDayRange(String startDay, String endDay, String month, JTable RecordDisplayTable, JLabel OperationStatusDisplayValue) {
        //validation is dont for the input text fields.
        if (!startDay.isEmpty() && !endDay.isEmpty()) {
            DateFormat df = new SimpleDateFormat("HHmm");
            List<DaylightRecord> retrievedRecordList = JDBCUtilities.retrieveRecordBetweenDayRange(startDay, endDay, month);
            if (retrievedRecordList != null) {
                //If any record exists then that record is diaplayed on Jtable.
                if (retrievedRecordList.size() > 0) {
                    Vector data = new Vector();
                    Vector columnsName = new Vector();
                    //get the columns name of the jtable.
                    for (int i = 0; i < RecordDisplayTable.getColumnCount(); i++) {
                        columnsName.addElement(RecordDisplayTable.getColumnName(i));
                    }
                    //The day light record are retrieved from the list and store in a row
                    //which is inturn added into data vector.
                    for (DaylightRecord dlr : retrievedRecordList) {
                        Vector row = new Vector(RecordDisplayTable.getColumnCount());
                        row.addElement(dlr.getDay());
                        row.addElement(dlr.getMonth());
                        row.addElement(df.format(dlr.getSunRise()));
                        row.addElement(df.format(dlr.getSunSet()));
                        data.addElement(row);
                    }
                    //The read and stored row records are bind into a table for respective columns and inserted into Jtable.
                    DefaultTableModel model = new DefaultTableModel(data, columnsName);
                    RecordDisplayTable.setModel(model);
                    OperationStatusDisplayValue.setText("Record(s) retrieved.");
                } else {
                    //If database is empty the appropriate message is displayed in Jtable.
                    Vector data = new Vector();
                    Vector columnsName = new Vector();
                    for (int i = 0; i < RecordDisplayTable.getColumnCount(); i++) {
                        columnsName.addElement(RecordDisplayTable.getColumnName(i));
                    }
                    Vector row = new Vector(RecordDisplayTable.getColumnCount());
                    row.addElement("");
                    row.addElement("No Record Exists.");
                    data.add(row);
                    DefaultTableModel model = new DefaultTableModel(data, columnsName);
                    RecordDisplayTable.setModel(model);
                    OperationStatusDisplayValue.setText("No Record Exists");
                }
            } else {
                OperationStatusDisplayValue.setText("Error while connecting to database to retrieve records. Please check the MySQL server connection.");
            }
        }//if any error in input text field then appropriate message is displayed. 
        else {
            OperationStatusDisplayValue.setText("Please enter the Begin day and End day to retrieve the records between two day ranges.");
        }
    }
    
    public static void writeDataToFile(JLabel OperationStatusDisplayValue, List<DaylightRecord> dayLightRecords){
          OperationStatusDisplayValue.setText("Processing.....");
        if (dayLightRecords != null) {
            //write all daylightRecords to text file daylight-Record.txt
            boolean result = Utilities.writeListtoFile(dayLightRecords);
            if(result){
                OperationStatusDisplayValue.setText("Data successfully written to file daylight-records.txt.");
            }else{
                OperationStatusDisplayValue.setText("Writing data to file failed.");
            }
        } else {
            OperationStatusDisplayValue.setText("Please read CSV file then try to write daylightrecords to a file.");
        }
    }
}
