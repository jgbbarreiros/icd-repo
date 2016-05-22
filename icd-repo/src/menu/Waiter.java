package menu;

import java.io.IOException;
import java.util.Scanner;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Waiter extends Client {

	private FileManager fp;
	private Element root;

	public void request() {

		fp = new FileManager();
		Document doc = fp.blank();
		root = doc.createElement("clients");
		doc.appendChild(root);

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

	public static void main(String[] args) {
		new Waiter().connect();
	}

	private void orders() {
		doc = fp.blank();
		root = doc.createElement("clients");
		doc.appendChild(root);
		try {
			oos.writeObject(doc);
			// Reads the whole clients list and orders.
			doc = (Document) ois.readObject();
		} catch (Exception e) {
			System.out.println("Exception caught in Waiter.orders.");
		}
	}

	public void update() {
		Scanner keyboard = new Scanner(System.in);
		boolean invalid = true;
		System.out.print("Insert order id: > ");
		String id = "o" + keyboard.nextInt();
		String status = "";
		while (invalid) {
			System.out.println("Choose new status for order id \"" + id + "\":\n");
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
		System.out.print(" -> status");
		keyboard.close();

		doc = fp.blank();
		root = doc.createElement("order");
		doc.appendChild(root);
		Attr attrId = doc.createAttribute("id");
		attrId.setValue(id);
		root.setAttributeNode(attrId);
		Attr attrStatus = doc.createAttribute("status");
		attrStatus.setValue(status);
		root.setAttributeNode(attrStatus);
		try {
			oos.writeObject(doc);
			// Reads the order to know if it was updated.
			doc = (Document) ois.readObject();
		} catch (Exception e) {
			System.out.println("Exception caught in Waiter.orders.");
		}
	}

	public void aniversary() {
		try {
			oos.writeObject(doc);

		} catch (IOException e) {
			System.out.println("Exception caught in Waiter.orders.");
		}
	}

}
