package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        PrintWriter    os = null;
        
        try {
            socket = new Socket(host, port);

            System.out.println("Ligação: " + socket);

            os = new PrintWriter(socket.getOutputStream(), true); 
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //os.println("Olá mundo!!!");
            //System.out.println("Recebi -> " + is.readLine());
            request(os);

        } 
        catch (IOException e) {
            System.err.println("Erro na ligação " + e.getMessage());   
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
    
    public abstract void request(PrintWriter os);
}



