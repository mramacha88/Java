/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermexam_program2characterstreams;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//@author Meghashree M Ramachandra

public class MidTermExam_Program2CharacterStreams {

    public static void main(String[] args) {

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            //instance of FileReader and FileWriter.
            inputStream = new FileReader("data/inputdata.txt");
            outputStream = new FileWriter("data/outputdata.txt");

            int c;
            //Read data from file and write data into another file
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
        } catch (IOException ex) {
            Logger.getLogger(MidTermExam_Program2CharacterStreams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(MidTermExam_Program2CharacterStreams.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(MidTermExam_Program2CharacterStreams.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
