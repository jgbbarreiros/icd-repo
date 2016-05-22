package menu;

import java.net.Socket;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class WaiterService extends Service {

	public WaiterService(Socket connection, Document restaurant) {
		super(connection, restaurant);
	}

	public void run() {
		String requestType = "";
		while (connected) {
			try {
				requestType = getRequestType((Document) ois.readObject());
				switch (requestType) {
				case "orders":
					orders();
					break;
				case "update":
					update();
					break;
				case "aniversary":
					aniversary();
					break;
				case "leave":
					leave();
					connected = false;
					break;
				default:
					break;
				}

			} catch (Exception e) {
				System.out.println("Exception caught in WaiterService.run.");
				closeStreams();
			}
		}
	}

	protected String getRequestType(Document request) {

		String root = "//" + "" + "/name()";
		try {
			Node n = (Node) xPath.compile(root).evaluate(doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void orders() {

	}

	public void update() {

	}

	public void aniversary() {

	}

	private void leave() {

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
