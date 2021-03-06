package uml.hotel.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import uml.hotel.dao.CostDAO;
import uml.hotel.dao.OrderDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.ServerDAO;
import uml.hotel.model.Cost;
import uml.hotel.model.Order;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.Server;
import uml.hotel.notification.NotificationCenter;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ChangeRoomFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JComboBox comboBox_1;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeRoomFrame frame = new ChangeRoomFrame();
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
	public ChangeRoomFrame() {
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u4ECE");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 49, 54, 15);
		contentPane.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setBounds(74, 46, 121, 21);
		contentPane.add(comboBox);
		
		JLabel label = new JLabel("\u6362\u5230");
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(239, 49, 54, 15);
		contentPane.add(label);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(303, 46, 121, 21);
		contentPane.add(comboBox_1);
		
		JButton button = new JButton("\u786E\u5B9A");
		button.setBounds(102, 128, 93, 23);
		contentPane.add(button);
		button.addActionListener(this);
		
		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.setBounds(239, 128, 93, 23);
		contentPane.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		
		
		//从数据库中查找所有已用房间
		final RoomDAO roomDAO = new RoomDAO();
		List<Room> rooms = roomDAO.findAll();
		Vector<String> roomNames = new Vector<String>();
		for (Room room : rooms) {
			if (room.getStatus() == Room.kRoomStatusUsed) {
				roomNames.add(room.getNumber());
			}
		}
		
		if (roomNames.size() == 0) {
			String[] tip = {"无已住房间"};
			comboBox.setModel(new DefaultComboBoxModel(tip));
			comboBox_1.setModel(new DefaultComboBoxModel(tip));
			button.setEnabled(false);
		} else {
			comboBox.setModel(new DefaultComboBoxModel(roomNames));
			comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String roomNum = (String)comboBox.getSelectedItem();
					//可用类型的房间
					List<Room> changableRooms = roomDAO.findByStatus(Room.kRoomStatusAvaliable);
					Room selectedRoom = (Room)roomDAO.findByNumber(roomNum).get(0);
					
					Vector<String> targetRooms = new Vector<String>();
					for (Room room : changableRooms) {
						//如果同类型的，并且不是同一间房间。
						if (room.getType().equals(selectedRoom.getType()) && !(room.getNumber().equals(selectedRoom.getNumber()))) {
							targetRooms.add(room.getNumber());
						}
					}
					
					comboBox_1.setModel(new DefaultComboBoxModel(targetRooms));
				}
			});
		}
	}
	
	public ChangeRoomFrame(String source) {
		this();
		comboBox.setSelectedItem(source);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (comboBox.getSelectedItem() == null || comboBox_1.getSelectedItem() == null) {
			return;
		}
		
		int result = JOptionPane.showConfirmDialog(null, "确认要换房吗?");
		if (result == JOptionPane.OK_OPTION) {
			setVisible(false);
			
			RoomDAO roomDAO = new RoomDAO();
			Room source = (Room)roomDAO.findByNumber((String)comboBox.getSelectedItem()).get(0);
			Room target = (Room)roomDAO.findByNumber((String)comboBox_1.getSelectedItem()).get(0);
			
			target.setStatus(source.getStatus());
			setProperRoomStateForRoom(source);
			
			//创建新的房间状态信息
			RoomStatusDAO statusDAO = new RoomStatusDAO();
			List<RoomStatus> list = statusDAO.findByRoomId(source.getNumber());
			RoomStatus sourceStatus = list.get(list.size() - 1);
			RoomStatus targetStatus = new RoomStatus(sourceStatus.getUserId(), 
					sourceStatus.getStartTime(), sourceStatus.getEndTime(), 
					target.getNumber(), sourceStatus.getDeposit(), 
					sourceStatus.getTime(), RoomStatus.kStatusDidNotRemind, 
					sourceStatus.getType());
			statusDAO.save(targetStatus);
			
			//创建新的消费信息
			CostDAO costDAO = new CostDAO();
			List<Cost> costList = costDAO.findByRoomId(source.getId());
			Cost cost = costList.get(costList.size() - 1);
			Cost newCost = new Cost(target.getId(), cost.getCost(), cost.getDiscount());
			costDAO.save(newCost);
			
			//额外消费
			ServerDAO serverDAO = new ServerDAO();
			List<Server> servers = serverDAO.findByRoomId(source.getId());
			for (Server server : servers) {
				server.setRoomId(target.getId());
				serverDAO.attachDirty(server);
			}
			
			//更新房间状态
			roomDAO.attachDirty(source);
			roomDAO.attachDirty(target);
			//发送通知：房间状态改变
			NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, source.getNumber());
			NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, target.getNumber());
		}
	}
	
	
	public void setProperRoomStateForRoom(Room source) {
		RoomStatusDAO statusDAO = new RoomStatusDAO();
		List<RoomStatus> list = statusDAO.findByRoomId(source.getNumber());
		
		OrderDAO orderDAO = new OrderDAO();
		List<Order> orders = orderDAO.findByRoomNum(source.getNumber());
		if (orders != null && orders.size() > 0) {
			Order order = orders.get(orders.size() - 1);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date arrive;
			try {
				arrive = df.parse(order.getArriveTime());
				if (arrive.compareTo(new Date()) != -1) {
					source.setStatus(Room.kRoomStatusReserved);
				} else {
					source.setStatus(Room.kRoomStatusAvaliable);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			source.setStatus(Room.kRoomStatusAvaliable);
		}
	}
}
