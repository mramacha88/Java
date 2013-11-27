/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermexam_pragram2bytestreams;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MeghaVinay
 */
public class MidTermExam_Pragram2ByteStreams {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            //instance of FileInputStream and FileOutputStream.
            in = new FileInputStream("data/inputdata.txt");
            out = new FileOutputStream("data/outputdata.txt");
            int c;
            //Read and Write data.
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } catch (IOException ex) {
            Logger.getLogger(MidTermExam_Pragram2ByteStreams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Close file pointer after job is done
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(MidTermExam_Pragram2ByteStreams.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(MidTermExam_Pragram2ByteStreams.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    

    }
}
