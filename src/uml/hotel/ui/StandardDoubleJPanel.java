package uml.hotel.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.notification.Observer;
import uml.hotel.utils.Utils;

public class StandardDoubleJPanel extends BaseRoomPanel {

	@Override
	public void createButtons() {
		JButton btnNewButton = new JButton("BS0001");
		btnNewButton.setBounds(10, 25, 83, 68);
		Utils.createRoomButton(btnNewButton);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("BS0002");
		btnNewButton_1.setBounds(103, 25, 83, 68);
		Utils.createRoomButton(btnNewButton_1);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("BS0003");
		btnNewButton_2.setBounds(196, 25, 83, 68);
		Utils.createRoomButton(btnNewButton_2);
		add(btnNewButton_2);
		
		buttons = new JButton[] {btnNewButton, btnNewButton_1, btnNewButton_2};
	}
		
}
