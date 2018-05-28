package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ChatEintrag;

public class DBController {

	private List<ChatEintrag> chatList = new ArrayList<ChatEintrag>();
	
	//Singelton
	private static DBController instance;
	
	public static DBController getInstance() {
		if(instance == null) {
			instance = new DBController();
		}
		return instance;
	}
	
	private DBController() {}
	
	/**
	 * ChatEintrag hinzufügen
	 * @param value
	 */
	public void addChatEintrag(ChatEintrag value) {
		chatList.add(value);
	}
	
	/**
	 * GesamtListe
	 * @return
	 */
	public List<ChatEintrag> getChatList(){
		return chatList;
	}
	
	/**
	 * Liste ab Datum ->not implemented
	 * @return
	 */
	public List<ChatEintrag> getChatListFromDate(Date date){
		return chatList;
	}
}
