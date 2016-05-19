package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Client {

    public final static String DEFAULT_HOSTNAME = "localhost";
    
    public final static int DEFAULT_PORT = 5025; 
    
    
    public void connect() {

        String host = DEFAULT_HOSTNAME;
        int    port = DEFAULT_PORT;

        System.out.println("-> " + host + ":" + port );
        
        Socket socket     = null;
        BufferedReader is = null;
        PrintWriter    pw = null;
        OutputStream   os = null;
        ObjectOutputStream oos = null;
        
        try {
            socket = new Socket(host, port);

            System.out.println("Ligacao: " + socket);

            pw = new PrintWriter(socket.getOutputStream(), true); 
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            
            request(pw, oos);
        } 
        catch (IOException e) {
            System.err.println("Error in connection " + e.getMessage());   
        }
        finally {
            try {
                if (os != null) os.close();
                if (is != null) is.close();
                if (socket != null ) socket.close();
            }
            catch (IOException e) { 
            }
        }
    }
    
    public abstract void request(PrintWriter os, ObjectOutputStream oos);
}



