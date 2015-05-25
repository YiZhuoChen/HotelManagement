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

public class SearchByDatePanel extends JPanel implements CalendarFrameDelegate {
	
	private JTextField startTimeTextField;
	private JTextField endTimeTextField;
	private JTextField selectedTextField;
	
	private CalendarFrame calendarFrame;
	
	public SearchByDatePanel(final Vector<Vector<Object>> data, final JTable table) {
		setLayout(null);
		
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
				
				boolean showAll = radioButton.isSelected();
				String startTime = startTimeTextField.getText().length() == 0 ? "" : startTimeTextField.getText() + " 00:00:00";
				String endTime = endTimeTextField.getText().length() == 0 ? "" : endTimeTextField.getText() + " 00:00:00";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				RoomStatusDAO statusDAO = new RoomStatusDAO();
				if (showAll) {
					List<RoomStatus> allStatus = statusDAO.findAll();
					for (RoomStatus roomStatus : allStatus) {
						try {
							//如果用户填了起始时间限制
							if (startTime.length() > 0) {
								Date startDate = df.parse(startTime);
								Date roomStartDate = df.parse(roomStatus.getStartTime());
								//房间的入住时间应晚于查询的起始时间
								if (roomStartDate.getTime() < startDate.getTime()) {
									continue;
								}
							}
							
							//如果用户填了结束时间限制
							if (endTime.length() > 0) {
								Date endDate = df.parse(endTime);
								Date roomEndDate = df.parse(roomStatus.getEndTime());
								//房间的结束时间应早于查询的结束时间
								if (roomEndDate.getTime() > endDate.getTime()) {
									continue;
								}
							}
							
							//获取客户信息
							UserDAO userDAO = new UserDAO();
							User user = userDAO.findById(roomStatus.getUserId());
							
							//获取房间信息
							RoomDAO roomDAO = new RoomDAO();
							Room room = (Room)roomDAO.findByNumber(roomStatus.getRoomId()).get(0);
							
							Vector<Object> row = new Vector<Object>();
							//宾客姓名
							row.add(user.getName());
							//联系电话
							row.add(user.getPhoneNum());
							//证件号码
							row.add(user.getIdCard());
							//房间号
							String roomNum = roomStatus.getRoomId();
							row.add(roomNum);
							//房间类型
							if (roomNum.startsWith("BD")) {
								row.add("标准单人间");
							} else if (roomNum.startsWith("BS")) {
								row.add("标准双人间");
							} else if (roomNum.startsWith("HD")) {
								row.add("豪华单人间");
							}
							//房间总消费
							row.add(room.getServiceCost() + room.getCost() * roomStatus.getLivingDay());
							//房间押金
							row.add(roomStatus.getDeposit());
							//房间额外消费
							row.add(room.getServiceCost());
							//入住时间
							row.add(roomStatus.getStartTime());
							//结束时间
							row.add(roomStatus.getEndTime());
							
							data.add(row);
							//更新数据源
							((CustomTableModel)table.getModel()).setData(data);
							
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					BillDAO billDAO = new BillDAO();
					List<Bill> allBills = billDAO.findAll();
					for (Bill bill : allBills) {
						
					}	
				}
				
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
