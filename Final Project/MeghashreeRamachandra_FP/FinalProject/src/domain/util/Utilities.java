/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.util;

import domain.DataAnalyticsResult;
import domain.DaylightRecord;
import domain.PersistentObject;
import domain.SummerSolstice;
import domain.VernalandAutumnalEquinox;
import domain.WinterSolstice;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.JDBCUtilities;

/**
 * @author Meghashree M Ramachandra
 */
public class Utilities {

    //to read data from file
    public static List readCSVfile() {
        BufferedReader br;
        String line;
        DaylightRecord dlrData;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HHmm");
        List daylightRecords = new ArrayList<DaylightRecord>();
        int month;
        int i = 0;
        try {
            // Read data in from CSV file...
            br = new BufferedReader(new FileReader("data/sunrise-sunset.csv"));
            String dateSunrise;
            String dateSunset;
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            while ((line = br.readLine()) != null) {
                // Extract tokens from CSV line with comma delimiter...
                i = 0;
                month = 1;
                StringTokenizer st = new StringTokenizer(line, ",");
                String day = st.nextToken();
                while (st.hasMoreTokens()) {
                    String sunRiseData = st.nextToken();
                    String sunSetData = st.nextToken();
                    if (sunRiseData.length() == 3) {
                        sunRiseData = "0" + sunRiseData;
                    }
                    if (sunSetData.length() == 3) {
                        sunSetData = "0" + sunSetData;
                    }
                    dateSunrise = Integer.toString(month) + "/" + day + "/2013 " + sunRiseData;
                    dateSunset = Integer.toString(month) + "/" + day + "/2013 " + sunSetData;
                    if ((day.equals("29") || day.equals("30")) && month == 2) {
                        //check for month which don't have day 29 and 30 in the year 2013.
                        daylightRecords.add(null);
                        dateSunrise = Integer.toString(++month) + "/" + day + "/2013 " + sunRiseData;
                        dateSunset = Integer.toString(month) + "/" + day + "/2013 " + sunSetData;
                        dlrData = new DaylightRecord(day, months[++i], format.parse(dateSunrise), format.parse(dateSunset));
                        daylightRecords.add(dlrData);

                    } else if ((day.equals("31")) && (month == 2 || month == 4 || month == 6 || month == 9 || month == 11)) {
                        //check for months which don't have day 31 in the year 2013.
                        daylightRecords.add(null);
                        dateSunrise = Integer.toString(++month) + "/" + day + "/2013 " + sunRiseData;
                        dateSunset = Integer.toString(month) + "/" + day + "/2013 " + sunSetData;
                        dlrData = new DaylightRecord(day, months[++i], format.parse(dateSunrise), format.parse(dateSunset));
                        daylightRecords.add(dlrData);

                    } else {
                        //create daylight record object and insert into list.
                        dlrData = new DaylightRecord(day, months[i], format.parse(dateSunrise), format.parse(dateSunset));
                        daylightRecords.add(dlrData);

                    }
                    i++;
                    month++;
                }
            }
        }//exception if any error occurs while retrieving the data from csv file and storing it. 
        catch (ParseException ex) {
            daylightRecords.clear();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            daylightRecords.clear();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            daylightRecords.clear();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daylightRecords;
    }

    //to get day light records data from the database.
    public static List databaseDataRecords() {
        DaylightRecord dlrData;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HHmm");
        DateFormat df = new SimpleDateFormat("HHmm");
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        List daylightRecords = null;
        String dateSunrise;
        String dateSunset;
        // Retrieves all records from database stored in list.
        List<DaylightRecord> retrievedRecordList = JDBCUtilities.retrieveAllRecords();
        // if retrieved data present in the list.
        if (retrievedRecordList.size() > 0 && retrievedRecordList != null) {
            daylightRecords = new ArrayList<DaylightRecord>();
            for (DaylightRecord dlr : retrievedRecordList) {
                String sunRiseData = df.format(dlr.getSunRise());
                String sunSetData = df.format(dlr.getSunSet());
                if (sunRiseData.length() == 3) {
                    sunRiseData = "0" + sunRiseData;
                }
                if (sunSetData.length() == 3) {
                    sunSetData = "0" + sunSetData;
                }
                dateSunrise = (months.indexOf(dlr.getMonth()) + 1) + "/" + dlr.getDay() + "/2013 " + sunRiseData;
                dateSunset = (months.indexOf(dlr.getMonth()) + 1) + "/" + dlr.getDay() + "/2013 " + sunSetData;
                try {
                    dlrData = new DaylightRecord(dlr.getDay(), dlr.getMonth(), format.parse(dateSunrise), format.parse(dateSunset));
                    daylightRecords.add(dlrData);
                } catch (ParseException ex) {
                    Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return daylightRecords;
    }

    //to serialize the persistend object.
    public static boolean serializeListObject(PersistentObject pObject) {
        boolean result = false;
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("data/daylight-record.ser"));
            oos.writeObject(pObject);
            result = true;
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //make app sleep for 10 seconds
    public static void makeAppSleep() {
        try {
            System.out.println("Waiting 10 seconds...");
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //to deserialize the serialized persistent object.
    public static PersistentObject deserializeListObject() {
        ObjectInputStream ois = null;
        PersistentObject pObject = null;
        try {
            // Serialize records as a aggregate...
            pObject = new PersistentObject();
            ois = new ObjectInputStream(new FileInputStream("data/daylight-record.ser"));
            pObject = (PersistentObject) ois.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pObject;
    }

    //create csv file after deserialization
    public static StringBuilder createCSVfile(PersistentObject pob) {
        DateFormat df = new SimpleDateFormat("HHmm");
        StringBuilder sb = new StringBuilder();
        int day = 1;
        DaylightRecord d;
        for (Object r : pob.getDayLightRecord()) {

            d = (DaylightRecord) r;
            // sb.append(pob.getDateObj() + ",");
            if (d != null) {
                if (Integer.toString(day).equals(d.getDay().toString())) {
                    if (day != 1) {
                        sb.append("\n");
                    }
                    sb.append(d.getDay().toString() + ",");
                    sb.append(df.format(d.getSunRise()) + ",");
                    sb.append(df.format(d.getSunSet()) + ",");
                    day++;
                } else {
                    sb.append(df.format(d.getSunRise()) + ",");
                    sb.append(df.format(d.getSunSet()) + ",");
                }

            } else {
                sb.append("," + ",");
                continue;
            }
        }
        return sb;
    }

    //write csv file after deserialization
    public static boolean writeCSVfile(StringBuilder sb) {
        boolean result = false;
        try {
            FileWriter fw;
            fw = new FileWriter("data/deserializedDaylightRecords.csv");
            fw.write(sb.toString(), 0, sb.length());
            fw.flush();
            result = true;
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //to compute data analytics for winter and summer
    public static List findCorrespondingData(List daylightRecords, String startDate, String endDate, boolean checkForSolstice) {
        List computationList = new ArrayList<DataAnalyticsResult>();
        int value;
        for (int index = 0; index < daylightRecords.size(); index++) {

            if (daylightRecords.get(index) == null) {
                index++;
                continue;
            } else {
                value = Utilities.computeSolsticeDataAnalytics(daylightRecords.get(index), startDate, endDate, checkForSolstice);
                if (value != 0) {
                    computationList.add(new DataAnalyticsResult(index, value));
                }
            }
        }
        if (checkForSolstice == true) {
            Collections.sort(computationList, new WinterSolstice());
        } else {
            Collections.sort(computationList, new SummerSolstice());
        }
        return computationList;
    }

    //to compute data analytics for spring and fall
    public static List findRelevantData(List daylightRecords, String startDate, String endDate) {
        List computationListEqualDayNight = new ArrayList<DataAnalyticsResult>();
        int nextDaySunriseDataIndex;
        int valueResult;
        for (int index = 0; index < daylightRecords.size(); index++) {

            if (daylightRecords.get(index) == null) {
                index++;
                continue;
            } else {
                nextDaySunriseDataIndex = index + 12;
                if (nextDaySunriseDataIndex >= daylightRecords.size()) {
                    nextDaySunriseDataIndex = 0;
                } else if (daylightRecords.get(nextDaySunriseDataIndex) == null) {
                    nextDaySunriseDataIndex++;
                } else {
                    valueResult = Utilities.computeEquinoxDataAnalytics(daylightRecords.get(index), daylightRecords.get(nextDaySunriseDataIndex), startDate, endDate);
                    if (valueResult != -1) {
                        computationListEqualDayNight.add(new DataAnalyticsResult(index, valueResult));
                    }
                }

            }
        }
        Collections.sort(computationListEqualDayNight, new VernalandAutumnalEquinox());
        return computationListEqualDayNight;
    }

    //compute data analytics for the specified date range and aggregate the data(for summer and winter)
    public static int computeSolsticeDataAnalytics(Object o, String startDayofSolstice, String endDayofSolstice, boolean checkForSolstice) {
        int finalValue = 0;
        DaylightRecord dr1 = (DaylightRecord) o;

        String winterSolsticeStart = startDayofSolstice;
        String winterSolsticeEnd = endDayofSolstice;
        DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            if (checkForSolstice == true) {
                if (dr1.getSunRise().compareTo(df1.parse(winterSolsticeStart)) >= 0 || dr1.getSunSet().compareTo(df1.parse(winterSolsticeEnd)) <= 0) {
                    finalValue = Integer.parseInt(findDayLength(dr1));
                }
            } else if (checkForSolstice == false) {
                if ((dr1.getSunRise().compareTo(df1.parse(winterSolsticeStart)) >= 0 && dr1.getSunSet().compareTo(df1.parse(winterSolsticeEnd)) <= 0)) {
                    finalValue = Integer.parseInt(findDayLength(dr1));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalValue;
    }

    //compute day length
    public static String findDayLength(DaylightRecord dr1) {
        long d;

        d = Math.abs(dr1.getSunRise().getTime() - dr1.getSunSet().getTime());
        int Hours = (int) ((d / (1000 * 60 * 60)) % 24);
        int Minutes = (int) (int) ((d / (1000 * 60)) % 60);
        String s;
        if (Integer.toString(Minutes).length() == 1) {
            s = "0" + Integer.toString(Minutes);
            s = Integer.toString(Hours) + s;
        } else {
            s = Integer.toString(Hours) + Integer.toString(Minutes);
        }
        return s;
    }

    //compute data analytics for the specified date range and aggregate the data(for spring and fall)
    public static int computeEquinoxDataAnalytics(Object o1, Object o2, String equinoxStartDate, String equinoxEndDate) {

        int finalValue = -1;
        DaylightRecord dr1 = (DaylightRecord) o1;
        DaylightRecord dr2 = (DaylightRecord) o2;
        long d1;
        String winterSolsticeStart = equinoxStartDate;
        String winterSolsticeEnd = equinoxEndDate;
        DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            if (dr1.getSunRise().compareTo(df1.parse(winterSolsticeStart)) >= 0 && dr1.getSunSet().compareTo(df1.parse(winterSolsticeEnd)) <= 0 && dr2.getSunRise().compareTo(df1.parse(winterSolsticeStart)) >= 0) {
                d1 = Math.abs(dr1.getSunRise().getTime() - dr1.getSunSet().getTime());
                int dayHours = (int) ((d1 / (1000 * 60 * 60)) % 24);
                int dayMinutes = (int) ((d1 / (1000 * 60)) % 60);

                int timeHoursDifference = 0;
                int timeMinutesDifference = 0;
                String s;
                if (dayHours >= 12 && dayMinutes >= 0) {
                    if (dayHours == 12 && dayMinutes == 0) {
                        finalValue = 0;
                    } else {
                        timeHoursDifference = 23 - dayHours;
                        timeMinutesDifference = 60 - dayMinutes;
                        timeHoursDifference = 11 - timeHoursDifference;
                        timeMinutesDifference = 60 - timeMinutesDifference;
                        if (Integer.toString(timeMinutesDifference).length() == 1) {
                            s = "0" + Integer.toString(timeMinutesDifference);
                            s = Integer.toString(timeHoursDifference) + s;
                        } else {
                            s = Integer.toString(timeHoursDifference) + Integer.toString(timeMinutesDifference);
                        }
                        finalValue = Integer.parseInt(s);
                    }
                } else {
                    timeHoursDifference = 12 - dayHours;
                    timeMinutesDifference = 00 - dayMinutes;
                    if (timeMinutesDifference < 0) {
                        timeHoursDifference = timeHoursDifference - 1;
                        timeMinutesDifference = timeMinutesDifference + 60;
                    }
                    if (Integer.toString(timeMinutesDifference).length() == 1) {
                        s = "0" + Integer.toString(timeMinutesDifference);
                        s = Integer.toString(timeHoursDifference) + s;
                    } else {
                        s = Integer.toString(timeHoursDifference) + Integer.toString(timeMinutesDifference);
                    }
                    finalValue = Integer.parseInt(s);
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalValue;
    }

    //retrieve required data for display
    public static ArrayList retrieveDataFromComputation(List newList, List dataList) {
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        ArrayList finalValues = new ArrayList();
        DataAnalyticsResult dar;
        DaylightRecord dlr;
        int firstTime = 0;
        int data = 0;
        int repeat = 1;
        for (Object o : newList) {
            dar = (DataAnalyticsResult) o;
            dlr = (DaylightRecord) dataList.get(dar.getArrayIndexData());

            if (firstTime == 0) {
                data = dar.getComputationData();
                firstTime = 1;
                finalValues.add(df.format(dlr.getSunRise()).toString());
            } else {
                if (data == dar.getComputationData()) {
                    repeat++;
                    finalValues.add(df.format(dlr.getSunRise()).toString());
                }
            }
        }
        finalValues.add(repeat);
        return finalValues;
    }

    //display computed data
    public static void printData(ArrayList computationData) {
        int repeatData = computationData.size();
        for (int m = 0; m < repeatData - 1; m++) {
            System.out.print(computationData.get(m) + ",");
        }
    }

    //write daylightrecords to file
    public static boolean writeListtoFile(List daylightRecords) {
        boolean result = false;
        try {
            BufferedWriter out = null;
            File file = new File("output/daylight-records.txt");
            out = new BufferedWriter(
                    new FileWriter(file));
            for (Object s : daylightRecords) {
                // Write line to file.
                if (s != null) {
                    DaylightRecord d = (DaylightRecord) s;
                    out.write(d.toString());
                } else {
                    out.write("null");
                }
                // Write newLine with BufferedReader method.
                out.newLine();
            }
            out.close();
            result = true;
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
         return result;
    }
   
}
