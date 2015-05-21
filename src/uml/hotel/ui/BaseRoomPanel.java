package uml.hotel.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;
import uml.hotel.notification.NotificationCenter;
import uml.hotel.notification.Observer;
import uml.hotel.utils.Utils;

public abstract class BaseRoomPanel extends JPanel implements Observer, ActionListener {
	protected JButton[] buttons;
	private JPopupMenu menu;
	//�����鿴״̬ʱѡ�еİ�ť
	private JButton selectedBtn;
	
	//����ʱ��ק�İ�ť
	private JButton draggedButton;
	private Point startPoint;

	/**
	 * Create the panel.
	 */
	public BaseRoomPanel() {
		setLayout(null);
		
		initPopupMenu();
		
		createButtons();
		
		setUpButtons();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		NotificationCenter.postNotification(NotificationCenter.kRoomDidSelectedNotification, e.getSource());
	}
	
	private void initPopupMenu() {
		menu = new JPopupMenu();
		final JMenuItem item = new JMenuItem("���ͽ���");;
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
		
		final JMenuItem item1 = new JMenuItem("��������");
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menu.add(item1);
		final JMenuItem item2 = new JMenuItem("���Ϳ���");
		item2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MakeOrderFrame(selectedBtn.getText()).setVisible(true);
			}
		});
		menu.add(item2);
		final JMenuItem item3 = new JMenuItem("����Ԥ��");
		item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ReservationFrame rf = new ReservationFrame();
				rf.setVisible(true);
				rf.getAddReservationBtn().doClick();
			}
		});
		menu.add(item3);
		final JMenuItem item4 = new JMenuItem("�������");
		item4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomDAO roomDAO = new RoomDAO();
				Room room = (Room)roomDAO.findByNumber(selectedBtn.getText()).get(0);
				room.setStatus(Room.kRoomStatusAvaliable);
				//���浽���ݿ�
				roomDAO.attachDirty(room);
				//��֪ͨ������״̬�ı�
				NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, room.getNumber());
			}
		});
		menu.add(item4);
		
		final JMenuItem item5 = new JMenuItem("��������");
		item5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		menu.add(item5);
	}
	
	/**
	 *	������ť 
	 */
	abstract public void createButtons();
	
	/**
	 * Ϊ���а�ť��Ӽ�����
	 */
	public void setUpButtons() {
		//������Ҫ��Ϊbuttons��ֵ.
		for (final JButton button : buttons) {
			button.addActionListener(this);
			button.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						RoomDAO roomDAO = new RoomDAO();
						Room room = (Room)roomDAO.findByNumber(button.getText()).get(0);
						if (room.getStatus() == Room.kRoomStatusUsed) {
							//�����ǰ�����м�¼
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
						//��������Ҽ�����
						//����ѡ�а�ť��һ������
						JButton btn = (JButton)ev.getComponent();
						draggedButton = new JButton();
						draggedButton.setText(btn.getText());
						draggedButton.setBounds(btn.getBounds());
						draggedButton.setIcon(btn.getIcon());
						btn.getParent().add(draggedButton);
						
						//��¼���
						startPoint = SwingUtilities.convertPoint(draggedButton, ev.getPoint(), draggedButton.getParent());
					}
					 
				 }	// mousePressed
				
				@Override
				public void mouseReleased(MouseEvent ev) {
					
					//�ж��Ƿ�������һ�������ϣ����Ҳ����Ѿ�ѡ�еķ���
					for (JButton btn : buttons) {
						if (!btn.getText().equals(draggedButton.getText()) && btn.getBounds().intersects(draggedButton.getBounds())) {
							//����
							System.out.println("���� from " + draggedButton.getText() + "to " + btn.getText());
							RoomDAO roomDAO = new RoomDAO();
							Room target = (Room)roomDAO.findByNumber(btn.getText()).get(0);
							Room source = (Room)roomDAO.findByNumber(draggedButton.getText()).get(0);
						
							//ֻ�����õķ������ʹ�û����书��
							if (source.getStatus() == Room.kRoomStatusUsed) {
								if (target.getStatus() != Room.kRoomStatusAvaliable) {
									JOptionPane.showMessageDialog(null, "Ŀǰ���䴦�ڲ��ɹ�״̬������ʧ��");
								} else {
									int result = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ������?");
									if (result == JOptionPane.OK_OPTION) {
										target.setStatus(source.getStatus());
										source.setStatus(Room.kRoomStatusAvaliable);
										
										//�����µķ���״̬��Ϣ
										RoomStatusDAO statusDAO = new RoomStatusDAO();
										List<RoomStatus> list = statusDAO.findByRoomId(source.getNumber());
										RoomStatus sourceStatus = list.get(list.size() - 1);
										RoomStatus targetStatus = new RoomStatus(sourceStatus.getUserId(), 
												sourceStatus.getStartTime(), sourceStatus.getEndTime(), 
												target.getNumber(), sourceStatus.getDeposit(), 
												sourceStatus.getTime(), sourceStatus.getLongStay(), 
												sourceStatus.getType());
										statusDAO.save(targetStatus);
										
										//���·���״̬
										roomDAO.attachDirty(source);
										roomDAO.attachDirty(target);
										//����֪ͨ������״̬�ı�
										NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, source.getNumber());
										NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, target.getNumber());
									}
									
								}
							}
							
							break;
						}
					}
					
					
					draggedButton.getParent().remove(draggedButton);
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
		
		//��������״̬�ı��֪ͨ
		NotificationCenter.addNotification(this, NotificationCenter.kRoomStatusDidChangeNotification);
		//������ʾ��������״̬�ı��֪ͨ
		NotificationCenter.addNotification(this, NotificationCenter.kShowSpecialRoomNotification);
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
				//��ʾȫ������
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
	
	
	
}
