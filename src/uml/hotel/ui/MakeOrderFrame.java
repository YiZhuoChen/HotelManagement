package uml.hotel.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import uml.hotel.dao.CostDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Cost;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;
import uml.hotel.notification.NotificationCenter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class MakeOrderFrame extends JFrame {

	private JPanel contentPane;
	private RoomInfoPanel ri;
	private CustomerInfoPanel ci;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeOrderFrame frame = new MakeOrderFrame("BD0001");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MakeOrderFrame(final String text) {

		setBounds(100, 100, 550, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 514, 45);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\u4E3B\u5BA2\u623F\u95F4\uFF1A");
		label.setBounds(10, 15, 72, 15);
		panel.add(label);
		
		JLabel lblBd = new JLabel(text);
		lblBd.setForeground(Color.RED);
		lblBd.setFont(new Font("宋体", Font.PLAIN, 14));
		lblBd.setBounds(82, 10, 54, 25);
		panel.add(lblBd);
		
		JLabel label_1 = new JLabel("\u623F\u95F4\u7C7B\u578B\uFF1A");
		label_1.setBounds(167, 15, 73, 15);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u6807\u51C6\u5355\u4EBA\u95F4");
		label_2.setFont(new Font("宋体", Font.PLAIN, 17));
		label_2.setForeground(Color.BLUE);
		label_2.setBounds(236, 9, 86, 25);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("\u4EF7\u683C\uFF1A");
		label_3.setBounds(358, 15, 54, 15);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("\uFFE599");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("宋体", Font.PLAIN, 16));
		label_4.setBounds(422, 15, 54, 15);
		panel.add(label_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 55, 514, 1);
		panel_1.setBackground(Color.gray);
		contentPane.add(panel_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 65, 514, 300);
		contentPane.add(tabbedPane);
		
		ri = new RoomInfoPanel();
		ci = new CustomerInfoPanel();
		tabbedPane.addTab("房间信息", ri);
		tabbedPane.addTab("顾客信息", ci);
		
		JButton button = new JButton("\u53D6\u6D88");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		button.setBounds(431, 378, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u786E\u5B9A");
		button_1.setBounds(328, 378, 93, 23);
		contentPane.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//获取用户输入
				RoomStatus roomStatus = ri.getRoomStatus();
				User user = ci.getUser();
				//保存用户
				UserDAO userDAO = new UserDAO();
				userDAO.save(user);
				
				//设置房间状态
				roomStatus.setUserId(user.getId());
				roomStatus.setRoomId(text);
				//保存房间状态
				RoomStatusDAO rsDao = new RoomStatusDAO();
				rsDao.save(roomStatus);
				
				//更新房间
				RoomDAO roomDAO = new RoomDAO();
				List roomList = roomDAO.findByNumber(text);
				Room room = (Room)roomList.get(roomList.size() - 1);
				room.setStatus(Room.kRoomStatusUsed);
				roomDAO.save(room);
				
				//为房间保存一条消费记录
				CostDAO costDAO = new CostDAO();
				Cost cost = new Cost(room.getId(), (float)(room.getCost() * roomStatus.getLivingDay()), 0);
				costDAO.save(cost);
				
				setVisible(false);
				
				//更新UI
				NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, text);
				//更新订单信息（如果需要的话)
				NotificationCenter.postNotification(NotificationCenter.kReservationStateDidChangeNotification, null);
			}
		});
	}
	
	public MakeOrderFrame(RoomStatus roomStatus, User user) {
		this(roomStatus.getRoomId());
		ri.setRoomStatus(roomStatus);
		ci.setUser(user);
	}
}
