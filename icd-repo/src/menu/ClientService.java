package menu;

import java.io.IOException;
import java.net.Socket;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ClientService extends Service {

	private Element requestElement;

	private Element clientElement;
	private Element debtElement;
	private double debt;

	public ClientService(Socket connection, Document menu, Document database) {
		super(connection, menu, database);
		String expression;
		try {
			expression = "//client[@id='1']"; // TODO client id
			clientElement = (Element) xPath.compile(expression).evaluate(database, XPathConstants.NODE);

			expression = "//client[@id='1']/debt"; // TODO client id
			debtElement = (Element) xPath.compile(expression).evaluate(database, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		debt = Double.parseDouble(debtElement.getTextContent());
	}

	public void run() {
		openStreams();
		String requestType = "";
		while (connected) {
			try {
				request = (Document) ois.readObject();
				requestElement = (Element) request.getDocumentElement().getLastChild();
				requestType = getRequestType(request);
				System.out.println("\n" + requestType + " request");
				System.out.println(docToString(request));
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
		return requestElement.getNodeName();
	}

	private void menu() throws XPathExpressionException, IOException {

		Element menuElem = doc.createElement("menu");
		responses.appendChild(menuElem);
		String expression;

		// get menu for certain day and type
		expression = "//" + requestElement.getAttribute("weekday") + "/" + requestElement.getAttribute("type")
				+ "/item";
		NodeList menuItems = (NodeList) xPath.compile(expression).evaluate(menu, XPathConstants.NODESET);

		for (int i = 0; i < menuItems.getLength(); i++) {
			Element menuItem = (Element) menuItems.item(i);

			// get names in certain language
			expression = "//item[@id='" + menuItem.getAttribute("itref") + "']/name/"
					+ requestElement.getAttribute("language") + "/text()";
			String name = (String) xPath.compile(expression).evaluate(menu, XPathConstants.STRING);
			Element menuItemNew = (Element) menuItem.cloneNode(true);
			doc.adoptNode(menuItemNew);
			menuItemNew.setAttribute("name", name);
			menuElem.appendChild(menuItemNew);
		}
		oos.reset();
		oos.writeObject(doc);
	}

	private void order() throws IOException, XPathExpressionException {

		// database update
		Element orderItems = (Element) requestElement.cloneNode(true);
		database.adoptNode(orderItems);
		orderItems.setAttribute("status", "accepted");
		clientElement.appendChild(orderItems);

		String expression = "sum(*/*[last()]/item/text())";
		String orderDebt = (String) xPath.compile(expression).evaluate(request, XPathConstants.STRING);
		debt += Double.parseDouble(orderDebt);

		debtElement.setTextContent(debt + "");

		FileManager fm = new FileManager();
		fm.load(database);
		fm.save(); // TODO update real database file

		// client response
		Element orderElem = doc.createElement("print");
		orderElem.appendChild(doc.createTextNode("Ordered successfully"));
		responses.appendChild(orderElem);

		oos.reset();
		oos.writeObject(doc);
	}

	private void check() throws IOException, XPathExpressionException {
		
		Element checkElement = doc.createElement("check");
		String expression = "//client[@id='1']/*";
		NodeList clientStatus = (NodeList) xPath.compile(expression).evaluate(database, XPathConstants.NODESET);
		//NodeList clientStatus = clientElement.getChildNodes();
		for (int i = 0; i < clientStatus.getLength(); i++) {
			Element item = (Element) clientStatus.item(i);
			Element itemNew = (Element) item.cloneNode(true);
			doc.adoptNode(itemNew);
			checkElement.appendChild(itemNew);
		}
		responses.appendChild(checkElement);
		
		oos.reset();
		oos.writeObject(doc);
	}

	private void pay() {

	}

	private void leave() {

	}
}
