package uml.hotel.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JButton;

import uml.hotel.notification.NotificationCenter;
import uml.hotel.utils.RoomStateTipTimer;
import uml.hotel.utils.Utils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTabbedPane;

import java.awt.Frame;

import javax.swing.JPopupMenu;


/**
 * @author 杨闯
 *
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JLabel timeLabel;
	
	public static final int kShowAvailableRoom = 1;
	public static final int kShowUsedRoom = 0;
	public static final int kShowReservedRoom = 2;
	public static final int kShowAllRoom = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	/**
	 * 
	 */
	/**
	 * 
	 */
	public MainFrame() {
		setTitle("疯狂宾馆管理系统 V1.0");
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1368, 1368);
		
		initMenuItems();
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addMenuButtons();
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 79, 200, 25);
		contentPane.add(panel_1);
		
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String nowString = df.format(date);
		timeLabel = new JLabel(nowString);
		panel_1.add(timeLabel);
		
		//开启计时器更新时间
		Timer t = new Timer();
		t.schedule(new UpdateTimeTimer(), date, 1000);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 104, 200, 551);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 5, 180, 536);
		panel_2.add(tabbedPane);
		
		StatusPanel statusPanel = new StatusPanel();
		tabbedPane.addTab("状态", statusPanel);
		tabbedPane.addTab("便签", new JPanel());
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("提醒", panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(196, 79, 1172, 576);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(0, 0, 1162, 415);
		panel_4.add(panel_5);
		panel_5.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 10, 1127, 395);
		tabbedPane_1.addTab("标准单人间", new StandardSingleJPanel());
		tabbedPane_1.addTab("标准双人间", new StandardDoubleJPanel());
		tabbedPane_1.addTab("豪华房间", new LuxurySingleJPanel());
//		tabbedPane_1.addTab("豪华双人间", new LuxuryDoubleJPanel());
//		tabbedPane_1.addTab("商务套房", new BusinessSuiteJPanel());
//		tabbedPane_1.addTab("总统套房", new PresidentialSuiteJPanel());
		panel_5.add(tabbedPane_1);
		
		addBottomButtons(panel_4);
		
		
		//添加计时器，在合适时间提醒房间状态
		//5分钟更新一次
		new Timer().schedule(new RoomStateTipTimer(), date, 5 * 60 * 1000);
	}
	
	private void initMenuItems() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u6765\u5BBE\u767B\u8BB0");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("\u6563\u5BA2\u5F00\u5355");
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("\u56E2\u4F53\u5F00\u5355");
		menu.add(menuItem_1);
		
		JMenu menu_1 = new JMenu("\u6536\u94F6\u7ED3\u7B97");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u5BBE\u5BA2\u7ED3\u8D26");
		menu_1.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("\u5408\u5E76\u8D26\u5355");
		menu_1.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("\u62C6\u5206\u8D26\u5355");
		menu_1.add(menuItem_4);
		
		JMenu menu_2 = new JMenu("\u7CFB\u7EDF\u7EF4\u62A4");
		menuBar.add(menu_2);
		
		JMenuItem menuItem_5 = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");
		menu_2.add(menuItem_5);
		
		JMenuItem menuItem_6 = new JMenuItem("\u7CFB\u7EDF\u8BBE\u7F6E");
		menu_2.add(menuItem_6);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	private void addMenuButtons() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1352, 77);
		contentPane.add(panel);
		panel.setBorder(new LineBorder(Color.gray));
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(10, 10, 48, 48);
		
		
		Utils.createToolBarItem(btnNewButton, "pic/ToolBar/m01.gif");

		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChooseRoomFrame(ChooseRoomFrame.kChooseRoomTypeMakeOrder).setVisible(true);
			}
		});
		
		panel.add(btnNewButton);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReservationFrame().setVisible(true);
			}
		}); 
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(70, 10, 48, 48);
		
		Utils.createToolBarItem(button, "pic/ToolBar/m05.gif");
		
		panel.add(button);
		
		JLabel label = new JLabel("顾客开单");
		label.setBounds(10, 58, 54, 15);
		panel.add(label);
		
		JLabel label_1 = new JLabel("预定管理");
		label_1.setBounds(70, 58, 54, 15);
		panel.add(label_1);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChooseRoomFrame(ChooseRoomFrame.kChooseRoomTypePayoff).setVisible(true);
			}
		});
		button_1.setBounds(130, 10, 48, 48);
		
		Utils.createToolBarItem(button_1, "pic/ToolBar/m10.gif");
		panel.add(button_1);
		
		JLabel label_2 = new JLabel("客户结账");
		label_2.setBounds(130, 58, 54, 15);
		panel.add(label_2);
		
		JButton button_2 = new JButton("");
		button_2.setBounds(190, 10, 48, 48);
		
		Utils.createToolBarItem(button_2, "pic/ToolBar/m02.gif");
		
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 客户换房
				new ChangeRoomFrame().setVisible(true);
			}
		});
		
		panel.add(button_2);
		
		JLabel label_3 = new JLabel("客户换房");
		label_3.setBounds(190, 58, 54, 15);
		panel.add(label_3);
		
		JButton button_6 = new JButton("");
		button_6.setBounds(250, 10, 48, 48);
		Utils.createToolBarItem(button_6, "pic/ToolBar/m03.gif");
		panel.add(button_6);
		button_6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//增加消费
			}
		});
		
		JLabel label_4 = new JLabel("增加消费");
		label_4.setBounds(250, 58, 54, 15);
		panel.add(label_4);
		
		JButton button_7 = new JButton("");
		button_7.setBounds(310, 10, 48, 48);
		Utils.createToolBarItem(button_7, "pic/ToolBar/m06.gif");
		panel.add(button_7);
		button_7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//营业查询
			}
		});
		
		JLabel label_5 = new JLabel("营业查询");
		label_5.setBounds(310, 58, 54, 15);
		panel.add(label_5);
	}
	
	private void addBottomButtons(JPanel container) {
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 414, 1136, 47);
		container.add(panel_6);
		panel_6.setLayout(null);
		
		final MenuButton btnNewButton_1 = new MenuButton("显示全部", false);
		btnNewButton_1.setBounds(31, 14, 118, 23);
		panel_6.add(btnNewButton_1);
		
		JPopupMenu showStatusMenu = new JPopupMenu();
		
		final JMenuItem item1 = new JMenuItem("显示全部");
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnNewButton_1.setText(item1.getText());
				btnNewButton_1.setSelected(false);
				
				//发通知，显示全部
				NotificationCenter.postNotification(NotificationCenter.kShowSpecialRoomNotification, kShowAllRoom);
			}
		});
		
		final JMenuItem item2 = new JMenuItem("显示可用 ");
		item2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnNewButton_1.setText(item2.getText());
				btnNewButton_1.setSelected(false);
				
				//发通知，显示可用
				NotificationCenter.postNotification(NotificationCenter.kShowSpecialRoomNotification, kShowAvailableRoom);
			}
		});
		
		final JMenuItem item3 = new JMenuItem("显示占用");
		item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnNewButton_1.setText(item3.getText());
				btnNewButton_1.setSelected(false);
				
				//发通知，显示占用
				NotificationCenter.postNotification(NotificationCenter.kShowSpecialRoomNotification, kShowUsedRoom);
			}
		});
		
		final JMenuItem item4 = new JMenuItem("显示预定");
		item4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnNewButton_1.setText(item4.getText());
				btnNewButton_1.setSelected(false);
				
				//发通知，显示预定
				NotificationCenter.postNotification(NotificationCenter.kShowSpecialRoomNotification, kShowReservedRoom);
			}
		});
		showStatusMenu.add(item1);
		showStatusMenu.add(item2);
		showStatusMenu.add(item3);
		showStatusMenu.add(item4);
		
		btnNewButton_1.setMenu(showStatusMenu);
		
		JButton button_3 = new JButton("\u623F\u52A1\u4E2D\u5FC3");
		button_3.setBounds(170, 14, 93, 23);
		panel_6.add(button_3);
		
		JButton button_4 = new JButton("\u62BC\u91D1\u72B6\u6001");
		button_4.setBounds(289, 14, 93, 23);
		panel_6.add(button_4);
	}
	
	private class UpdateTimeTimer extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			String nowString = df.format(date);
			timeLabel.setText(nowString);
		}
		
	}
	
}
