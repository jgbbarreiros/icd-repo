package menu;

import java.net.Socket;

import org.w3c.dom.Document;

public class ClientService extends Service {

	public ClientService(Socket connection, Document restaurant) {
		super(connection, restaurant);
	}

	public void run() {
		String requestType = "";

		while (connected) {
			try {
				doc = (Document) ois.readObject();
				requestType = getRequestType(doc);
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
					break;
				}

			} catch (Exception e) {

			}
		}
	}

	protected String getRequestType(Document doc) {
		return null;
	}

	private void menu() {

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
