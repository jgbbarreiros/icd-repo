package menu;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Waiter extends Client {

	private Element rootElement;
	private Element ingredients;
	private Element items;
	private Element menu;

	public void request(PrintWriter os, ObjectOutputStream oos) {

		FileManager fileManager = new FileManager();
		Document doc = fileManager.blank();
		rootElement = doc.createElement("restaurant");
		doc.appendChild(rootElement);

		ingredients = doc.createElement("ingredients");
		rootElement.appendChild(ingredients);

		items = doc.createElement("items");
		rootElement.appendChild(items);

		menu = doc.createElement("menu");
		rootElement.appendChild(menu);

		Scanner keyboard = new Scanner(System.in);
		boolean exit = false;
		while (!exit) {
			System.out.println("Choose a command:\n");
			System.out.println("\t 1. Check orders");
			System.out.println("\t 2. Modify status.");
			System.out.println("\t 3. Check aniversary.");
			System.out.println("\t 4. Leave");
			System.out.print(">> ");
			switch (keyboard.nextInt()) {

			case 1:
				os.println("I want to check the orders.");
				break;
			case 2:
				os.println("I want to modify a status.");
				break;
			case 3:
				os.println("I want to check aniversary.");
				break;
			case 4:
				os.println("I'm leaving.");
				exit = true;
				System.out.println("Bye");
				break;
			case 0:
				try {
					oos.writeObject(doc);
				} catch (IOException e) {
					System.out.println("Error sending document.");
				}
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

	private void checkOrders() {

	}

	public void modifyOrder() {

	}

	public void checkAniversary() {

	}

}
