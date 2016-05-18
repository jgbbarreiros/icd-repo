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
		while(!exit) {
			System.out.println("Choose a command:\n");
			System.out.println("\t 1. Say Hello");
			System.out.println("\t 9. Exit");
			System.out.print(">> ");
			switch (keyboard.nextInt()) {
				case 1:
					os.println("Hello!");
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
					break;
			}
		}
		keyboard.close();
		
	}
	
	public static void main(String[] args) {
		new User().connect();
    }

}
