package menu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.w3c.dom.Document;

public class Server {

	public final static int DEFAULT_PORT = 5025;
	private static ServerSocket serverSocket = null;
	private static Document doc = null;

	public Server(String path) {
		FileManager fm = new FileManager();
		doc = fm.blank();
		fm.load(path);
	}

	private void launch() throws IOException {
		serverSocket = new ServerSocket(DEFAULT_PORT);
		Socket service = null;

		while (true) {
			System.out.println("Server listening on localhost:" + DEFAULT_PORT + "...");
			service = serverSocket.accept();
			Thread th = new ClientService(service, doc);
			th.start();
		}
	}

	public static void main(String[] args) {
		Server server = new Server("x");

		Server server1 = new Server("monapettit");
		Server server2 = new Server("praisetheburger");
		Server server3 = new Server("ricksavenue");
		Server server4 = new Server("dianasdiner");

		try {
			server.launch();
		} catch (IOException e) {
			System.err.println("Exception in server: " + e);
		}
	}
}
