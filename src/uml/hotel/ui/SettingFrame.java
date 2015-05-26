package uml.hotel.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import uml.hotel.dao.AdminDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.ServerItemDAO;
import uml.hotel.model.Admin;
import uml.hotel.model.Room;
import uml.hotel.model.ServerItem;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.utils.CustomTableModel;
import uml.hotel.utils.HeaderRenderer;

public class SettingFrame extends JFrame implements TreeSelectionListener {

	private JPanel contentPane;
	private JTextField roomNumField;
	private JTextField roomPriceField;
	private JTextField roomTelField;
	private JComboBox roomTypeBox;
	private JTextField serviceNameField;
	private JTextField servicePriceField;
	private JPasswordField originPasswdField;
	private JPasswordField newPasswdField;
	private JPasswordField confirmField;
	private JTree tree;
	private JTable table;

	private String lastType;
	private Vector<Vector<Object>> serviceData;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingFrame frame = new SettingFrame();
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
	public SettingFrame() {
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 414, 241);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.add("��������", panel);
		panel.setLayout(null);
		
		tree = new JTree();
		updateTreeModel();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		JScrollPane treeScrollPane = new JScrollPane(tree);
		treeScrollPane.setBounds(0, 0, 100, 190);
		panel.add(treeScrollPane);
		
		JLabel label = new JLabel("�����ţ�");
		label.setBounds(169, 28, 78, 15);
		panel.add(label);
		
		roomNumField = new JTextField();
		roomNumField.setBounds(257, 25, 115, 21);
		panel.add(roomNumField);
		roomNumField.setColumns(10);
		
		JLabel label_1 = new JLabel("����۸�");
		label_1.setBounds(169, 56, 78, 15);
		panel.add(label_1);
		
		roomPriceField = new JTextField();
		roomPriceField.setBounds(257, 53, 115, 21);
		panel.add(roomPriceField);
		roomPriceField.setColumns(10);
		
		JLabel label_2 = new JLabel("�������ͣ�");
		label_2.setBounds(169, 87, 78, 15);
		panel.add(label_2);
		
		roomTypeBox = new JComboBox();
		roomTypeBox.setModel(new DefaultComboBoxModel(new String[] {"\u6807\u51C6\u5355\u4EBA\u95F4", "\u6807\u51C6\u53CC\u4EBA\u95F4", "\u8C6A\u534E\u5355\u4EBA\u95F4"}));
		roomTypeBox.setBounds(257, 84, 115, 21);
		panel.add(roomTypeBox);
		
		JLabel label_3 = new JLabel("����绰��");
		label_3.setBounds(169, 118, 78, 15);
		panel.add(label_3);
		
		roomTelField = new JTextField();
		roomTelField.setBounds(257, 115, 115, 21);
		panel.add(roomTelField);
		roomTelField.setColumns(10);
		
		JButton addRoomBtn = new JButton("��ӷ���");
		addRoomBtn.setBounds(98, 161, 93, 23);
		panel.add(addRoomBtn);
		addRoomBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String num = roomNumField.getText();
				String tel = roomTelField.getText();
				int type = roomTypeBox.getSelectedIndex() + 2;
				String price = roomPriceField.getText();
				
				if (num.length() == 0 || tel.length() == 0 || price.length() == 0) {
					JOptionPane.showMessageDialog(null, "����дȫ����Ϣ");
					return;
				}
				
				addRoom(num, price, type, tel);
				
			}
		});
		
		JButton deleteRoomBtn = new JButton("ɾ������");
		deleteRoomBtn.setBounds(201, 161, 93, 23);
		panel.add(deleteRoomBtn);
		deleteRoomBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String roomNum = roomNumField.getText();
				if (roomNum.length() == 0) {
					JOptionPane.showMessageDialog(null, "����д��ɾ���ķ������");
					return;
				}
				deleteRoom(roomNum);
			}
		});
		
		JButton modifyRoomBtn = new JButton("�޸ķ���");
		modifyRoomBtn.setBounds(306, 161, 93, 23);
		panel.add(modifyRoomBtn);
		modifyRoomBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String roomNum = roomNumField.getText();
				updateRoomInfo(roomNum);
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.add("������������", panel_1);
		panel_1.setLayout(null);
		
		
		CustomTableModel model = new CustomTableModel();
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] columnNames = {"����", "����"}; 
		model.setColumnNames(columnNames);
		table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
		updateServiceTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0) {
					return;
				}
				
				String name = (String)table.getModel().getValueAt(selectedRow, 0);
				serviceNameField.setText(name);
				float price = (Float)table.getModel().getValueAt(selectedRow, 1);
				servicePriceField.setText("" + price);
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 120, 192);
		panel_1.add(scrollPane);
		
		JLabel label_4 = new JLabel("�������ƣ�");
		label_4.setBounds(156, 34, 65, 15);
		panel_1.add(label_4);
		
		serviceNameField = new JTextField();
		serviceNameField.setBounds(231, 31, 138, 21);
		panel_1.add(serviceNameField);
		serviceNameField.setColumns(10);
		
		JLabel label_5 = new JLabel("���Ѽ۸�");
		label_5.setBounds(156, 110, 65, 15);
		panel_1.add(label_5);
		
		servicePriceField = new JTextField();
		servicePriceField.setBounds(231, 107, 138, 21);
		panel_1.add(servicePriceField);
		servicePriceField.setColumns(10);
		
		JButton addServiceBtn = new JButton("���");
		addServiceBtn.setBounds(140, 159, 81, 23);
		panel_1.add(addServiceBtn);
		addServiceBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String serviceName = serviceNameField.getText();
				if (serviceName.length() == 0) {
					JOptionPane.showMessageDialog(null, "����д��������");
				}
				
				try {
					float price = Float.parseFloat(servicePriceField.getText());
					addServiceItem(serviceName, price);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		JButton deleteServiceBtn = new JButton("ɾ��");
		deleteServiceBtn.setBounds(231, 159, 81, 23);
		panel_1.add(deleteServiceBtn);
		deleteServiceBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String serviceName = serviceNameField.getText();
				if (serviceName.length() == 0) {
					JOptionPane.showMessageDialog(null, "����д��������");
				}
				
				deleteServiceItem(serviceName);
			}
		});
		
		
		JButton modifyServiceBtn = new JButton("�޸�");
		modifyServiceBtn.setBounds(318, 159, 81, 23);
		panel_1.add(modifyServiceBtn);
		modifyServiceBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String serviceName = serviceNameField.getText();
				if (serviceName.length() == 0) {
					JOptionPane.showMessageDialog(null, "����д��������");
				}
				updateServiceInfo(serviceName);
			}
		});
		
		JPanel panel_2 = new JPanel();
		tabbedPane.add("����Ա����", panel_2);
		panel_2.setLayout(null);
		
		JLabel label_6 = new JLabel("��ǰ����Ա��");
		label_6.setBounds(110, 24, 97, 15);
		panel_2.add(label_6);
		
		JLabel adminLabel = new JLabel("super");
		adminLabel.setForeground(Color.RED);
		adminLabel.setFont(new Font("����", Font.PLAIN, 13));
		adminLabel.setBounds(217, 24, 54, 15);
		panel_2.add(adminLabel);
		if (LoginFrame.getCurrentAdmin() != null) {
			adminLabel.setText(LoginFrame.getCurrentAdmin().getUsername());
		}
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "�޸�����", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(30, 49, 305, 153);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel label_7 = new JLabel("ԭ���룺");
		label_7.setBounds(52, 30, 71, 15);
		panel_3.add(label_7);
		
		originPasswdField = new JPasswordField();
		originPasswdField.setBounds(153, 27, 132, 21);
		panel_3.add(originPasswdField);
		originPasswdField.setColumns(10);
		
		JLabel label_8 = new JLabel("�����룺");
		label_8.setBounds(52, 61, 71, 15);
		panel_3.add(label_8);
		
		newPasswdField = new JPasswordField();
		newPasswdField.setBounds(153, 58, 132, 21);
		panel_3.add(newPasswdField);
		newPasswdField.setColumns(10);
		
		JLabel label_9 = new JLabel("ȷ�������룺");
		label_9.setBounds(52, 89, 82, 15);
		panel_3.add(label_9);
		
		confirmField = new JPasswordField();
		confirmField.setBounds(153, 86, 132, 21);
		panel_3.add(confirmField);
		confirmField.setColumns(10);
		
		JButton confirmBtn = new JButton("ȷ�ϸ���");
		confirmBtn.setBounds(99, 120, 93, 23);
		panel_3.add(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				changePasswd();
			}
		});
		
	}
	
	private void updateTreeModel() {
		tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("��������") {
					{	
						RoomDAO roomDAO = new RoomDAO();
						List<Room> allRooms = roomDAO.findAll();
						//�������Ա�һ�ᶯ̬��ȡ���з�������
						Collections.sort(allRooms, comparator);
						
						for (Room room : allRooms) {
							String roomNum = room.getNumber();
							String currentType = roomNum.substring(0, 2);
							//������
							if (currentType.equals(lastType) == false) {
								lastType = currentType;
								DefaultMutableTreeNode node = new DefaultMutableTreeNode(translateRoomType(currentType));
								add(node);
							}
							
							DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(roomNum);
							//��ȡ�������һ�������
							DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) getRoot().getChildAt(getRoot().getChildCount() - 1);
							parentNode.add(newNode);
							
						}
					}
				}
			));
	}
	
	private final Comparator<Room> comparator = new Comparator<Room>() {

		@Override
		public int compare(Room o1, Room o2) {
			// TODO Auto-generated method stub
			String type1 = o1.getNumber().substring(0, 2);
			String type2 = o2.getNumber().substring(0, 2);
			return type1.compareTo(type2);
		}
		
	};
	
	private String translateRoomType(String type) {
		if (type.equals("BD")) {
			return "��׼���˼�";
		} else if (type.endsWith("BS")) {
			return "��׼˫�˼�";
		} else if (type.equals("HD")) {
			return "�������˼�";
		} else {
			return "����";
		}
	}
	
	private Integer translateRoomLocation(String roomNum) {
		String roomType = roomNum.substring(0, 2);
		DecimalFormat df = new DecimalFormat("00");
		String prefix = roomNum.substring(4);
		try {
			Integer num = df.parse(prefix).intValue();
			if (roomType.equals("BD")) {
				return 200 + num;
			} else if (roomType.equals("BS")) {
				return 300 + num;
			} else if (roomType.equals("HD")){
				return 400 + num;
			} else {
				return 500 + num;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		
		String roomNum = (String)node.getUserObject();
		//ֻ�е��Ҷ�ӽ��ʱ��Ӧ
		if (node.isLeaf()) {
			RoomDAO roomDAO = new RoomDAO();
			Room room = (Room)roomDAO.findByNumber(roomNum).get(0);
			//�������
			roomNumField.setText(roomNum);
			//����۸�
			roomPriceField.setText("" + room.getCost());
			//��������, ��׼����-2�� ��׼˫��-3...
			roomTypeBox.setSelectedIndex(room.getType() - 2);
			//����绰
			roomTelField.setText(room.getPhoneNum());
		}
		
	}
	
	
	private void resetRoomPanel() {
		roomNumField.setText("");
		roomPriceField.setText("");
		roomTelField.setText("");
	}
	
	private void updateServiceTable() {
		serviceData = new Vector<Vector<Object>>();
		ServerItemDAO itemDAO = new ServerItemDAO();
		List<ServerItem> allItems = itemDAO.findAll();
		for (ServerItem serverItem : allItems) {
			Vector<Object> row = new Vector<Object>();
			row.add(serverItem.getContent());
			row.add(serverItem.getCost());
			serviceData.add(row);
		}
		//��������Դ
		((CustomTableModel)table.getModel()).setData(serviceData);
	}
	
	//---------------actions--------------
	
	private void updateRoomInfo(String roomNum) {
		RoomDAO roomDAO = new RoomDAO();
		List<Room> rooms = roomDAO.findByNumber(roomNum);
		if (rooms == null || rooms.size() == 0) {
			JOptionPane.showMessageDialog(null, "�޷���" + roomNum + "����������");
			return;
		}
		
		int result = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�޸ķ�����Ϣ�𣿣���ʾ������ź������޷��޸�)");
		if (result == JOptionPane.OK_OPTION) {
			String price = roomPriceField.getText();
			String phone = roomTelField.getText();
			if (price.length() == 0 || phone.length() == 0) {
				JOptionPane.showMessageDialog(null, "����д������Ϣ");
				return;
			}
			
			Room room = rooms.get(0);
			try {
				int cost = Integer.parseInt(price);
				room.setCost(cost);	
				room.setPhoneNum(phone);
				roomDAO.attachDirty(room);
				
				updateTreeModel();
				resetRoomPanel();
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
				
				//��֪ͨ��������Ϣ�ı�
				NotificationCenter.postNotification(NotificationCenter.kRoomInfoDidChangeNotification, null);
			} catch (NumberFormatException e2) {
				// TODO: handle exception
			}
		}
	}
	
	private void deleteRoom(String roomNum) {
		RoomDAO roomDAO = new RoomDAO();
		List<Room> rooms = roomDAO.findByNumber(roomNum);
		if (rooms == null || rooms.size() == 0) {
			JOptionPane.showMessageDialog(null, "�޷���" + roomNum + "����������");
			return;
		}
		
		int result = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ϣ�𣿣���ʾ��ɾ�����޷��ָ�)");
		if (result == JOptionPane.OK_OPTION) {
			
			Room room = rooms.get(0);
			roomDAO.delete(room);
			
			updateTreeModel();
			resetRoomPanel();
			
			JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
			
			//��֪ͨ��������Ϣ�ı�
			NotificationCenter.postNotification(NotificationCenter.kRoomInfoDidChangeNotification, null);
		}
	}
	
	private void addRoom(String roomNum, String price, int roomType, String tel) {
		RoomDAO roomDAO = new RoomDAO();
		try {
			int cost = Integer.parseInt(price);
			int location = translateRoomLocation(roomNum);
			Room room = new Room(Room.kRoomStatusAvaliable, roomNum, roomType, tel, cost, 0, location);
			roomDAO.save(room);
			
			resetRoomPanel();
			updateTreeModel();
			JOptionPane.showMessageDialog(null, "�½�����ɹ�");
			
			//��֪ͨ��������Ϣ�ı�
			NotificationCenter.postNotification(NotificationCenter.kRoomInfoDidChangeNotification, null);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateServiceInfo(String name) {
		ServerItemDAO itemDAO = new ServerItemDAO();
		List<ServerItem> items = itemDAO.findByContent(name);
		if (items == null || items.size() == 0) {
			JOptionPane.showMessageDialog(null, "�ֿ���û��" + name + "����������");
			return;
		}
		
		int result = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�޸ĸ���������");
		if (result == JOptionPane.OK_OPTION) {
			String price = servicePriceField.getText();
			if (price.length() == 0) {
				JOptionPane.showMessageDialog(null, "����д������Ϣ");
				return;
			}
			
			ServerItem item = items.get(0);
			try {
				float cost = Float.parseFloat(price);
				item.setCost(cost);	
				itemDAO.attachDirty(item);
				
				updateServiceTable();
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
				
			} catch (NumberFormatException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	private void deleteServiceItem(String name) {
		ServerItemDAO itemDAO = new ServerItemDAO();
		List<ServerItem> items = itemDAO.findByContent(name);
		if (items == null || items.size() == 0) {
			JOptionPane.showMessageDialog(null, "�ֿ���û��" + name + "����������");
			return;
		}
		
		int result = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ�����������𣿣�ɾ���󲻿ɻָ���");
		if (result == JOptionPane.OK_OPTION) {
			ServerItem item = items.get(0);
			itemDAO.delete(item);
			
			updateServiceTable();
			JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
		}
	}
	
	private void addServiceItem(String name, float price) {
		ServerItemDAO itemDAO = new ServerItemDAO();
		ServerItem item = new ServerItem(name, price);
		itemDAO.save(item);
		updateServiceTable();
		JOptionPane.showMessageDialog(null, "��ӳɹ�");
	}
	
	private void changePasswd() {
		if (LoginFrame.getCurrentAdmin() == null) {
			return;
		}
		String userName = LoginFrame.getCurrentAdmin().getUsername();
		String passwd = new String(originPasswdField.getPassword());
		if (passwd.equals(LoginFrame.getCurrentAdmin().getPassword()) == false) {
			JOptionPane.showMessageDialog(null, "���������ȷ������");
			return;
		}
		String newPasswd = new String(newPasswdField.getPassword());
		String confirmPasswd = new String(confirmField.getPassword());
		if (newPasswd.equals(confirmPasswd) == false) {
			JOptionPane.showMessageDialog(null, "�������벻һ�£���ȷ������");
			return;
		}
		AdminDAO adminDAO = new AdminDAO();
		Admin admin = LoginFrame.getCurrentAdmin();
		admin.setPassword(newPasswd);
		adminDAO.attachDirty(admin);
		//���µ�ǰ����Ա
		LoginFrame.updateCurrentAdmin();
		JOptionPane.showMessageDialog(null, "���ĳɹ�");
	}
	
}
