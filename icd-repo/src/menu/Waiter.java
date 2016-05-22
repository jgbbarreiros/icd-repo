package menu;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Waiter extends Client {

	public Waiter() {
		super();
	}

	public void request() {
		System.out.println("waiter requested");

		Scanner keyboard = new Scanner(System.in);
		while (connected) {
			System.out.println("Choose a command:\n");
			System.out.println("\t 1. Check orders");
			System.out.println("\t 2. Modify status.");
			System.out.println("\t 3. Check aniversary.");
			System.out.println("\t 4. Leave");
			System.out.print(">> ");
			switch (keyboard.nextInt()) {
			case 1:
				orders();
				break;
			case 2:
				update();
				break;
			case 3:
				aniversary();
				break;
			case 4:
				connected = false;
				System.out.println("Bye");
				break;
			default:
				System.out.println("Please choose a valid option.");
				break;
			}
		}
		keyboard.close();
	}

	private void orders() {
		Element clients = doc.createElement("clients");
		requests.appendChild(clients);
		try {
			oos.writeObject(doc);
			// Reads the whole clients list and orders.
			doc = (Document) ois.readObject();
			System.out.println(docToString(doc));
			System.out.println(
					(String) xPath.compile("string(//client[@id='c1']//@status)").evaluate(doc, XPathConstants.STRING));
		} catch (Exception e) {
			System.out.println("Exception caught in Waiter.orders.");
			e.printStackTrace();
		}
	}

	public void update() {
		System.out.println(docToString(doc));

		Scanner keyboard = new Scanner(System.in);
		boolean invalid = true;
		System.out.print("Insert client ID: > ");
		String cid = Integer.toString(keyboard.nextInt());
		System.out.print("Insert order ID: > ");
		String oid = Integer.toString(keyboard.nextInt());
		String status = "";
		while (invalid) {
			System.out.println("Choose new status for client \"" + cid + "\" with order id \"" + oid + "\":\n");
			System.out.println("\t 1. Accepted");
			System.out.println("\t 2. Ready.");
			System.out.println("\t 3. Delivered.");
			System.out.println("\t 4. Complete");
			System.out.print(">> ");
			switch (keyboard.nextInt()) {
			case 1:
				status = "accepted";
				invalid = false;
				break;
			case 2:
				status = "ready";
				invalid = false;
				break;
			case 3:
				status = "delivered";
				invalid = false;
				break;
			case 4:
				status = "complete";
				invalid = false;
				break;
			default:
				System.out.println("Please choose a valid status.");
			}
		}
		keyboard.close();

		FileManager fm = new FileManager();
		Document request = fm.blank();
		Element e = request.createElement("update");
		Element c = request.createElement("client");
		c.setAttribute("id", cid);
		Element o = request.createElement("order");
		o.setAttribute("id", oid);
		o.setAttribute("status", status);
		c.appendChild(o);
		e.appendChild(c);
		request.appendChild(e);
		//request.appendChild(e);
		//request.adoptNode(c);

		System.out.println(docToString(request));
		try {
			oos.writeObject(request);
			// Reads the order to know if it was updated.
			// doc = (Document) ois.readObject();
		} catch (Exception e1) {
			System.out.println("Exception caught in Waiter.update.");
		}
	}

	public void aniversary() {
		try {
			oos.writeObject(doc);

		} catch (IOException e) {
			System.out.println("Exception caught in Waiter.aniversary.");
		}
	}

	public static void main(String[] args) {
		Waiter waiter = new Waiter();
		waiter.connect();
	}
}
