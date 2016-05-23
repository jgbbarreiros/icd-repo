package menu;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class WaiterService extends Service {

	public WaiterService(Socket connection, ObjectInputStream ois, ObjectOutputStream oos, Document menu,
			Document database) {
		super(connection, ois, oos, menu, database);
	}

	public void run() {
		String requestType = "";
		while (connected) {
			try {
				Document d = (Document) ois.readObject();
				requestType = getRequestType(d);
				switch (requestType) {
				case "clients":
					orders();
					break;
				case "update":
					update(d);
					break;
				case "aniversary":
					aniversary();
					break;
				default:
					break;
				}

			} catch (Exception e) {
				System.out.println("Exception caught in WaiterService.run.");
				e.printStackTrace();
				connected = false;
				closeStreams();
			}
		}
	}

	protected String getRequestType(Document request) {
		System.out.println("GETTING REQUEST TYPE.");
		System.out.println(docToString(request));
		return request.getDocumentElement().getFirstChild().getNodeName();
	}

	private void orders() {
		System.out.println("RETURNING ORDERS");
		try {
			// System.out.println((String)
			// xPath.compile("string(//client[@id='c1']//@status)").evaluate(database,
			// XPathConstants.STRING));
			oos.writeObject(database);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Document d) {
		System.out.println("RETURNING UPDATE");
		docToString(d);

		try {
			String client = (String) xPath.compile("//client/@id/text())").evaluate(database, XPathConstants.STRING);
			String order = (String) xPath.compile("//order/@id/text())").evaluate(database, XPathConstants.STRING);
			Node n = (Node) xPath.compile("//client[@id = " + client + "]/order[@id = " + order + "]")
					.evaluate(database, XPathConstants.NODE);
			System.out.println("Node > " + n.toString());
			oos.writeObject(database);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldnt get element.");
		}
	}

	public void aniversary() {
		System.out.println("RETURNING ANIVERSARY");
	}

	// ======================= EXAMPLE OF XPATH USAGE.
	// ===========================
	public boolean addItem(String name, String day, String type, float price) {
		String list = "//" + day + "/" + type;
		String itemID = "//item[text() = " + name + "]/@itemID";
		try {
			Node parent = (Node) xPath.compile(list).evaluate(responses, XPathConstants.NODE);
			String id = (String) xPath.compile(itemID).evaluate(responses, XPathConstants.STRING);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
