package uml.hotel.ui;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;

import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.notification.Observer;
import uml.hotel.utils.Utils;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class StandardSingleJPanel extends BaseRoomPanel {

	@Override
	public void createButtons() {
		JButton btnNewButton = new JButton("BD0001");
		btnNewButton.setBounds(10, 25, 83, 68);
		Utils.createRoomButton(btnNewButton);
		add(btnNewButton);
		
		
		JButton btnNewButton_1 = new JButton("BD0002");
		btnNewButton_1.setBounds(103, 25, 83, 68);
		Utils.createRoomButton(btnNewButton_1);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("BD0003");
		btnNewButton_2.setBounds(196, 25, 83, 68);
		Utils.createRoomButton(btnNewButton_2);
		add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("BD0004");
		btnNewButton_3.setBounds(289, 25, 83, 68);
		Utils.createRoomButton(btnNewButton_3);
		add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("BD0005");
		btnNewButton_4.setBounds(384, 25, 83, 68);
		Utils.createRoomButton(btnNewButton_4);
		add(btnNewButton_4);
		
		this.buttons = new JButton[] {btnNewButton, btnNewButton_1, btnNewButton_2, btnNewButton_3, btnNewButton_4};
	}
	
	

}
