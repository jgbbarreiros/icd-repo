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
	private Scanner keyboard;

	public User() {
		super();
		clientType = "user";
		keyboard = new Scanner(System.in);
		date = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	@Override
	public void request() {
		sendData();
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
		
		// choose language
		menuOptions(new String[] { "english", "portugu�s", "fran�ais", "Exit" });
		String language = null;
		switch (keyboard.nextInt()) {
		case 1:
			language = "en";
			break;
		case 2:
			language = "pt";
			break;
		case 3:
			language = "fr";
			break;
		case 4:
			return;
		default:
			System.out.println("Please choose a valid option.");
			break;
		}
		
		// choose date
		menuOptions(new String[] { "Current date", "Other date", "Exit" });
		String type = null;
		String weekday = null;
		switch (keyboard.nextInt()) {
		case 1:
			type = calendar.get(Calendar.HOUR_OF_DAY) < 19 ? "lunch" : "dinner";
			weekday = calendar.get(Calendar.DAY_OF_WEEK) > 6 || calendar.get(Calendar.DAY_OF_WEEK) == 1 ? "restday"
					: "weekday";
			menu = requestMenu(language, type, weekday);
			showMenu(menu);
			break;
		case 2:
			Calendar cal = setDate();
			type = cal.get(Calendar.HOUR_OF_DAY) < 19 ? "lunch" : "dinner";
			weekday = calendar.get(Calendar.DAY_OF_WEEK) > 6 || calendar.get(Calendar.DAY_OF_WEEK) == 1 ? "restday"
					: "weekday";
			showMenu(requestMenu(language, type, weekday));
			break;
		case 3:
			return;
		default:
			System.out.println("Please choose a valid option.");
			break;
		}
	}

	private Calendar setDate() {
		System.out.print("year = ");
		int year = keyboard.nextInt();
		System.out.print("month = ");
		int month = keyboard.nextInt() - 1;
		System.out.print("day = ");
		int day = keyboard.nextInt();
		System.out.print("hour = ");
		int hour = keyboard.nextInt();
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, 0);
		return cal;
	}

	private Document requestMenu(String language, String type, String weekday) throws IOException, ClassNotFoundException {
		Element menu = requests.createElement("menu");
		menu.setAttribute("language", language);
		menu.setAttribute("type", type);
		menu.setAttribute("weekday", weekday);
		rootElement.appendChild(menu);
		oos.reset();
		oos.writeObject(requests);
		return (Document) ois.readObject();
	}

	private void showMenu(Document menu) {
		// TODO after menu response
		System.out.println(docToString(menu));
	}

	private void order() throws DOMException, XPathExpressionException, IOException, ClassNotFoundException {
		if (menu == null) {
			System.out.println("You need to request today's menu first");
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
		System.out.println(docToString(order));
	}

	private void check() throws IOException, ClassNotFoundException {
		Element check = requests.createElement("check");
		rootElement.appendChild(check);

		Element status = requests.createElement("status");
		check.appendChild(status);

		Element debt = requests.createElement("debt");
		check.appendChild(debt);

		oos.reset();
		oos.writeObject(requests);
		showCheck((Document) ois.readObject());
	}

	private void showCheck(Document check) {
		// TODO after check response
		System.out.println(docToString(check));
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

	private void sendData() {
		Document d = fileManager.blank();
		Element data = d.createElement("data");
		String input = "";
		menuOptions(new String[] { "My birthday is today", "My birthday is NOT today" });
		switch (keyboard.nextInt()) {
		case 1:
			input = "yes";
			break;
		case 2:
			input = "no";
		}
		data.setAttribute("birthday", input);
		d.appendChild(data);
		try {
			oos.writeObject(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
