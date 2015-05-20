package uml.hotel.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.border.LineBorder;

import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.notification.Observer;

public class StatusPanel extends JPanel implements Observer {
	
	JLabel userNameLabel = new JLabel();
	JLabel telLabel = new JLabel();
	JLabel locationLabel = new JLabel();
	JLabel enterTimeLabel = new JLabel();
	JLabel exitTimeLabel = new JLabel();
	JLabel depositLabel = new JLabel();
	JLabel costLabel = new JLabel();
	
	JLabel roomCountLabel = new JLabel();
	JLabel availableCountLabel = new JLabel();
	JLabel reserveCountLabel = new JLabel();
	JLabel rateLabel = new JLabel();
	
	/**
	 * Create the panel.
	 */
	public StatusPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 0, 151, 23);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u25CF");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(22, 5, 12, 15);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("房间状态");
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(44, 5, 85, 15);
		panel.add(label);
		
		JLabel label_1 = new JLabel("宾客姓名：");
		label_1.setBounds(10, 33, 73, 15);
		add(label_1);
		
		userNameLabel.setBounds(80, 33, 100, 15);
		add(userNameLabel);
		
		JLabel label_2 = new JLabel("房间电话：");
		label_2.setBounds(10, 58, 73, 15);
		add(label_2);
		
		telLabel.setBounds(80, 58, 100, 15);
		add(telLabel);
		
		JLabel label_3 = new JLabel("房间位置：");
		label_3.setBounds(10, 86, 73, 15);
		add(label_3);
		
		locationLabel.setBounds(80, 86, 100, 15);
		add(locationLabel);
		
		JLabel label_4 = new JLabel("进店时间：");
		label_4.setBounds(10, 111, 73, 15);
		add(label_4);
		
		enterTimeLabel.setBounds(80, 111, 100, 15);
		add(enterTimeLabel);
		
		JLabel label_5 = new JLabel("离店时间：");
		label_5.setBounds(10, 136, 73, 15);
		add(label_5);
		
		exitTimeLabel.setBounds(80, 136, 100, 15);
		add(exitTimeLabel);
		
		JLabel label_6 = new JLabel("已交押金：");
		label_6.setBounds(10, 161, 73, 15);
		add(label_6);
		
		depositLabel.setBounds(80, 161, 73, 15);
		add(depositLabel);
		
		JLabel label_7 = new JLabel("应收金额：");
		label_7.setBounds(10, 186, 73, 15);
		add(label_7);
		
		costLabel.setBounds(80, 186, 100, 15);
		add(costLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setLayout(null);
		panel_1.setBounds(0, 211, 151, 23);
		add(panel_1);
		
		JLabel label_8 = new JLabel("\u25CF");
		label_8.setForeground(Color.ORANGE);
		label_8.setBounds(22, 5, 12, 15);
		panel_1.add(label_8);
		
		JLabel label_9 = new JLabel("总房态");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setForeground(Color.RED);
		label_9.setFont(new Font("宋体", Font.PLAIN, 14));
		label_9.setBounds(44, 5, 85, 15);
		panel_1.add(label_9);
		
		JLabel label_10 = new JLabel("房间总数：");
		label_10.setBounds(10, 244, 73, 15);
		add(label_10);
		
		roomCountLabel.setBounds(83, 244, 100, 15);
		add(roomCountLabel);
		
		JLabel label_11 = new JLabel("当前可用：");
		label_11.setBounds(10, 282, 73, 15);
		add(label_11);
		
		availableCountLabel.setBounds(83, 282, 100, 15);
		add(availableCountLabel);
		
		JLabel label_12 = new JLabel("当前预定");
		label_12.setBounds(10, 323, 73, 15);
		add(label_12);
		
		reserveCountLabel.setBounds(83, 323, 100, 15);
		add(reserveCountLabel);
		
		JLabel label_13 = new JLabel("总入住率：");
		label_13.setBounds(10, 362, 73, 15);
		add(label_13);
		
		rateLabel.setBounds(83, 362, 100, 15);
		add(rateLabel);
		
		setAllRoomStatus();
		
		NotificationCenter.addNotification(this, NotificationCenter.kRoomDidSelectedNotification);
		NotificationCenter.addNotification(this, NotificationCenter.kRoomStatusDidChangeNotification);
		
	}

	public void setAllRoomStatus() {
		RoomDAO roomDAO = new RoomDAO();
		List rooms = roomDAO.findAll();
		roomCountLabel.setText(rooms.size() + "间");
		
		int available = 0;
		int reserved = 0;
		int used = 0;
		for (Object object : rooms) {
			Room room = (Room)object;
			if (room.getStatus() == Room.kRoomStatusAvaliable) {
				available++;
			} else if (room.getStatus() == Room.kRoomStatusReserved) {
				reserved++;
			} else if (room.getStatus() == Room.kRoomStatusUsed) {
				used++;
			}
		}
		availableCountLabel.setText(available + "间");
		reserveCountLabel.setText(reserved + "间");
		
		float rate = (float)used / rooms.size();
		rateLabel.setText((int)(rate * 100) + "%");
	}
	
	@Override
	public void receivedNotification(String name, Object userinfo) {
		// TODO Auto-generated method stub
		
		if (name.equals(NotificationCenter.kRoomDidSelectedNotification)) {
			//当某个房间被选中后，跟新左侧房间状态
			
			JButton button = (JButton)userinfo;
			String roomNum = button.getText();
			RoomDAO dao = new RoomDAO();
			List roomList = dao.findByNumber(roomNum);
			Room room = (Room)roomList.get(roomList.size() - 1);
			
			userNameLabel.setText(roomNum);
			telLabel.setText(room.getPhoneNum());
			locationLabel.setText("" + room.getLocation());
			
			
			RoomStatusDAO statusDAO = new RoomStatusDAO();
			List list = statusDAO.findByRoomId(room.getNumber());
			if (list != null && list.size() > 0) {
				RoomStatus status = (RoomStatus)list.get(list.size() - 1);
				enterTimeLabel.setText(status.getStartTime());
				exitTimeLabel.setText(status.getEndTime());
				depositLabel.setText("" + status.getDeposit());
				
				costLabel.setText("" + status.getLivingDay() * room.getCost());
			} else {
				enterTimeLabel.setText("");
				exitTimeLabel.setText("");
				depositLabel.setText("");
				costLabel.setText("");
			}
		} else if (name.equals(NotificationCenter.kRoomStatusDidChangeNotification)) {
			//更新总房态
			setAllRoomStatus();
		}
		
	}

}
