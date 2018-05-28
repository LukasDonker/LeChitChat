package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.util.ConnectionUtils;

public class ServerController extends Thread implements Runnable {

	private ServerSocket serverSocket;

	public ServerController() {
		start();
		new ChatController();
	}
	
	@Override
	public void run() {
		System.out.println("Server Start");
		while (true) {
			// While schleife um den server Stabil zu halten.
			try {
				// ServerSocket erstellen auf einem Port
				serverSocket = new ServerSocket(ConnectionUtils.PORT);

				// Schleife zur verarbeitung
				while (true) {
					System.out.println("Server waiting");
					// ServerSocket wartet bis Socket vom Client eingeht
					Socket mySocket = serverSocket.accept();
					// Weiterverarbeiten
					ChatController.startVerarbeitung(mySocket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void finalize() throws Throwable {
		if (serverSocket != null && serverSocket.isClosed()) {
			serverSocket.close();
		}
		System.out.println("Server geschlossen");
		super.finalize();
	}

	public static void main(String[] args) {
		new ServerController();

	}

}
