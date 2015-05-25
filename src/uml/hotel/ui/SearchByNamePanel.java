package uml.hotel.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import uml.hotel.dao.BillDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Bill;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;
import uml.hotel.utils.CustomTableModel;

public class SearchByNamePanel extends SearchPanel {
	
	private JTextField nameTextField;
	
	public SearchByNamePanel(final Vector<Vector<Object>> data, final JTable table) {
		
		super(data, table);
		
		JLabel nameLabel = new JLabel("客户姓名：");
		nameLabel.setBounds(90, 23, 75, 15);
		add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(195, 20, 130, 21);
		add(nameTextField);
		nameTextField.setColumns(10);
		
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
		queryButton.setBounds(690, 19, 93, 23);
		queryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				data.removeAllElements();
				((CustomTableModel)table.getModel()).setData(data);
				
				final boolean showAll = radioButton.isSelected();
				final RoomStatusDAO statusDAO = new RoomStatusDAO();
				
				final UserDAO userDAO = new UserDAO();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						List<User> nameLikedUsers;
						if (nameTextField.getText().length() == 0) {
							//用户没有输入查询条件时默认显示全部
							nameLikedUsers = userDAO.findAll();
						} else {
							//按查询条件模糊匹配
							nameLikedUsers = userDAO.findByName_Blur(nameTextField.getText().trim());	
						}
						
						if (showAll) {
							List<RoomStatus> allStatus = statusDAO.findAll();
							
							for (RoomStatus status : allStatus) {
								User user = userDAO.findById(status.getUserId());
								
								//如果不包含
								if (nameLikedUsers.contains(user) == false) {
									continue;
								}
								
								addRow(status, user);
							}
							
						} else {
							BillDAO billDAO = new BillDAO();
							List<Bill> allBills = billDAO.findAll();
							for (Bill bill : allBills) {
								User user = userDAO.findById(bill.getUserId());
								RoomStatus status = statusDAO.findById(bill.getRoomStatusId());
								
								if (nameLikedUsers.contains(user) == false) {
									continue;
								}
								
								addRow(status, user);
							}	
							
						}
						
						//更新数据源
						((CustomTableModel)table.getModel()).setData(data);
						
					}
				}).start();
				
			}
				
		});
		
		add(queryButton);
	}
}
