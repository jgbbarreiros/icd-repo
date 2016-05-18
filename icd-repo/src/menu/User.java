package menu;

import java.io.PrintWriter;
import java.util.Scanner;

public class User extends Client {
	
	Object menu;

	@Override
	public void request(PrintWriter os) {

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
