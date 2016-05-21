package menu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Client {

	public final static String DEFAULT_HOSTNAME = "localhost";
	public final static int DEFAULT_PORT = 5025;
	protected Document doc = null;
	private Socket connection = null;
	protected ObjectInputStream ois = null;
	protected ObjectOutputStream oos = null;
	protected boolean connected = true;
	protected Element requests;

	public Client() {
		FileManager fileManager = new FileManager();
		doc = fileManager.blank();
		requests = doc.createElement("requests");
		doc.appendChild(requests);
	}

	public void connect() {
		try {
			connection = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			oos = new ObjectOutputStream(connection.getOutputStream());
			ois = new ObjectInputStream(connection.getInputStream());
			request();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (oos != null)
					oos.close();
				if (connection != null)
					connection.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

	}

	public abstract void request();
}
