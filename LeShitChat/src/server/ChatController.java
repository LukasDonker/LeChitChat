package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

import model.ChatEintrag;
import model.ChatOperation;
import model.Operation;

public class ChatController implements Runnable {

	private static LinkedList<Socket> socketList = new LinkedList<>();
	private static int anzahl;
	private static final int MAX_ANZAHL = 4;

	public void run() {
		// Verarbeitung
		// Wenn Arbeit da ist, verarbeite
		while (socketList.size() > 0) {
			System.out.println("Thread" + anzahl + " startet Verarbeitung");
			// Socket wird aus liste geholt
			Socket socket = socketList.getFirst();
			socketList.removeFirst();

			ObjectInputStream inputStream;
			try {
				//Input lesen
				inputStream = new ObjectInputStream(socket.getInputStream());
				ChatOperation object = (ChatOperation) inputStream.readObject();
				
				//Verarbeitung anstoßen/durchführen
				//Welche Operation mache ich gerade?
				switch(object.getOperation()) {
					case CHATSEND: 
						System.out.println("Verarbeite CHATSEND Object" + object);
						chatSendverarbeitung(socket, object);
						break;
					case RECIEVECHATS: 
						System.out.println("Verarbeite RECIEVECHATS Object" + object);
						break;
					default: 
						System.out.println("DEFAULT");
						break;
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					//Socket in jedem fall schließen;
					socket.close();
					System.out.println("Verbindung geschlossen!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Verarbeite einen eingehenden Text und gebe den zurück
	 * @param socket
	 * @param object
	 * @throws IOException 
	 */
	private void chatSendverarbeitung(Socket socket, ChatOperation object) throws IOException {
		ChatEintrag eintrag = (ChatEintrag) object.getData();
		eintrag.setDateRecieved(new Date());
		
		ChatOperation resultOP = new ChatOperation(Operation.CHATSEND, eintrag);
		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		outStream.writeObject(resultOP);
		outStream.flush();
		System.out.println("Verarbeitung abgeschlosssen Object:" + object);
	}

	/**
	 * Verarbeitungseingang vom Server
	 * 
	 * @param socket
	 */
	public static void startVerarbeitung(Socket socket) {
		socketList.addLast(socket);
		if (anzahl < MAX_ANZAHL) {
			new ChatController().run();
		}
	}

}
