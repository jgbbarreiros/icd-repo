package menu;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.w3c.dom.Element;

public class User extends Client {

	private Element menu;
	private Calendar calendar;
	private Date date;
	private int year;
	private int month;
	private int day;
	private int hour;
	private String weekday;
	private String language;
	private Scanner keyboard;
	private String type;

	public User() {
		super();
		keyboard = new Scanner(System.in);
		// get var type >> var.getClass().getName();
		date = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		language = "eng";
		weekday = calendar.get(Calendar.DAY_OF_WEEK) > 5 ? "yes" : "no";
		type = calendar.get(Calendar.HOUR_OF_DAY) < 19 ? "lunch" : "dinner";
	}

	@Override
	public void request() {

		while (connected) {
			try {
				System.out.println("\n============================");
				System.out.println(calendar.getTime());
				menuOptions(new String[] { "Request Menu", "Order", "Check order", "Pay order", "Leave" });
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
			} catch (IOException e) {
				connected = false;
			}
		}
		keyboard.close();
	}

	public static void main(String[] args) {
		User user = new User();
		// user.request();
		user.connect();
	}

	private void menu() throws IOException {

		menuOptions(new String[] { "Current date", "Other date", "Back" });
		switch (keyboard.nextInt()) {
		case 1:
			break;
		case 2:
			setDate();
			break;
		case 3:
			return;
		default:
			System.out.println("Please choose a valid option.");
			break;
		}
		requestMenu();
	}

	private void setDate() {
		System.out.print("year = ");
		year = keyboard.nextInt();
		System.out.print("month = ");
		month = keyboard.nextInt() - 1;
		System.out.print("day = ");
		day = keyboard.nextInt();
		System.out.print("hour = ");
		hour = keyboard.nextInt();
		calendar.set(year, month, day, hour, 0);
		weekday = calendar.get(Calendar.DAY_OF_WEEK) > 5 ? "yes" : "no";
		type = hour < 19 ? "lunch" : "dinner";
	}

	private void requestMenu() throws IOException {
		menu = doc.createElement("ingredients");
		menu.setAttribute("language", language);
		menu.setAttribute("type", type);
		menu.setAttribute("weekday", weekday);
		requests.appendChild(menu);
		oos.writeObject(doc);
	}

	private void order() {
		try {
			oos.writeObject("ola");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void menuOptions(String[] options) {
		System.out.println("\nChoose a command:");
		int number = 1;
		for (String option : options) {
			System.out.println("\t" + number++ + ". " + option);
		}
		System.out.print(">> ");
	}

	private void check() {

	}

	private void pay() {

	}

	private void leave() {

	}

}
