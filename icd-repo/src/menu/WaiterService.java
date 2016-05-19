package menu;

import java.net.Socket;

import org.w3c.dom.Document;

public class WaiterService extends Service {

	public WaiterService(Socket connection, Document restaurant) {
		super(connection, restaurant);
	}

	public void run() {
		String requestType = "";
		while (connected) {
			try {
				doc = (Document) ois.readObject();
				requestType = getRequestType(doc);
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

			}
		}
	}

	protected String getRequestType(Document doc) {
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

}
