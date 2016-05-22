package menu;

import java.net.Socket;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class WaiterService extends Service {

	public WaiterService(Socket connection, Document restaurant, Document database) {
		super(connection, restaurant, database);
	}

	public void run() {
		openStreams();
		String requestType = "";
		while (connected) {
			try {
				requestType = getRequestType((Document) ois.readObject());
				switch (requestType) {
				case "clients":
					orders();
					break;
				case "update":
					update();
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
		return request.getDocumentElement().getFirstChild().getNodeName();
	}

	private void orders() {
		System.out.println("RETURNING ORDERS");
		
	}

	public void update() {
		System.out.println("RETURNING UPDATE");
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
			Node parent = (Node) xPath.compile(list).evaluate(doc, XPathConstants.NODE);
			String id = (String) xPath.compile(itemID).evaluate(doc, XPathConstants.STRING);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
