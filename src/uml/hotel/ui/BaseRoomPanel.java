package uml.hotel.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import uml.hotel.dao.CostDAO;
import uml.hotel.dao.OrderDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.ServerDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Cost;
import uml.hotel.model.Order;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.Server;
import uml.hotel.model.User;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.notification.Observer;
import uml.hotel.utils.Utils;

public class BaseRoomPanel extends JPanel implements Observer, ActionListener {
	protected Vector<JButton> buttons;
	protected String prefix;
	
	private JPopupMenu menu;
	//单击查看状态时选中的按钮
	private JButton selectedBtn;
	
	//换房时拖拽的按钮
	private JButton draggedButton;
	private Point startPoint;
	
	//当前最后一个房间的下一个房间按钮的下标
	private int index;

	/**
	 * Create the panel.
	 */
	public BaseRoomPanel() {
		setLayout(null);
		this.buttons = new Vector<JButton>();
		initPopupMenu();
		
		createButtons();
		
		setUpButtons();
		
		//监听房间状态改变的通知
		NotificationCenter.addNotification(this, NotificationCenter.kRoomStatusDidChangeNotification);
		//监听显示房间类型状态改变的通知
		NotificationCenter.addNotification(this, NotificationCenter.kShowSpecialRoomNotification);
		//监听房间数量和信息的改变的通知
		NotificationCenter.addNotification(this, NotificationCenter.kRoomInfoDidChangeNotification);
	}
	
	public void addRoomButtom(String btnName) {
		final int width = 83;
		final int height = 68;
		final int row_room_count = 10;
		final int row_padding = 10;
		final int column_padding = 25;
		
		JButton btnNewButton = new JButton(btnName);
		btnNewButton.setBounds(row_padding + (index % row_room_count) * (width + row_padding),
				column_padding + (index / row_room_count) * (height + column_padding),
				width, height);
		Utils.createRoomButton(btnNewButton);
		add(btnNewButton);
		buttons.add(btnNewButton);
		
		index++;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
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
				RoomStatusDAO statusDAO = new RoomStatusDAO();
				List<RoomStatus> list = statusDAO.findByRoomId(selectedBtn.getText());
				RoomStatus status = list.get(list.size() - 1);
				new ConsumeFrame(status).setVisible(true);
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
				//设置正确的房态
				setProperRoomStateForRoom(room);
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
				new ChangeRoomFrame((selectedBtn.getText())).setVisible(true);
			}
		});
		menu.add(item5);
	}
	
	/**
	 *	创建按钮 
	 */
	public void createButtons() {
		RoomDAO roomDAO = new RoomDAO();
		List<Room> rooms = roomDAO.findByRoomTypePrefix(prefix);
		for (int i = 1; i <= rooms.size(); i++) {
			DecimalFormat df = new DecimalFormat("0000");
			String suffix = df.format(i);
			addRoomButtom(prefix + suffix);
		}		
	}
	
	/**
	 * 消除所有按钮
	 */
	public void removeButtons() {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).getParent().remove(buttons.get(i));
		}
		buttons.removeAllElements();
		index = 0;
	}
	
	/**
	 * 更新所有按钮
	 */
	public void updateButtons() {
		removeButtons();
		createButtons();
		setUpButtons();
		repaint();
	}
	
	/**
	 * 为所有按钮添加监听器
	 */
	public void setUpButtons() {
		//子类需要先为buttons赋值.
		for (final JButton button : buttons) {
			button.addActionListener(this);
			button.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						RoomDAO roomDAO = new RoomDAO();
						Room room = (Room)roomDAO.findByNumber(button.getText()).get(0);
						if (room.getStatus() == Room.kRoomStatusUsed) {
							//如果当前房间有记录
							RoomStatusDAO statusDAO = new RoomStatusDAO();
							List<RoomStatus> list = statusDAO.findByRoomId(button.getText());
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
					} else {
						//如果不是右键单击
						//复制选中按钮的一切属性
						JButton btn = (JButton)ev.getComponent();
						draggedButton = new JButton();
						draggedButton.setText(btn.getText());
						draggedButton.setVerticalTextPosition(SwingUtilities.BOTTOM);
						draggedButton.setHorizontalTextPosition(SwingUtilities.CENTER);
						draggedButton.setBounds(btn.getBounds());
						draggedButton.setIcon(btn.getIcon());
						btn.getParent().add(draggedButton);
						
						//记录起点
						startPoint = SwingUtilities.convertPoint(draggedButton, ev.getPoint(), draggedButton.getParent());
					}
					 
				 }	// mousePressed
				
				@Override
				public void mouseReleased(MouseEvent ev) {
					//忽略右键单击
					if (draggedButton == null) {
						return;
					}
					
					//判断是否落在另一个房间上，并且不是已经选中的房间
					for (JButton btn : buttons) {
						if (!btn.getText().equals(draggedButton.getText()) && btn.getBounds().intersects(draggedButton.getBounds())) {
							//换房
							System.out.println("换房 from " + draggedButton.getText() + "to " + btn.getText());
							RoomDAO roomDAO = new RoomDAO();
							Room target = (Room)roomDAO.findByNumber(btn.getText()).get(0);
							Room source = (Room)roomDAO.findByNumber(draggedButton.getText()).get(0);
						
							//只有已用的房间可以使用换房间功能
							if (source.getStatus() == Room.kRoomStatusUsed) {
								if (target.getStatus() != Room.kRoomStatusAvaliable) {
									JOptionPane.showMessageDialog(null, "目前房间处于不可供状态，调换失败");
								} else {
									int result = JOptionPane.showConfirmDialog(null, "确认要换房吗?");
									if (result == JOptionPane.OK_OPTION) {
										target.setStatus(source.getStatus());
										
										//查找source正确的房态
										setProperRoomStateForRoom(source);
//										
//										OrderDAO orderDAO = new OrderDAO();
//										List<Order> orders = orderDAO.findByRoomNum(source.getNumber());
//										if (orders != null && orders.size() > 0) {
//											Order order = orders.get(orders.size() - 1);
//											SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//											Date arrive;
//											try {
//												arrive = df.parse(order.getArriveTime());
//												if (arrive.compareTo(new Date()) != -1) {
//													source.setStatus(Room.kRoomStatusReserved);
//												}
//											} catch (ParseException e) {
//												// TODO Auto-generated catch block
//												e.printStackTrace();
//											}
//										} else {
//											source.setStatus(Room.kRoomStatusAvaliable);
//										}
										
										
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
											if (server.getFinished() == Server.kServerStateNotFinish) {
												server.setRoomId(target.getId());
												serverDAO.attachDirty(server);												
											}
										}
										
										//更新房间状态
										roomDAO.attachDirty(source);
										roomDAO.attachDirty(target);
										//发送通知：房间状态改变
										NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, source.getNumber());
										NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, target.getNumber());
									}
									
								}
							}
							
							break;
						}
					}
					
					if (draggedButton != null && draggedButton.getParent() != null) {
						draggedButton.getParent().remove(draggedButton);	
						draggedButton = null;
					}
					repaint();
				}
				
			});
			
			
			button.addMouseMotionListener(new MouseMotionAdapter() {
				
				@Override
				public void mouseDragged(MouseEvent e) {
					draggedButton.setLocation(e.getX() + startPoint.x - draggedButton.getWidth(), e.getY() + startPoint.y - draggedButton.getHeight());
					repaint();
				}
			});
			
			
		}
	}

	@Override
	public void receivedNotification(String name, Object userInfo) {
		// TODO Auto-generated method stub
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
			
		} else if (name.equals(NotificationCenter.kRoomInfoDidChangeNotification)) {
			updateButtons();
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
