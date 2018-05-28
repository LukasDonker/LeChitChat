package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.ChatEintrag;
import model.ChatOperation;
import model.Operation;

public class ChatController extends Thread implements Runnable {

	private static LinkedList<Socket> socketList = new LinkedList<>();
	private static final int MAX_ANZAHL = 4;
	private static int anzahl = 1;
	private DBController dbController = DBController.getInstance();

	public ChatController() {
		start();
		anzahl =+1;
	}
	
	public void run() {
		// Verarbeitung
		// Wenn Arbeit da ist, verarbeite
		while (socketList.size() > 0 || anzahl == 1) {
			
			if(socketList.isEmpty()) {
				System.out.print("...");
				continue;
			}
			
			System.out.println("Thread " + this.getId() + " startet Verarbeitung");
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
						System.out.println("Verarbeite CHATSEND Object:" + object);
						chatSendverarbeitung(socket, object);
						break;
					case RECIEVECHATS: 
						System.out.println("Verarbeite RECIEVECHATS Object:" + object);
						recieveChatsVerarbeitung(socket, object);
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
		anzahl =- 1;
	}

	private void recieveChatsVerarbeitung(Socket socket, ChatOperation object) throws IOException {
		
		List<ChatEintrag> eintraege = dbController.getChatList();
		object.setData(eintraege);
		
		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		outStream.writeObject(object);
		outStream.flush();
		System.out.println("Verarbeitung abgeschlosssen Object:" + object);
		
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
		//ChatEintrag in Datenbank speichern
		dbController.addChatEintrag(eintrag);
		
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
	public static synchronized void startVerarbeitung(Socket socket) {
		socketList.addLast(socket);
	}

}
