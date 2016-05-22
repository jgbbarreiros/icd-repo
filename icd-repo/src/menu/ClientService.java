package menu;

import java.io.IOException;
import java.net.Socket;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ClientService extends Service {

	public ClientService(Socket connection, Document menu, Document database) {
		super(connection, menu, database);
	}

	public void run() {
		openStreams();
		String requestType = "";
		while (connected) {
			try {
				request = (Document) ois.readObject();
				requestType = getRequestType(request);
				switch (requestType) {
				case "menu":
					menu();
					break;
				case "order":
					order();
					break;
				case "check":
					check();
					break;
				case "pay":
					pay();
					break;
				case "leave":
					leave();
					connected = false;
					break;
				default:
					System.out.println("Unkown request");
					break;
				}

			} catch (Exception e) {
				System.err.println(e.getMessage());
				connected = false;
			}
		}
		closeStreams();
	}

	protected String getRequestType(Document request) {
		return request.getDocumentElement().getLastChild().getNodeName();
	}

	private void menu() throws XPathExpressionException, IOException {
		System.out.println("menu");

		Element tag = (Element) request.getDocumentElement().getLastChild();
		
		Element menuElem = doc.createElement("menu");
		responses.appendChild(menuElem);
		String expression;
		// get menu for certain day and type

		expression = "//" + tag.getAttribute("weekday") + "/" + tag.getAttribute("type") + "/item";
		NodeList menuItems = (NodeList) xPath.compile(expression).evaluate(menu, XPathConstants.NODESET);

		for (int i = 0; i < menuItems.getLength(); i++) {
			// Node n = menuItems.item(i);
			Element menuItem = (Element) menuItems.item(i);

			// get names in certain language
			expression = "//item[@id='" + menuItem.getAttribute("itref") + "']/name/" + tag.getAttribute("language") + "/text()";
			String name = (String) xPath.compile(expression).evaluate(menu, XPathConstants.STRING);
			Element menuItemNew = (Element) menuItem.cloneNode(true);
			doc.adoptNode(menuItemNew);
			menuItemNew.setAttribute("name", name);
			menuElem.appendChild(menuItemNew);
		}
		oos.writeObject(doc);
	}

	private void order() {

	}

	private void check() {

	}

	private void pay() {

	}

	private void leave() {

	}
}
