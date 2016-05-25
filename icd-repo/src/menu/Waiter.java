package menu;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Waiter extends Client {

	private Scanner keyboard;

	public Waiter() {
		super();
		clientType = "waiter";
		keyboard = new Scanner(System.in);
	}

	public void request() {
		System.out.println("waiter requested");

		while (connected) {
			requests = createRequestDocument();

			System.out.println("Choose a command:\n");
			System.out.println("\t 1. Check orders");
			System.out.println("\t 2. Modify status.");
			System.out.println("\t 3. Check aniversary.");
			System.out.println("\t 4. Leave");
			System.out.print(">> ");
			int choice = keyboard.nextInt();
			switch (choice) {
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
		Element users = requests.createElement("users");
		rootElement.appendChild(users);
		try {
			oos.writeObject(requests);
			// Reads the whole clients list and orders.
			Document response = (Document) ois.readObject();
			System.out.println(docToString(response));
			System.out.println((String) xPath.compile("string(//user[@id='c1']//@status)").evaluate(response,
					XPathConstants.STRING));
		} catch (Exception e) {
			System.out.println("Exception caught in Waiter.orders.");
			e.printStackTrace();
		}
	}

	public void update() {
		Element e = requests.createElement("update");
		rootElement.appendChild(e);
		System.out.println(docToString(requests));

		boolean invalid = true;
		System.out.print("Insert user ID: > ");
		String cid = Integer.toString(keyboard.nextInt());

		Element c = requests.createElement("user");
		c.setAttribute("id", cid);
		e.appendChild(c);
		System.out.println(docToString(requests));

		System.out.print("Insert order ID: > ");
		String oid = Integer.toString(keyboard.nextInt());
		String status = "";
		while (invalid) {
			System.out.println("Choose new status for user \"" + cid + "\" with order id \"" + oid + "\":\n");
			System.out.println("\t 1. Accepted");
			System.out.println("\t 2. Ready.");
			System.out.println("\t 3. Delivered.");
			System.out.println("\t 4. Complete");
			System.out.print(">> ");
			int choice = keyboard.nextInt();
			switch (choice) {
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
		try {
			Element o = requests.createElement("order");
			o.setAttribute("id", oid);
			o.setAttribute("status", status);
			c.appendChild(o);
			System.out.println(docToString(requests));

			oos.writeObject(requests);
			Document d = (Document) ois.readObject();
			System.out.println(docToString(d));
			String s = (String) xPath.compile("string(//order/@status)").evaluate(d, XPathConstants.STRING);
			System.out.println("s is = " + s);
			if (status.equals(s)) {
				System.out.println("User's order updated succesfully.");
			} else {
				System.out.println("Could not update user's order.");
			}
		} catch (Exception e1) {
			System.out.println("Exception caught in Waiter.update.");
		}
	}

	public void aniversary() {
		try {
			oos.writeObject(requests);

		} catch (IOException e) {
			System.out.println("Exception caught in Waiter.aniversary.");
		}
	}

	public static void main(String[] args) {
		Waiter waiter = new Waiter();
		waiter.connect();
	}
}
