package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientService extends Thread {

    private Socket connection;


    public ClientService(Socket connection) {
        this.connection = connection;
    }


    public void run() {

        BufferedReader is = null;
        PrintWriter os    = null;

        try {

            System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

            is = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            os = new PrintWriter(connection.getOutputStream(), true);

            String inputLine = is.readLine(); 

            System.out.println("Recebi -> " + inputLine);

            os.println("@" + inputLine.toUpperCase());
        }
        catch (IOException e) {
            System.err.println("erro na ligaçao " + connection + ": " + e.getMessage());
        }
        finally {
            try {
                if (is != null) is.close();  
                if (os != null) os.close();

                if (connection != null) connection.close();                    
            } catch (IOException e) { }
        }
    }

}
