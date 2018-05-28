package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.util.ConnectionUtils;

public class ServerController {

	private ServerSocket serverSocket;

	public ServerController() throws IOException {
		System.out.println("Server Start");
		while (true) {
			//While schleife um den server Stabil zu halten.
			try {
				//ServerSocket erstellen auf einem Port
				serverSocket = new ServerSocket(ConnectionUtils.PORT);
				
				//Schleife zur verarbeitung
				while(true) {
					System.out.println("Server waiting");
					//ServerSocket wartet bis Socket vom Client eingeht
					Socket mySocket = serverSocket.accept();
					//Weiterverarbeiten
					ChatController.startVerarbeitung(mySocket);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				serverSocket.close();
			}
		}
	}

	@Override
	public void finalize() throws IOException {
		if (serverSocket != null && serverSocket.isClosed()) {
			serverSocket.close();
		}

	}

	public static void main(String[] args) {
		try {
			new ServerController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
