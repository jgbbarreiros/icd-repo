package menu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class Server {

	public final static int DEFAULT_PORT = 5025;
	private static ServerSocket serverSocket = null;
	private static Document doc = null;
	private static Document menu = null;
	private static Document database = null;

	public Server(String path) {
		loadFiles(path);
	}

	private void loadFiles(String path) {
		FileManager fm = new FileManager();
		XPath xPath = XPathFactory.newInstance().newXPath();
		doc = fm.blank();
		doc = fm.load(path);
		try {
			String menuPath = (String) xPath.compile("string(//menu/@path)").evaluate(doc, XPathConstants.STRING);
			String databasePath = (String) xPath.compile("string(//database/@path)").evaluate(doc,
					XPathConstants.STRING);
			menu = fm.blank();
			menu = fm.load(menuPath);
			database = fm.blank();
			database = fm.load(databasePath);
			// System.out.println((String) xPath.compile("//item[@itID=
			// 'it1']/name/en/text()").evaluate(menu, XPathConstants.STRING));
			// System.out.println((String)
			// xPath.compile("string(//client[@id='c1']//@status)").evaluate(database,
			// XPathConstants.STRING));
		} catch (XPathExpressionException e) {
			System.out.println("Could not find specified files.");
		}
	}

	private void launch() throws IOException {
		serverSocket = new ServerSocket(DEFAULT_PORT);
		Socket service = null;

		while (true) {

			 System.out.println("Server listening on localhost:" +
			 DEFAULT_PORT + "...");
			 service = serverSocket.accept();
			 Thread th = new ClientService(service, menu, database);
			 th.start();

			// WAITS FOR A WAITER TO CONNECT
//			System.out.println("Server listening on localhost:" + DEFAULT_PORT + "...");
//			service = serverSocket.accept();
//			Thread th2 = new WaiterService(service, menu, database);
//			th2.start();
			break;
		}
	}

	public static void main(String[] args) {
		Server server = new Server("monapettit.xml");

		// Server server1 = new Server("monapettit");
		// Server server2 = new Server("praisetheburger");
		// Server server3 = new Server("ricksavenue");
		// Server server4 = new Server("dianasdiner");

		try {
			server.launch();
		} catch (IOException e) {
			System.err.println("Exception in server: " + e);
		}
	}
}
