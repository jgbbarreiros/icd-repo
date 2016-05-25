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
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

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
		clientType = "user";
		keyboard = new Scanner(System.in);
		date = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		language = "en";
		weekday = calendar.get(Calendar.DAY_OF_WEEK) > 5 ? "weekday" : "restday";
		type = calendar.get(Calendar.HOUR_OF_DAY) < 19 ? "lunch" : "dinner";
	}

	@Override
	public void request() {
		System.out.println("User request connected = " + connected);
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
		weekday = calendar.get(Calendar.DAY_OF_WEEK) > 5 ? "weekday" : "restday";
		type = hour < 19 ? "lunch" : "dinner";
	}

	private Document requestMenu() throws IOException, ClassNotFoundException {
		Element menu = requests.createElement("menu");
		menu.setAttribute("language", language);
		menu.setAttribute("type", type);
		menu.setAttribute("weekday", weekday);
		rootElement.appendChild(menu);
		oos.reset();
		oos.writeObject(requests);
		return (Document) ois.readObject();
	}

	private void showMenu() {
		// TODO after menu response
		DOMImplementationLS domImplementation = (DOMImplementationLS) menu.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		System.out.println(lsSerializer.writeToString(menu));
	}

	private void order() throws DOMException, XPathExpressionException, IOException, ClassNotFoundException {
		if (menu == null) {
			System.out.println("You need to request a menu first");
			return;
		}
		System.out.println("\nInsert item id's separated by commas:");
		System.out.print(">> ");
		keyboard.nextLine();
		String[] orderList = keyboard.nextLine().trim().split(",");
		Element order = requests.createElement("order");
		rootElement.appendChild(order);
		String expression;
		for (int i = 0; i < orderList.length; i++) {
			expression = "//item[@itref='" + orderList[i] + "']";
			Element item = (Element) xPath.compile(expression).evaluate(menu, XPathConstants.NODE);
			Element itemNew = (Element) item.cloneNode(true);
			requests.adoptNode(itemNew);
			order.appendChild(itemNew);
		}
		oos.reset();
		oos.writeObject(requests);
		showOrder((Document) ois.readObject());
	}

	private void showOrder(Document order) {
		// TODO after order response
	}

	private void check() throws IOException, ClassNotFoundException {
		Element check = requests.createElement("check");
		rootElement.appendChild(check);

		Element status = requests.createElement("status");
		check.appendChild(status);

		Element debt = requests.createElement("debt");
		check.appendChild(debt);

		DOMImplementationLS domImplementation = (DOMImplementationLS) requests.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		System.out.println(lsSerializer.writeToString(requests));

		oos.reset();
		oos.writeObject(requests);
		showCheck((Document) ois.readObject());
	}

	private void showCheck(Document check) {
		// TODO after check response
		DOMImplementationLS domImplementation = (DOMImplementationLS) check.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		System.out.println(lsSerializer.writeToString(check));
		fileManager.saveAs(check, "check.xml");
	}

	private void pay() throws IOException, ClassNotFoundException {
		Element pay = requests.createElement("pay");
		rootElement.appendChild(pay);
		
		oos.reset();
		oos.writeObject(requests);
		showPay((Document) ois.readObject());
	}

	private void showPay(Document check) {
		// TODO after pay response
		DOMImplementationLS domImplementation = (DOMImplementationLS) check.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		System.out.println(lsSerializer.writeToString(check));
	}

	private void leave() throws IOException, ClassNotFoundException {
		Element leave = requests.createElement("leave");
		rootElement.appendChild(leave);
		
		oos.reset();
		oos.writeObject(requests);
		showLeave((Document) ois.readObject());
	}

	private void showLeave(Document check) {
		// TODO after leave response
		DOMImplementationLS domImplementation = (DOMImplementationLS) check.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		System.out.println(lsSerializer.writeToString(check));
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
