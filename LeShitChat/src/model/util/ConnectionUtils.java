package model.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionUtils {

	public static final int PORT = 50001;
	public static final String HOSTNAME = "IBS_T470_3";

	public static InetAddress getServerHost() {
		try {
			return InetAddress.getByName(HOSTNAME);
		} catch (UnknownHostException e) {
			try {
				System.out.println(HOSTNAME + " unreachable - trying to connect to local host");
				return InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				return null;
			}
		}
	}
}
