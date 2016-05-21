package menu;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class User extends Client {

	private Document menu;
	private Calendar calendar;
	private Date date;
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
			} catch (Exception e) {
				System.err.println(e.getMessage());
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

	private void menu() throws ClassNotFoundException, IOException {

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
		menu = requestMenu();
		showMenu();
	}

	private void setDate() {
		System.out.print("year = ");
		int year = keyboard.nextInt();
		System.out.print("month = ");
		int month = keyboard.nextInt() - 1;
		System.out.print("day = ");
		int day = keyboard.nextInt();
		System.out.print("hour = ");
		int hour = keyboard.nextInt();
		calendar.set(year, month, day, hour, 0);
		weekday = calendar.get(Calendar.DAY_OF_WEEK) > 5 ? "yes" : "no";
		type = hour < 19 ? "lunch" : "dinner";
	}

	private Document requestMenu() throws IOException, ClassNotFoundException {
		Element menu = doc.createElement("menu");
		menu.setAttribute("language", language);
		menu.setAttribute("type", type);
		menu.setAttribute("weekday", weekday);
		requests.appendChild(menu);
		oos.writeObject(doc);
		return (Document) ois.readObject();
	}

	private void showMenu() {
		// TODO after menu response
	}

	private void order() throws DOMException, XPathExpressionException {
		System.out.println("\nInsert item id's separated by commas:");
		System.out.print(">> ");
		keyboard.nextLine();
		String[] orderList = keyboard.nextLine().trim().split(",");
		Element order = doc.createElement("order");
		requests.appendChild(order);
		String expression;
		for (int i = 0; i < orderList.length; i++) {
			expression = "//item[@itemRef='" + orderList[i] + "']/@name";
			order.appendChild((Node) xPath.compile(expression).evaluate(menu, XPathConstants.NODE));
		}
	}

	private void check() {
		
	}

	private void pay() {

	}

	private void leave() {

	}
	
	private void menuOptions(String[] options) {
		System.out.println("\nChoose a command:");
		int number = 1;
		for (String option : options) {
			System.out.println("\t" + number++ + ". " + option);
		}
		System.out.print(">> ");
	}

}
