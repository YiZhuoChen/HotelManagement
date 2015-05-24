package uml.hotel.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import uml.hotel.dao.OrderDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.model.Order;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.utils.CalendarFrame;
import uml.hotel.utils.CalendarFrameDelegate;
import uml.hotel.utils.PCalendar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class AddReservationFrame extends JFrame implements CalendarFrameDelegate {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_5;

	private List<Room> selectedTypeRooms;
	private JComboBox roomNumBox;
	private CalendarFrame calendarFrame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddReservationFrame frame = new AddReservationFrame();
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
	public AddReservationFrame() {
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5BBE\u5BA2\u59D3\u540D\uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 13));
		label.setBounds(41, 76, 68, 26);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u8054\u7CFB\u7535\u8BDD\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 13));
		label_1.setBounds(41, 126, 68, 26);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u516C\u53F8\u540D\u79F0\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 13));
		label_2.setBounds(41, 176, 68, 26);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u5BBE\u5BA2\u6765\u6E90\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 13));
		label_3.setBounds(41, 226, 68, 26);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u623F\u95F4\u7F16\u53F7\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 13));
		label_4.setBounds(41, 326, 68, 26);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u9884\u62B5\u65F6\u95F4\uFF1A");
		label_5.setFont(new Font("宋体", Font.PLAIN, 13));
		label_5.setBounds(41, 376, 68, 26);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("宾客类型");
		label_6.setFont(new Font("宋体", Font.PLAIN, 13));
		label_6.setBounds(41, 276, 68, 26);
		contentPane.add(label_6);
		
		textField = new JTextField();
		textField.setBounds(119, 79, 270, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(119, 129, 270, 21);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(119, 179, 270, 21);
		contentPane.add(textField_2);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(119, 379, 220, 21);
		contentPane.add(textField_5);
		
		JButton addDateButton = new JButton("添加日期");
		addDateButton.setBounds(340, 375, 100, 30);
		contentPane.add(addDateButton);
		addDateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				calendarFrame = new CalendarFrame(new PCalendar());
				calendarFrame.setDelegate(AddReservationFrame.this);
				calendarFrame.setVisible(true);
				calendarFrame.showCalendar();
			}
		});
		
		JLabel lblNewLabel = new JLabel("\u5BBE\u5BA2\u9884\u5B9A\u767B\u8BB0");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(93, 30, 307, 26);
		contentPane.add(lblNewLabel);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"normal", "website", "tralvel agency", "other"}));
		comboBox.setBounds(119, 229, 270, 21);
		contentPane.add(comboBox);
		
		final JComboBox typeBox = new JComboBox();
		typeBox.setModel(new DefaultComboBoxModel(new String[] {"标准单人间", "标准双人间", "豪华单人间"}));
		typeBox.setBounds(119, 279, 270, 21);
		contentPane.add(typeBox);
		
		final RoomDAO roomDAO = new RoomDAO();
		
		typeBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//标单的type编号是2，往后以此类推
				selectedTypeRooms = roomDAO.findByType(typeBox.getSelectedIndex() + 2);
				Vector<String> roomNums = new Vector<String>();
				for (Room room : selectedTypeRooms) {
					//将所选类型的房间名字添加进数组中。
					roomNums.add(room.getNumber());
				}
				roomNumBox.setModel(new DefaultComboBoxModel(roomNums));
			}
		});
		
		roomNumBox = new JComboBox();
		roomNumBox.setBounds(119, 329, 270, 21);
		contentPane.add(roomNumBox);
		
		JButton button = new JButton("\u6DFB\u52A0\u9884\u5B9A");
		button.setBounds(93, 428, 93, 23);
		contentPane.add(button);
		//添加预定
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//获取预定信息
				String userName = textField.getText();
				String tel = textField_1.getText();
				String company = textField_2.getText();
				int from = comboBox.getSelectedIndex();
				String arriveTime = textField_5.getText();
				int type = typeBox.getSelectedIndex() + 2;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
				String now = df.format(new Date());
				
				//是否需要通知更新房态
				boolean shouldChangeState = true;
				
				String roomNum = (String) roomNumBox.getSelectedItem();
				
				List<Room> roomList = roomDAO.findByNumber(roomNum);
				Room selectedRoom = (Room)roomList.get(roomList.size() - 1);
				if (selectedRoom.getStatus() == Room.kRoomStatusUsed) {
					RoomStatusDAO statusDAO = new RoomStatusDAO();
					List<RoomStatus> statusList = statusDAO.findByRoomId(selectedRoom.getNumber());
					RoomStatus status = statusList.get(statusList.size() - 1);
					String endTime = status.getEndTime();
					
					try {
						Date end = df.parse(endTime);
						Date arrive = df.parse(arriveTime);
						int compareResult = end.compareTo(arrive);	//-1表示end在arrive前, 1表示end在arrive后
						if (compareResult == 1) {
							JOptionPane.showMessageDialog(null, "预定时间与房间结束时间冲突，预定添加失败");
							return;
						}
						
						//此时只添加预定，不更新房态
						shouldChangeState = false;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} else if (selectedRoom.getStatus() != Room.kRoomStatusAvaliable) {
					int result = JOptionPane.showConfirmDialog(null, "预定房间的当期房态为非可供状态，是否继续？");
					if (result != JOptionPane.OK_OPTION) {
						return;
					}
				}
				
				//保存预定信息
				OrderDAO orderDAO = new OrderDAO();
				Order order = new Order(arriveTime, now, type, Order.kOrderTypeOrdering, roomNum, userName, company, tel, from);
				orderDAO.save(order);
				
				
				roomDAO.attachDirty(selectedRoom);
				if (shouldChangeState) {
					//修改房间状态
					selectedRoom.setStatus(Room.kRoomStatusReserved);
					//发送通知：房间状态改变
					NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, roomNum);
				}
				//发送通知，预定状态改变
				NotificationCenter.postNotification(NotificationCenter.kReservationStateDidChangeNotification, null);
				
				//关闭当前窗口
				setVisible(false);
			}
		});
		
		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		button_1.setBounds(296, 428, 93, 23);
		contentPane.add(button_1);
	}

	@Override
	public void calendarDidSelectWithDayName(String day) {
//		System.out.println(day);
		
		String defaultDateString = day + " 19:00:00";
		textField_5.setText(defaultDateString);
		if (calendarFrame != null) {
			calendarFrame.setVisible(false);
		}
	}
}
