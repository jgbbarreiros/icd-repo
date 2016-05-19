package menu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.w3c.dom.Document;

public abstract class Client {

	public final static String DEFAULT_HOSTNAME = "localhost";
	public final static int DEFAULT_PORT = 5025;
	private Document doc = null;
	private Socket connection = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	protected boolean connected = true;

	public Client() {

	}

	public void connect() {
		try {
			connection = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			ois = new ObjectInputStream(this.connection.getInputStream());
			oos = new ObjectOutputStream(this.connection.getOutputStream());
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

		request(ois, oos);

	}

	public abstract void request(ObjectInputStream ois, ObjectOutputStream oos);
}
