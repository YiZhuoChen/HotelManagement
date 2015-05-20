package uml.hotel.notification;

import java.util.HashMap;
import java.util.Vector;

public class NotificationCenter {
	
	public static final String kRoomStatusDidChangeNotification = "kRoomStatusDidChangeNotification";
	public static final String kRoomDidSelectedNotification = "kRoomDidSelectedNotification";
	public static final String kShowSpecialRoomNotification = "kShowSpecialRoomNotification";
	public static final String kReservationStateDidChangeNotification = "kReservationStateDidChangeNotification";
	
	public static HashMap<String, Vector<Observer>> relations = new HashMap<String, Vector<Observer>>();
	
	public static void postNotification(String name, Object userInfo) {
		for (String key : relations.keySet()) {
			if (key == name) {
				Vector<Observer> values = relations.get(key);
				for (Observer observer : values) {
					observer.receivedNotification(name, userInfo);
				}
				break;
			}
		}
	}
	
	public static void addNotification(Observer o, String name) {
		Vector<Observer> values = relations.get(name);
		if (values != null) {
			values.add(o);
		} else {
			Vector<Observer> observers = new Vector<Observer>();
			observers.add(o);
			relations.put(name, observers);
		}
	}

}
