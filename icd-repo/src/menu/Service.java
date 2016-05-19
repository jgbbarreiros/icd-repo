package menu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.w3c.dom.Document;

public abstract class Service extends Thread {

	protected Socket connection;
	protected boolean connected = false;
	protected Document doc = null;
	protected Document restaurant = null;
	protected ObjectInputStream ois = null;
	protected ObjectOutputStream oos = null;

	public Service(Socket connection, Document restaurant) {
		this.restaurant = restaurant;
		this.connection = connection;
		this.connected = true;
		openStreams();
	}

	private void openStreams() {
		try {
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
	}

	public abstract void run();

	protected abstract String getRequestType(Document doc);

}