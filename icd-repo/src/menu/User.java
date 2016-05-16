package menu;

import java.io.PrintWriter;
import java.util.Scanner;

public class User extends Client {

	@Override
	public void request(PrintWriter os) {
		
		Scanner keyboard = new Scanner(System.in);;
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
