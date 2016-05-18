package menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class ClientService extends Thread {

    private Socket connection;


    public ClientService(Socket connection) {
        this.connection = connection;
    }


    public void run() {

        BufferedReader is = null;
        PrintWriter os    = null;
        ObjectInputStream ois = null;
        Transformer transformer = null;

        try {

            System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

            is = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            os = new PrintWriter(connection.getOutputStream(), true);
            ois = new ObjectInputStream(connection.getInputStream());
            Document doc = null;
            try {
            	doc = (Document) ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e1) {
				e1.printStackTrace();
			}
            
            try {
    			DOMSource source = new DOMSource(doc);
    			StreamResult result = new StreamResult(new File("test.xml"));
    			transformer.transform(source, result);
    		} catch (TransformerException e) {
    			System.err.println(e.getMessage());
    		}
            
//            String inputLine = is.readLine();
//            System.out.println("Recebi -> " + inputLine);
//            os.println("@" + inputLine.toUpperCase());
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
