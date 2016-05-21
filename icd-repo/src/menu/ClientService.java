package menu;

import java.net.Socket;

import org.w3c.dom.Document;

public class ClientService extends Service {

	public ClientService(Socket connection, Document restaurant) {
		super(connection, restaurant);
	}

	public void run() {
		openStreams();
		String str = "";
		String requestType = "";
		while (connected) {
			try {
				requestType = getRequestType(doc);
				str = (String) ois.readObject();
				System.out.println(str);
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
				connected = false;
			}
		}

		closeStreams();
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
