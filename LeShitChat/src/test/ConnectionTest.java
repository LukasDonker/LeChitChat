package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ClientController;
import model.ChatEintrag;
import server.DBController;
import server.ServerController;

class ConnectionTest {

	ServerController server;
	ClientController client;

	@BeforeEach
	void setUp() throws IOException {
		server = new ServerController();
		client = ClientController.getInstance();

	}

	@AfterEach
	void tearDown() throws Throwable {
		server.finalize();
	}
	
	@Test
	void test() throws IOException {
		
		List<ChatEintrag> testList = new ArrayList<ChatEintrag>();
		for (int i = 0; i < 100; i++) {
			ChatEintrag test = new ChatEintrag("me", "Hello World! " + i);
			testList.add(test);
		}

		for (ChatEintrag test : testList) {
			client.sentToServer(test);
		}

		assertTrue(DBController.getInstance().getChatList() != null);

		List<ChatEintrag> resultList = client.recieveFromServer();

		assertEquals(testList.size(), resultList.size());

	}
	
	

}
