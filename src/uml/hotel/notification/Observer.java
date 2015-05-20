package uml.hotel.notification;

public interface Observer {
	void receivedNotification(String name, Object userinfo);
}
