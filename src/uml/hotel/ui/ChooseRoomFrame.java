package uml.hotel.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.List;

import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseRoomFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private int type;
	
	public static final int kChooseRoomTypeMakeOrder = 0;
	public static final int kChooseRoomTypePayoff = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseRoomFrame frame = new ChooseRoomFrame(kChooseRoomTypeMakeOrder);
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
	public ChooseRoomFrame(int type) {
		this.type = type;
		
		setLocationRelativeTo(null);
		setBounds(100, 100, 330, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("请输入要下单的房间号");
		if (this.type == kChooseRoomTypePayoff) {
			label.setText("请输入要结账的房间号");
		}
		label.setBounds(10, 10, 152, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(10, 35, 294, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("\u221A  \u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				if (text != null) {
					
					RoomDAO dao = new RoomDAO();
					List l = dao.findByNumber(text);
					if (l.size() == 0) {
						JOptionPane.showMessageDialog(null, "请输入正确的房间号");
					} else {
						Room selectedRoom = (Room)l.get(l.size() - 1);
						
						if (ChooseRoomFrame.this.type == kChooseRoomTypeMakeOrder) {
							if (selectedRoom.getStatus() != Room.kRoomStatusAvaliable) {
								RoomStatusDAO statusDAO = new RoomStatusDAO();
								List list = statusDAO.findByRoomId(text);
								if (list != null && list.size() > 0) {
									//如果当前房间有记录
									RoomStatus status = (RoomStatus)list.get(list.size() - 1);
									UserDAO userDAO = new UserDAO();
									User user = userDAO.findById(status.getUserId());
									new MakeOrderFrame(status, user).setVisible(true);							
								} else {
									new MakeOrderFrame(text).setVisible(true);
								}
								
								setVisible(false);
							} else {
								setVisible(false);
								new MakeOrderFrame(text).setVisible(true);
							}
						} else if (ChooseRoomFrame.this.type == kChooseRoomTypePayoff) {
							if (selectedRoom.getStatus() != Room.kRoomStatusUsed) {
								JOptionPane.showMessageDialog(null, "您选择的房间不是入住状态");
							} else {
								setVisible(false);
								RoomStatusDAO statusDAO = new RoomStatusDAO();
								List statusList = statusDAO.findByRoomId(selectedRoom.getNumber());
								RoomStatus status = (RoomStatus)statusList.get(statusList.size() - 1);
								new PayoffFrame(status).setVisible(true);
							}
						}
						
					}
					
				}
			}
		});
		button.setBounds(108, 66, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u00D7  \u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		button_1.setBounds(211, 66, 93, 23);
		contentPane.add(button_1);
	}
}
