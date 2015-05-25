package uml.hotel.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.management.Notification;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import uml.hotel.dao.OrderDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.model.Order;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.notification.Observer;
import uml.hotel.utils.HeaderRenderer;
import uml.hotel.utils.CustomTableModel;
import uml.hotel.utils.Utils;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ReservationFrame extends JFrame implements Observer {

	private JPanel contentPane;
	private JTable table;
	private JButton addReservationBtn;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservationFrame frame = new ReservationFrame();
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
	public ReservationFrame() {
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 514, 441);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Ԥ����Ϣ", panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 489, 58);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		addReservationBtn = new JButton("\u65B0\u589E");
		Utils.createToolBarItem(addReservationBtn, "pic/new.gif");
		addReservationBtn.setBounds(10, 10, 63, 41);
		panel_1.add(addReservationBtn);
		//����Ԥ��
		addReservationBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddReservationFrame().setVisible(true);
			}
		});
		
		JButton button = new JButton("\u7F16\u8F91");
		Utils.createToolBarItem(button, "pic/modi3.gif");
		button.setBounds(105, 10, 63, 41);
		panel_1.add(button);
		
		JButton button_1 = new JButton("\u5220\u9664");
		Utils.createToolBarItem(button_1, "pic/cancel.gif");
		button_1.setBounds(218, 10, 63, 41);
		panel_1.add(button_1);
		//ɾ��
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex < 0) {
					JOptionPane.showMessageDialog(null, "��ѡ��һ��Ҫɾ���ļ�¼");
					return;
				}
				
				int result = JOptionPane.showConfirmDialog(null, "ɾ���󲻿ɻָ���ȷ��ɾ����?");
				if (result == JOptionPane.OK_OPTION) {
					int id = (Integer)table.getModel().getValueAt(selectedIndex, 0);
					OrderDAO orderDAO = new OrderDAO();
					Order order = orderDAO.findById(id);
					orderDAO.delete(order);
					
					RoomDAO roomDAO = new RoomDAO();
					Room room = (Room)roomDAO.findByNumber(order.getRoomNum()).get(0);
					room.setStatus(Room.kRoomStatusAvaliable);
					roomDAO.attachDirty(room);
					
					//��֪ͨ������״̬�ı�
					NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, order.getRoomNum());
					
					//ˢ�±������
					updateTableData();
				}
			}
		});
		
		JButton button_2 = new JButton("\u5F00\u5355");
		Utils.createToolBarItem(button_2, "pic/b2.gif");
		button_2.setBounds(305, 10, 63, 41);
		panel_1.add(button_2);
		//����
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex < 0) {
					JOptionPane.showMessageDialog(null, "��ѡ��һ�м�¼");
					return;
				}
				
				//�ж�״̬
				String state = (String)table.getModel().getValueAt(selectedIndex, 1);
				if (state.equals("Ԥ����") == false) {
					JOptionPane.showMessageDialog(null, "��ѡ��ķ��䲻��Ԥ��״̬");
					return;
				}
				
				//��ȡѡ��Ԥ����Ϣ
				int id = (Integer)table.getModel().getValueAt(selectedIndex, 0);
				OrderDAO orderDAO = new OrderDAO();
				Order order = orderDAO.findById(id);
				
				//����user
				String userName = order.getUserName();
				String company = order.getCompany();
				String tel = order.getTel();
				int from = order.getUserFrom();
				User user = new User(userName, tel, "", 0, 0, from, "", company, "");
				
				//��ȡ����״̬��Ϣ
				String roomNum = order.getRoomNum();
				RoomStatusDAO statusDAO = new RoomStatusDAO();
				List<RoomStatus> list = statusDAO.findByRoomId(roomNum);
				RoomStatus status = list.get(list.size() - 1);
				
				//��������
				new MakeOrderFrame(status, user).setVisible(true);
			}
		});
		
		JButton button_3 = new JButton("\u9000\u51FA");
		Utils.createToolBarItem(button_3, "pic/exit.gif");
		button_3.setBounds(416, 10, 63, 41);
		panel_1.add(button_3);
		button_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.setBounds(190, 0, 1, 58);
		panel_1.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_4.setBounds(394, 0, 1, 58);
		panel_1.add(panel_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 70, 489, 1);
		panel_2.setBackground(Color.gray);
		panel.add(panel_2);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 78, 489, 324);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 489, 324);
		panel_5.add(scrollPane);
		
		CustomTableModel tableModel = new CustomTableModel();
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		//����Ϊ��ѡ
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//��ʹ���Զ���������������ʱ�������
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//�Զ���tableheaderrender����column name������ʾ
		table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
		scrollPane.setViewportView(table);
		
		String[] columnNames = {"Ԥ������", "Ԥ��״̬", "��������", "������", "��������", "��˾����", "��ϵ�绰", "������Դ", "Ԥ��ʱ��", "Ԥ��ʱ��"};
		int[] columnWidth = {60, 60, 120, 60, 80, 100, 80, 60, 180, 180};
		tableModel.setColumnNames(columnNames);
		for (int i = 0; i < columnWidth.length; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}
		
		updateTableData();
		
		NotificationCenter.addNotification(this, NotificationCenter.kReservationStateDidChangeNotification);
	}
	
	public void updateTableData() {
		//��ӵ�ǰ����Ԥ���������
		OrderDAO orderDAO = new OrderDAO();
		List<Order> orders = orderDAO.findAll();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (int i = 0; i < orders.size(); i++) {
			Order order = orders.get(i);
			
			Vector<Object> row = new Vector<Object>();
			//Ԥ������
			row.add(order.getId());
			//Ԥ��״̬
			int state = order.getState();
			if (state == Order.kOrderStateOrdering) {
				row.add("Ԥ����");
			} else if (state == Order.kOrderStateFinished) {
				row.add("Ԥ�����");
			} else if (state == Order.kOrderStateCanceled) {
				row.add("Ԥ��ȡ��");
			} else {
				row.add("");
			}
			//��������
			int type = order.getType();
			if (type == Room.kRoomTypeStandardSignle) {
				row.add("��׼���˼�");
			} else if (type == Room.kRoomTypeStandardDouble) {
				row.add("��׼˫�˼�");
			} else if (type == Room.kRoomTypeLuxurySingle) {
				row.add("������");
			} else {
				row.add("");
			}
			//������
			row.add(order.getRoomNum());
			//��������
			row.add(order.getUserName());
			//��˾����
			row.add(order.getCompany());
			//��ϵ�绰
			row.add(order.getTel());
			//������Դ
			int from = order.getUserFrom();
			if (from == User.kUserFromNormal) {
				row.add("��ͨ�û�");
			} else if (from == User.kUserFromWebsite) {
				row.add("��վ");
			} else if (from == User.kUserFromTravelAgency) {
				row.add("������");
			} else {
				row.add("����");
			}
			//Ԥ��ʱ��
			row.add(order.getOrderTime());
			//Ԥ��ʱ��
			row.add(order.getArriveTime());
			
			//����ǰ����Ϣ��ӵ��б�����Դ��
			data.add(row);
		}
		
		Collections.sort(data, orderComparator);
		
		CustomTableModel model = (CustomTableModel)table.getModel();
		model.setData(data);
	}
	
	//�Զ�����Ϣ����
	private final Comparator<Vector<Object>> orderComparator = new Comparator<Vector<Object>>() {

		@Override
		public int compare(Vector<Object> o1, Vector<Object> o2) {
			if (o1 == null || o1.size() < 2) {
			    return -1;
			}
			if (o2 == null || o2.size() < 2) {
			    return 1;
			}
			String s1 = (String)o1.get(1);
			String s2 = (String)o2.get(1);
			return (s1.compareTo(s2));
		}
		
	};

	@Override
	public void receivedNotification(String name, Object userinfo) {
		// TODO Auto-generated method stub
		if (name == NotificationCenter.kReservationStateDidChangeNotification) {
			updateTableData();
		}
	}
	
	//�����ֱ�ӵ������������Ľ���
	public JButton getAddReservationBtn() {
		return addReservationBtn;
	}
}
