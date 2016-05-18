package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientService extends Thread {

	private Socket connection;

	public ClientService(Socket connection) {
		this.connection = connection;
	}

	public void run() {

		BufferedReader is = null;
		PrintWriter os = null;

		boolean reading = true;

		try {

			System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

			is = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			os = new PrintWriter(connection.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("Erro na ligacao " + connection + ": " + e.getMessage());
		}
		while (reading) {
			String inputLine = null;
			try {
				inputLine = is.readLine();
			} catch (IOException e) {
				System.out.println("Client disconnected.");
				reading = false;
				continue;
			}
			System.out.println("Recebi -> " + inputLine);
			os.println("@" + inputLine.toUpperCase());
		}
		try {
			os.close();
			is.close();
		} catch (IOException e) {
			System.out.println("Exception while closing streams.");
		}
	}

}
