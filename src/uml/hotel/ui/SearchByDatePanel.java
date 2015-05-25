package uml.hotel.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sun.istack.internal.FinalArrayList;

import uml.hotel.dao.BillDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Bill;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;
import uml.hotel.utils.CalendarFrame;
import uml.hotel.utils.CalendarFrameDelegate;
import uml.hotel.utils.CustomTableModel;
import uml.hotel.utils.PCalendar;

public class SearchByDatePanel extends SearchPanel implements CalendarFrameDelegate {
	
	private JTextField startTimeTextField;
	private JTextField endTimeTextField;
	private JTextField selectedTextField;
	
	
	private CalendarFrame calendarFrame;
	
	public SearchByDatePanel(final Vector<Vector<Object>> data, final JTable table) {
		super(data, table);
		
		JLabel startTimeLabel = new JLabel("\u8D77\u59CB\u65F6\u95F4\uFF1A");
		startTimeLabel.setBounds(10, 23, 75, 15);
		add(startTimeLabel);
		
		startTimeTextField = new JTextField();
		startTimeTextField.setBounds(95, 20, 100, 21);
		add(startTimeTextField);
		startTimeTextField.setColumns(10);
		
		JButton calendarBtn1 = new JButton("📅");
		calendarBtn1.setBounds(180, 20, 40, 21);
		add(calendarBtn1);
		calendarBtn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				selectedTextField = startTimeTextField;
				showCalendar();
			}
		});
		
		JLabel endTimeLabel = new JLabel("截止时间：");
		endTimeLabel.setBounds(257, 23, 75, 15);
		add(endTimeLabel);
		
		endTimeTextField = new JTextField();
		endTimeTextField.setColumns(10);
		endTimeTextField.setBounds(342, 20, 100, 21);
		add(endTimeTextField);
		
		JButton calendarBtn2 = new JButton("📅");
		calendarBtn2.setBounds(428, 20, 40, 21);
		add(calendarBtn2);
		calendarBtn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				selectedTextField = endTimeTextField;
				showCalendar();
			}
		});
		
		
		final JRadioButton radioButton = new JRadioButton("\u5168\u90E8\u8D26\u5355");
		radioButton.setBounds(471, 19, 92, 23);
		add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("\u5DF2\u7ED3\u8D26\u8D26\u5355");
		radioButton_1.setBounds(568, 19, 100, 23);
		add(radioButton_1);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioButton);
		bg.add(radioButton_1);
		radioButton.setSelected(true);
		
		JButton queryButton = new JButton("\u67E5\u8BE2");
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data.removeAllElements();
				((CustomTableModel)table.getModel()).setData(data);
				
				final boolean showAll = radioButton.isSelected();
				final String startTime = startTimeTextField.getText().length() == 0 ? "" : startTimeTextField.getText() + " 00:00:00";
				final String endTime = endTimeTextField.getText().length() == 0 ? "" : endTimeTextField.getText() + " 00:00:00";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				final RoomStatusDAO statusDAO = new RoomStatusDAO();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (showAll) {
							List<RoomStatus> allStatus = statusDAO.findAll();
							for (RoomStatus roomStatus : allStatus) {

								//获取客户信息
								UserDAO userDAO = new UserDAO();
								User user = userDAO.findById(roomStatus.getUserId());
							
								//获取房间信息
								RoomDAO roomDAO = new RoomDAO();
								Room room = (Room)roomDAO.findByNumber(roomStatus.getRoomId()).get(0);
								
								addRowWithTime(startTime, endTime, roomStatus, user, room);
							}
						} else {
							BillDAO billDAO = new BillDAO();
							List<Bill> allBills = billDAO.findAll();
							for (Bill bill : allBills) {
								UserDAO userDAO = new UserDAO();
								User user = userDAO.findById(bill.getUserId());
								
								RoomStatus status = statusDAO.findById(bill.getRoomStatusId());
								
								RoomDAO roomDAO = new RoomDAO();
								Room room = (Room)roomDAO.findByNumber(status.getRoomId()).get(0);
								
								addRowWithTime(startTime, endTime, status, user, room);
								
							}	//for
							
						}
						
						//更新数据源
						((CustomTableModel)table.getModel()).setData(data);
					}
				}).start();
				
				
				
			}
		});
		queryButton.setBounds(690, 19, 93, 23);
		add(queryButton);
	}
	
	public void showCalendar() {
		calendarFrame = new CalendarFrame(new PCalendar());
		calendarFrame.setDelegate(this);
		calendarFrame.setVisible(true);
		calendarFrame.showCalendar();
	}
	
	@Override
	public void calendarDidSelectWithDayName(String day) {
		String defaultDateString = day;
		
		if (selectedTextField != null) {
			selectedTextField.setText(defaultDateString);
		}
		
		if (calendarFrame != null) {
			calendarFrame.setVisible(false);
		}
	}
}
