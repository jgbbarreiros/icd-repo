package menu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public abstract class Client {

	public final static String DEFAULT_HOSTNAME = "localhost";
	public final static int DEFAULT_PORT = 5025;
	protected XPath xPath;
	protected FileManager fileManager;
	protected Document requests;
	protected Element rootElement;

	private Socket connection;
	protected ObjectInputStream ois;
	protected ObjectOutputStream oos;
	protected boolean connected;
	protected String clientType;

	public Client() {
		xPath = XPathFactory.newInstance().newXPath();
		fileManager = new FileManager();
		requests = fileManager.blank();
		rootElement = requests.createElement("requests");
		requests.appendChild(rootElement);
		connected = false;
	}

	public void connect() {
		try {
			connection = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
			oos = new ObjectOutputStream(connection.getOutputStream());
			ois = new ObjectInputStream(connection.getInputStream());
			connected = true;
			login();
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

	private void login() throws IOException {
		Document login = fileManager.blank();
		Element loginElement = login.createElement("login");
		login.appendChild(loginElement);

		Element clientElement = login.createElement("client");
		clientElement.appendChild(login.createTextNode(clientType));
		loginElement.appendChild(clientElement);

		oos.writeObject(login);
		oos.flush();
	}

	public abstract void request();

	public String docToString(Document doc) {
		DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		return lsSerializer.writeToString(doc);
	}
}
