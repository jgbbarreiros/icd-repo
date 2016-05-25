package menu;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WaiterService extends Service {

	public WaiterService(Socket connection, ObjectInputStream ois, ObjectOutputStream oos, Document menu,
			Document database) {
		super(connection, ois, oos, menu, database);
	}

	public void run() {
		String requestType = "";
		while (connected) {
			responses = createRequestDocument();
			try {
				Document d = (Document) ois.readObject();
				requestType = getRequestType(d);
				switch (requestType) {
				case "users":
					orders();
					break;
				case "update":
					update(d);
					break;
				case "aniversary":
					aniversary();
					break;
				default:
					System.out.println("Waiter service defaulted.");
					break;
				}

			} catch (EOFException e) {
				System.out.println("Waiter disconnected.");
				connected = false;
				closeStreams();
			} catch (Exception e) {
				System.out.println("Exception caught in WaiterService.run.");
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
			oos.reset();
			oos.writeObject(database);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Document d) {
		System.out.println("RETURNING UPDATE");

		try {
			String userId = (String) xPath.compile("string(//user/@id)").evaluate(d, XPathConstants.STRING);
			String orderId = (String) xPath.compile("string(//order/@id)").evaluate(d, XPathConstants.STRING);
			String newStatus = (String) xPath.compile("string(//order/@status)").evaluate(d, XPathConstants.STRING);
			String currentStatus = (String) xPath.compile("string(//user[@id = '0']/order[@id='1']/@status)")
					.evaluate(database, XPathConstants.STRING);

			System.out.println("Attempting to change user id=\"" + userId + "\" order=\"" + orderId
					+ "\" with current status=\"" + currentStatus + "\" to new status=\"" + newStatus + "\"");

			String expression = "//user[@id = \"" + userId + "\"]/order[@id=\"" + orderId + "\"]";
			Element e = (Element) xPath.compile(expression).evaluate(database, XPathConstants.NODE);
			e.setAttribute("status", newStatus);

			System.out.println(docToString(database));
			Element c = responses.createElement("user");
			Element o = responses.createElement("order");
			c.setAttribute("id", userId);
			o.setAttribute("id", orderId);
			o.setAttribute("status", newStatus);
			c.appendChild(o);
			rootElement.appendChild(c);
			oos.writeObject(responses);
		} catch (XPathException e) {
			System.out.println("Couldnt get element.");
		} catch (Exception e) {
			System.out.println("Error while updating a status in WaiterService.update.");
		}
	}

	public void aniversary() {
		System.out.println("RETURNING ANIVERSARY");
	}
}
