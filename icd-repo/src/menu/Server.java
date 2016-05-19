package menu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public final static int DEFAULT_PORT = 5025;

	public static void main(String[] args) {
		int port = DEFAULT_PORT;

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(port);

			Socket newSock = null;

			while (true) {
				System.out.println("Server listening on localhost:" + port + "...");

				newSock = serverSocket.accept();

				Thread th = new ClientService(newSock);
				th.start();
			}
		} catch (IOException e) {
			System.err.println("Exception in server: " + e);
		}
	}
}
