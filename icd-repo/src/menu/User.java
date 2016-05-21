package menu;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.w3c.dom.Element;

public class User extends Client {
	private Element rootElement;
	private Element ingredients;
	private Element items;
	private Element menu;

	private Calendar calendar;
	private Date date;
	private int year;
	private int month;
	private int day;
	private int hour;
	private boolean weekday;
	private String language;
	private Scanner keyboard;
	private boolean lunch;

	public User() {
		keyboard = new Scanner(System.in);
		// get var type >> var.getClass().getName();
		date = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		language = "eng";
		weekday = calendar.get(Calendar.DAY_OF_WEEK) > 5 ? false : true;
	}

	@Override
	public void request() {
		FileManager fileManager = new FileManager();
		doc = fileManager.blank();
		rootElement = doc.createElement("restaurant");
		doc.appendChild(rootElement);

		ingredients = doc.createElement("ingredients");
		rootElement.appendChild(ingredients);

		items = doc.createElement("items");
		rootElement.appendChild(items);

		menu = doc.createElement("menu");
		rootElement.appendChild(menu);

		while (connected) {
			System.out.println("\n============================");
			System.out.println(calendar.getTime());
			System.out.println("\nChoose a command:");
			System.out.println("\t1. Request Menu");
			System.out.println("\t2. Order.");
			System.out.println("\t3. Check order");
			System.out.println("\t4. Pay order.");
			System.out.println("\t5. Leave");
			System.out.print(">> ");
			switch (keyboard.nextInt()) {
			case 1:
				setDate();
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
		User user = new User();
		user.connect();
	}

	private void menu(/* String language, String type, boolean weekday */) {

	}

	private void setDate() {
		System.out.println("\nChoose a command:");
		System.out.println("\t1. Request Menu");
		System.out.println("\t2. Order.");
		System.out.println("\t5. back");
		System.out.print(">> ");

		System.out.print("year = ");
		year = keyboard.nextInt();
		System.out.print("month = ");
		month = keyboard.nextInt() - 1;
		System.out.print("day = ");
		day = keyboard.nextInt();
		System.out.print("hour = ");
		hour = keyboard.nextInt();

		calendar.set(year, month, day, hour, 0);
		System.out.println(calendar.getTime());

		weekday = calendar.get(Calendar.DAY_OF_WEEK) > 5 ? false : true;
		lunch = hour < 19 ? true : false;

		System.out.println("weeday: " + weekday);
		System.out.println("lunch: " + lunch);

	}

	private void order() {
		try {
			oos.writeObject("ola");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void check() {

	}

	private void pay() {

	}

	private void leave() {

	}

}
