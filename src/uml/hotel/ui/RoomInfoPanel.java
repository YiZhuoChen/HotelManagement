package uml.hotel.ui;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;

public class RoomInfoPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Create the panel.
	 */
	public RoomInfoPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("预住天数：");
		lblNewLabel.setBounds(10, 49, 65, 15);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(85, 46, 81, 21);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("宾客人数：");
		lblNewLabel_1.setBounds(10, 129, 65, 15);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(85, 126, 81, 21);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label = new JLabel("实收押金：");
		label.setBounds(233, 52, 65, 15);
		add(label);
		
		textField_2 = new JTextField();
		textField_2.setBounds(308, 49, 93, 21);
		add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_1 = new JLabel("实际单价：");
		label_1.setBounds(233, 132, 65, 15);
		add(label_1);
		
		textField_3 = new JTextField();
		textField_3.setBounds(308, 129, 93, 21);
		add(textField_3);
		textField_3.setColumns(10);
		
		JLabel label_2 = new JLabel("打折比例：");
		label_2.setBounds(10, 199, 65, 15);
		add(label_2);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(85, 196, 81, 21);
		add(textField_4);
		
		JLabel label_3 = new JLabel("收据单号：");
		label_3.setBounds(233, 202, 65, 15);
		add(label_3);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(308, 199, 81, 21);
		add(textField_5);
		
	}
	
	public RoomStatus getRoomStatus() {
		try {
			int day = Integer.parseInt(textField.getText());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			Calendar calender = Calendar.getInstance();
			calender.add(calender.DAY_OF_MONTH, day);
			Date toDate = calender.getTime();
			
			String nowString = df.format(now);
			String toString = df.format(toDate);
			
			int deposit = Integer.parseInt(textField_2.getText());
			
			RoomStatus s = new RoomStatus(0, nowString, toString, "", deposit, nowString, 0, 0);
			return s;
			
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		textField_2.setText("" + roomStatus.getDeposit());
		
		textField.setText("" + roomStatus.getLivingDay());
		
	}
}
