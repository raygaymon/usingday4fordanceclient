package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NumberFormatException, UnknownHostException, IOException
    {
        String serverHost = args[0];
        String serverPort = args [1];

        Socket socket = new Socket (serverHost, Integer.parseInt(serverPort));

        //set up console input from keyboard
        Console cons = System.console();
        
        //variable to save keyboard inputs
        String keyboardInput = "";

        //variable to save msgReceived
        String msgReceived = "";

        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bisNuts = new BufferedInputStream(is);
            DataInputStream disNuts = new DataInputStream(bisNuts);

            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                //while loop
                while (!keyboardInput.equals("close")) {

                    keyboardInput = cons.readLine("Please insert $2 to receive your fortune: \n");

                    //send msg through to server
                    dos.writeUTF(keyboardInput);
                    dos.flush();

                    //receive message from server
                    //add received msg to msgreceived
                    msgReceived = disNuts.readUTF();
                    System.out.println("\n" + msgReceived + "\n\nHope you get your favourite fortune next time!\n");

                }

                dos.close();
                bos.close();
                os.close();

            } catch (EOFException ex) {

            }

            bisNuts.close();
            disNuts.close();
            is.close();

        } catch (EOFException ex) {
            ex.printStackTrace();
            System.exit(0);

        }
    }
}
