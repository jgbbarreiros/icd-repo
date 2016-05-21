package menu;

import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Waiter extends Client {

	private Element rootElement;
	private Element ingredients;
	private Element items;
	private Element menu;

	public void request() {

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

	}

	public void update() {

	}

	public void aniversary() {

	}

}
