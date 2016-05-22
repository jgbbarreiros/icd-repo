package menu;

import java.net.Socket;

import org.w3c.dom.Document;

public class ClientService extends Service {

	public ClientService(Socket connection, Document menu, Document database) {
		super(connection, menu, database);
	}

	public void run() {
		openStreams();
		String requestType = "";
		while (connected) {
			try {
				requestType = getRequestType((Document) ois.readObject());
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

	private void menu() {
		System.out.println("hello");
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
