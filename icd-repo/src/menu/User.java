package menu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class User extends Client {
	private Element rootElement;
	private Element ingredients;
	private Element items;
	private Element menu;

	public User() {

	}

	@Override
	public void request(ObjectInputStream ois, ObjectOutputStream oos) {
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
		while (!connected) {
			System.out.println("Choose a command:\n");
			System.out.println("\t 1. Request Menu");
			System.out.println("\t 2. Order.");
			System.out.println("\t 3. Check order");
			System.out.println("\t 4. Pay order.");
			System.out.println("\t 5. Leave");
			System.out.print(">> ");
			switch (keyboard.nextInt()) {
			case 1:
				menu();
				break;
			case 2:
				order();
				break;
			case 3:
				check();
				break;
			case 4:
				pay();
				break;
			case 5:
				leave();
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
		new User().connect();
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
