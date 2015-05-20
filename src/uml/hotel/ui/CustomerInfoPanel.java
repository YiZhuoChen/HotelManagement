package uml.hotel.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import uml.hotel.model.User;

public class CustomerInfoPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JComboBox comboBox;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;

	/**
	 * Create the panel.
	 */
	public CustomerInfoPanel() {
		setLayout(null);
		
		JLabel label = new JLabel("宾客类型：");
		label.setBounds(10, 13, 65, 15);
		add(label);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"普通宾客", "VIP宾客"}));
		comboBox.setBounds(74, 10, 135, 21);
		add(comboBox);
		
		JLabel label_1 = new JLabel("证件类型：");
		label_1.setBounds(10, 58, 65, 15);
		add(label_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"\u8EAB\u4EFD\u8BC1", "\u5B66\u751F\u8BC1", "\u9A7E\u9A76\u8BC1", "\u519B\u5B98\u8BC1", "\u62A4\u7167", "\u5DE5\u4F5C\u8BC1", "\u5176\u4ED6"}));
		comboBox_1.setBounds(74, 55, 135, 21);
		add(comboBox_1);
		
		JLabel label_2 = new JLabel("主客性别：");
		label_2.setBounds(10, 109, 65, 15);
		add(label_2);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"\u7537", "\u5973"}));
		comboBox_2.setBounds(74, 106, 135, 21);
		add(comboBox_2);
		
		JLabel label_3 = new JLabel("\u5BBE\u5BA2\u6765\u6E90\uFF1A");
		label_3.setBounds(10, 156, 65, 15);
		add(label_3);
		
		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"\u666E\u901A\u5BBE\u5BA2", "\u65C5\u884C\u793E", "\u53BB\u54EA\u7F51", "\u5927\u4F17\u70B9\u8BC4", "\u5176\u4ED6"}));
		comboBox_3.setBounds(74, 153, 135, 21);
		add(comboBox_3);
		
		JLabel label_4 = new JLabel("主客姓名：");
		label_4.setBounds(260, 16, 65, 15);
		add(label_4);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(335, 13, 81, 21);
		add(textField);
		
		JLabel label_5 = new JLabel("证件号码：");
		label_5.setBounds(260, 61, 65, 15);
		add(label_5);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(335, 58, 81, 21);
		add(textField_1);
		
		JLabel label_6 = new JLabel("联系电话：");
		label_6.setBounds(260, 112, 65, 15);
		add(label_6);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(335, 109, 81, 21);
		add(textField_2);
		
		JLabel label_7 = new JLabel("公司名称：");
		label_7.setBounds(260, 159, 65, 15);
		add(label_7);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(335, 156, 81, 21);
		add(textField_3);
		
		JLabel label_8 = new JLabel("地址信息：");
		label_8.setBounds(10, 194, 65, 15);
		add(label_8);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(85, 191, 331, 21);
		add(textField_4);
		
		JLabel label_9 = new JLabel("备注信息：");
		label_9.setBounds(10, 235, 65, 15);
		add(label_9);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(85, 232, 331, 21);
		add(textField_5);

	}
	
	public User getUser() {
		int type = comboBox.getSelectedIndex();
		String userName = textField.getText();
		String idNum = textField_1.getText();
		String tel = textField_2.getText();
		int gender = comboBox_2.getSelectedIndex();
		int from = comboBox_3.getSelectedIndex();
		String location = textField_4.getText();
		String company = textField_3.getText();
		String other = textField_5.getText();
		User u = new User(userName, tel, idNum, gender, type, from, location, company, other);
		return u;
	}
	
	public void setUser(User user) {
		comboBox.setSelectedIndex(user.getType());
		textField.setText(user.getName());
		textField_1.setText(user.getIdCard());
		textField_2.setText(user.getPhoneNum());
		comboBox_2.setSelectedIndex(user.getGender());
		textField_3.setText(user.getCompany());
		textField_4.setText(user.getLocation());
		textField_5.setText(user.getOther());
	}
}
