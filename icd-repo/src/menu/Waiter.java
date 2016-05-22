package menu;

import java.io.IOException;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Waiter extends Client {

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
		} catch (Exception e) {
			System.out.println("Exception caught in Waiter.orders.");
		}
	}

	public void update() {
		Scanner keyboard = new Scanner(System.in);
		boolean invalid = true;
		System.out.print("Insert order id: > ");
		int orderId = keyboard.nextInt();
		String id = "o" + orderId;
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
		keyboard.close();

		Element order = doc.createElement("order");
		order.setAttribute("id", id);
		order.setAttribute("status", status);
		requests.appendChild(order);
		System.out.println("Document >> \n" + doc);
		try {
			oos.writeObject(doc);
			// Reads the order to know if it was updated.
			// doc = (Document) ois.readObject();
		} catch (Exception e) {
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
