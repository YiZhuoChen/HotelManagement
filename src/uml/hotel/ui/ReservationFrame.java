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
		tabbedPane.addTab("预定信息", panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 489, 58);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		addReservationBtn = new JButton("\u65B0\u589E");
		Utils.createToolBarItem(addReservationBtn, "pic/new.gif");
		addReservationBtn.setBounds(10, 10, 63, 41);
		panel_1.add(addReservationBtn);
		//新增预定
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
		//删除
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex < 0) {
					JOptionPane.showMessageDialog(null, "请选中一行要删除的记录");
					return;
				}
				
				int result = JOptionPane.showConfirmDialog(null, "删除后不可恢复，确认删除吗?");
				if (result == JOptionPane.OK_OPTION) {
					int id = (Integer)table.getModel().getValueAt(selectedIndex, 0);
					OrderDAO orderDAO = new OrderDAO();
					Order order = orderDAO.findById(id);
					orderDAO.delete(order);
					
					RoomDAO roomDAO = new RoomDAO();
					Room room = (Room)roomDAO.findByNumber(order.getRoomNum()).get(0);
					room.setStatus(Room.kRoomStatusAvaliable);
					roomDAO.attachDirty(room);
					
					//发通知，房间状态改变
					NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, order.getRoomNum());
					
					//刷新表格数据
					updateTableData();
				}
			}
		});
		
		JButton button_2 = new JButton("\u5F00\u5355");
		Utils.createToolBarItem(button_2, "pic/b2.gif");
		button_2.setBounds(305, 10, 63, 41);
		panel_1.add(button_2);
		//开单
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex < 0) {
					JOptionPane.showMessageDialog(null, "请选中一行记录");
					return;
				}
				
				//判断状态
				String state = (String)table.getModel().getValueAt(selectedIndex, 1);
				if (state.equals("预定中") == false) {
					JOptionPane.showMessageDialog(null, "您选择的房间不是预定状态");
					return;
				}
				
				//获取选中预定信息
				int id = (Integer)table.getModel().getValueAt(selectedIndex, 0);
				OrderDAO orderDAO = new OrderDAO();
				Order order = orderDAO.findById(id);
				
				//构造user
				String userName = order.getUserName();
				String company = order.getCompany();
				String tel = order.getTel();
				int from = order.getUserFrom();
				User user = new User(userName, tel, "", 0, 0, from, "", company, "");
				
				//获取房间状态信息
				String roomNum = order.getRoomNum();
				RoomStatusDAO statusDAO = new RoomStatusDAO();
				List<RoomStatus> list = statusDAO.findByRoomId(roomNum);
				RoomStatus status = list.get(list.size() - 1);
				
				//开单界面
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
		//设置为单选
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//不使用自动放缩，当表格过长时横向滚动
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//自定义tableheaderrender来让column name居中显示
		table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
		scrollPane.setViewportView(table);
		
		String[] columnNames = {"预定单号", "预定状态", "房间类型", "房间编号", "宾客姓名", "公司名称", "联系电话", "宾客来源", "预定时间", "预抵时间"};
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
		//添加当前所有预定到表格中
		OrderDAO orderDAO = new OrderDAO();
		List<Order> orders = orderDAO.findAll();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (int i = 0; i < orders.size(); i++) {
			Order order = orders.get(i);
			
			Vector<Object> row = new Vector<Object>();
			//预定单号
			row.add(order.getId());
			//预定状态
			int state = order.getState();
			if (state == Order.kOrderStateOrdering) {
				row.add("预定中");
			} else if (state == Order.kOrderStateFinished) {
				row.add("预定完成");
			} else if (state == Order.kOrderStateCanceled) {
				row.add("预定取消");
			} else {
				row.add("");
			}
			//房间类型
			int type = order.getType();
			if (type == Room.kRoomTypeStandardSignle) {
				row.add("标准单人间");
			} else if (type == Room.kRoomTypeStandardDouble) {
				row.add("标准双人间");
			} else if (type == Room.kRoomTypeLuxurySingle) {
				row.add("豪华间");
			} else {
				row.add("");
			}
			//房间编号
			row.add(order.getRoomNum());
			//宾客姓名
			row.add(order.getUserName());
			//公司名称
			row.add(order.getCompany());
			//联系电话
			row.add(order.getTel());
			//宾客来源
			int from = order.getUserFrom();
			if (from == User.kUserFromNormal) {
				row.add("普通用户");
			} else if (from == User.kUserFromWebsite) {
				row.add("网站");
			} else if (from == User.kUserFromTravelAgency) {
				row.add("旅行社");
			} else {
				row.add("其它");
			}
			//预定时间
			row.add(order.getOrderTime());
			//预抵时间
			row.add(order.getArriveTime());
			
			//将当前行信息添加到列表数据源中
			data.add(row);
		}
		
		Collections.sort(data, orderComparator);
		
		CustomTableModel model = (CustomTableModel)table.getModel();
		model.setData(data);
	}
	
	//对订单信息排序
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
	
	//让外界直接调出新增订单的界面
	public JButton getAddReservationBtn() {
		return addReservationBtn;
	}
}
