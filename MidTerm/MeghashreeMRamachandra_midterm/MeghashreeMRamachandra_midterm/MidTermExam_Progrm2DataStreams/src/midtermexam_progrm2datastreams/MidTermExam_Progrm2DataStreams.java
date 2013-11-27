/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermexam_progrm2datastreams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 // @author Meghashree M Ramachandra
 
public class MidTermExam_Progrm2DataStreams {    
    public static void main(String[] args) {
        int integerValue = 1;
        String StrinValue = "Hello";
        float floatValue = 10.50f;
        try {            
            // Create an instance of FileOutputStream with primitivedata.dat
            // as the file name to be created. Then we pass the input
            // stream object in the DataOutputStream constructor.            
            FileOutputStream fos = new FileOutputStream("data/primitivedata.dat");
            DataOutputStream dos = new DataOutputStream(fos);           
            //  write  data to the primitivedata.dat.
            // using dataoutputstream methods writeInt(), writeFloat(), writeUTF().          
            dos.writeInt(integerValue);
            dos.writeUTF(StrinValue);           
            dos.writeFloat(floatValue);
            dos.flush();
            dos.close();           
           //read back data and display it. 
            // Using DataInputStream methods readInt(), readFloat(),readUTF(), etc.          
            FileInputStream fis = new FileInputStream("data/primitivedata.dat");
            DataInputStream dis = new DataInputStream(fis);           
            // Read the data            
            int integerValueResult = dis.readInt();
            System.out.println("integer: " + integerValueResult);
            String StringValueResult = dis.readUTF();
            System.out.println("string: " + StringValueResult);           
            float floatValueResult = dis.readFloat();
            System.out.println("float: " + floatValueResult);           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
