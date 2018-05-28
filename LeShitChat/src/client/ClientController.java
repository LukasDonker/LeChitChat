package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.ChatEintrag;
import model.ChatOperation;
import model.Operation;
import model.util.ConnectionUtils;

public class ClientController {

	private static ClientController instance;

	public static ClientController getInstance() {
		if (instance == null) {
			instance = new ClientController();
		}
		return instance;
	}

	private ClientController() {

	}

	public ChatEintrag sentToServer(ChatEintrag value) throws IOException {
		//Verpacken
		ChatOperation chatOp = new ChatOperation(Operation.CHATSEND, value);
		
		//Socket stellt eine Verbindung mit dem Server her.
		Socket mySocket = new Socket(ConnectionUtils.getServerHost(), ConnectionUtils.PORT);
		// Verbindung steht, Objekt wird geschrieben
		ObjectOutputStream outStream = new ObjectOutputStream(mySocket.getOutputStream());
		outStream.writeObject(chatOp);
		outStream.flush();
		//Objekt wird zurückgeholt
		ObjectInputStream inStream = new ObjectInputStream(mySocket.getInputStream());
		ChatOperation result = null;
		try {
			result = (ChatOperation) inStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			mySocket.close();
		}
		
		//entpacken
		return (ChatEintrag) result.getData();
	}

	public static void main(String[] args) {
		ChatEintrag test = new ChatEintrag("me", "Hello World!");
		try {
			ClientController.getInstance().sentToServer(test);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
