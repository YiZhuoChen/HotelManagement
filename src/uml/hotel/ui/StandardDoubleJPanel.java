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

public class StandardDoubleJPanel extends JPanel implements Observer, ActionListener {

	private JButton[] buttons;
	private JPopupMenu menu;
	private JButton selectedBtn;
	
	/**
	 * Create the panel.
	 */
	public StandardDoubleJPanel() {
		setLayout(null);
		
		initPopupMenu();
		
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
		for (final JButton button : buttons) {
			button.addActionListener(this);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						RoomStatusDAO dao = new RoomStatusDAO();
						List list = dao.findByRoomId(button.getText());
						if (list != null && list.size() > 0) {
							//如果当前房间有记录
							RoomStatus status = (RoomStatus)list.get(list.size() - 1);
							UserDAO userDAO = new UserDAO();
							User user = userDAO.findById(status.getUserId());
							new MakeOrderFrame(status, user).setVisible(true);							
						} else {
							new MakeOrderFrame(button.getText()).setVisible(true);
						}
					}
				}
				
				@Override
				public void mousePressed(MouseEvent ev) {
					 if (ev.isPopupTrigger()) {
						menu.show(ev.getComponent(), ev.getX(), ev.getY());
						RoomDAO roomDAO = new RoomDAO();
						selectedBtn = (JButton)ev.getComponent();
						Room room = (Room)roomDAO.findByNumber(selectedBtn.getText()).get(0);
						if (room.getStatus() == Room.kRoomStatusAvaliable) {
							menu.getComponent(0).setEnabled(false);
							menu.getComponent(1).setEnabled(false);
							menu.getComponent(2).setEnabled(true);
							menu.getComponent(3).setEnabled(true);
							menu.getComponent(4).setEnabled(false);
							menu.getComponent(5).setEnabled(false);
						} else if (room.getStatus() == Room.kRoomStatusReserved) {
							menu.getComponent(0).setEnabled(false);
							menu.getComponent(1).setEnabled(false);
							menu.getComponent(2).setEnabled(true);
							menu.getComponent(3).setEnabled(false);
							menu.getComponent(4).setEnabled(false);
							menu.getComponent(5).setEnabled(false);
						} else if (room.getStatus() == Room.kRoomStatusUsed) {
							menu.getComponent(0).setEnabled(true);
							menu.getComponent(1).setEnabled(true);
							menu.getComponent(2).setEnabled(false);
							menu.getComponent(3).setEnabled(false);
							menu.getComponent(4).setEnabled(false);
							menu.getComponent(5).setEnabled(true);
						} else if (room.getStatus() == Room.kRoomStatusClean) {
							menu.getComponent(0).setEnabled(false);
							menu.getComponent(1).setEnabled(false);
							menu.getComponent(2).setEnabled(false);
							menu.getComponent(3).setEnabled(false);
							menu.getComponent(4).setEnabled(true);
							menu.getComponent(5).setEnabled(false);
						}
					}
				 }
			});
		}
		
		NotificationCenter.addNotification(this, NotificationCenter.kRoomStatusDidChangeNotification);
	}

	@Override
	public void receivedNotification(String name, Object userInfo) {

		if (name.equals(NotificationCenter.kRoomStatusDidChangeNotification)) {
			String roomNum = (String)userInfo;
			for (JButton btn : buttons) {
				if (btn.getText().equals(roomNum)) {
					Utils.updateButtonIconWithStatus(btn, roomNum);
				}
			}
		} else if (name.equals(NotificationCenter.kShowSpecialRoomNotification)) {
			if ((Integer)userInfo == MainFrame.kShowAllRoom) {
				//显示全部房间
				for (JButton btn : buttons) {
					btn.setVisible(true);
				}
			} else {
				for (JButton btn : buttons) {
					String roomNum = btn.getText();
					RoomDAO dao = new RoomDAO();
					List roomList = dao.findByNumber(roomNum);
					Room room = (Room)roomList.get(roomList.size() - 1);
					if (room.getStatus().equals((Integer)userInfo)) {
						btn.setVisible(true);
					} else {
						btn.setVisible(false);
					}
				}
			}
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		NotificationCenter.postNotification(NotificationCenter.kRoomDidSelectedNotification, e.getSource());
	}
	
	private void initPopupMenu() {
		menu = new JPopupMenu();
		final JMenuItem item = new JMenuItem("宾客结账");;
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomStatusDAO statusDAO = new RoomStatusDAO();
				List<RoomStatus> list = statusDAO.findByRoomId(selectedBtn.getText());
				RoomStatus status = list.get(list.size() - 1);
				new PayoffFrame(status).setVisible(true);
			}
		});
		menu.add(item);
		
		final JMenuItem item1 = new JMenuItem("增加消费");
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menu.add(item1);
		final JMenuItem item2 = new JMenuItem("宾客开单");
		item2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MakeOrderFrame(selectedBtn.getText()).setVisible(true);
			}
		});
		menu.add(item2);
		final JMenuItem item3 = new JMenuItem("宾客预定");
		item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ReservationFrame rf = new ReservationFrame();
				rf.setVisible(true);
				rf.getAddReservationBtn().doClick();
			}
		});
		menu.add(item3);
		final JMenuItem item4 = new JMenuItem("清理完毕");
		item4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomDAO roomDAO = new RoomDAO();
				Room room = (Room)roomDAO.findByNumber(selectedBtn.getText()).get(0);
				room.setStatus(Room.kRoomStatusAvaliable);
				//保存到数据库
				roomDAO.attachDirty(room);
				//发通知：房间状态改变
				NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, room.getNumber());
			}
		});
		menu.add(item4);
		
		final JMenuItem item5 = new JMenuItem("更换房间");
		item5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		menu.add(item5);
		
	}
	
}
