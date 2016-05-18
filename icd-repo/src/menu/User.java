package menu;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class User extends Client {
	private Element rootElement;
	private Element ingredients;
	private Element items;
	private Element menu;
	

	@Override
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
			System.out.println("\t 1. Request Menu");
			System.out.println("\t 2. Order.");
			System.out.println("\t 3. Check order");
			System.out.println("\t 4. Pay order.");
			System.out.println("\t 5. Leave");
			System.out.print(">> ");
			switch (keyboard.nextInt()) {
				
				case 1:
					os.println("I want the menu.");
					break;
				case 2:
					os.println("I want to order.");
					break;
				case 3:
					os.println("I want to check my order.");
					break;
				case 4:
					os.println("I want to pay my order.");
					break;
				case 5:
					os.println("I'm leaving.");
					exit = true;
					System.out.println("Bye");
					break;
				case 9:
					exit = true;
					System.out.println("Bye");
					break;
				case 0:
					try {
						oos.writeObject(doc);
					} catch (IOException e) {
						e.printStackTrace();
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
		new User().connect();
	}

}
