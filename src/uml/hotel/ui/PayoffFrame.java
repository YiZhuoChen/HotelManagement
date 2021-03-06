package uml.hotel.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import sun.tools.jstat.Literal;
import uml.hotel.dao.BillDAO;
import uml.hotel.dao.CostDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.ServerDAO;
import uml.hotel.dao.ServerItemDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Bill;
import uml.hotel.model.Cost;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.Server;
import uml.hotel.model.ServerItem;
import uml.hotel.model.User;
import uml.hotel.notification.NotificationCenter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.List;

public class PayoffFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	private RoomStatus status;
	private Room room;
	private User user;
	private Cost cost;
	
	private float preferential;
	
	//labels
	private JLabel payoffNumLabel;	//结账单号
	private JLabel roomLabel;		//房间号
	private JLabel userNameLabel;	//客户名称
	private JLabel companyLabel;	//公司名称
	private JLabel didCostLabel;	//消费金额
	private JLabel shouldPayLabel;	//应收金额
	private JLabel depositLabel;	//已收押金
	private JLabel preferentiaLabel;//优惠价格
	private JLabel restCostLabel;	//还需支付
	private JLabel chargeLabel;		//找零

	/**
	 * Create the frame.
	 */
	public PayoffFrame(final RoomStatus status) {
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 514, 90);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setBorder(new TitledBorder("宾客信息"));
		
		JLabel label = new JLabel("结账单号：");
		label.setBounds(10, 25, 71, 15);
		panel.add(label);
		
		JLabel label_1 = new JLabel("结账房间：");
		label_1.setBounds(10, 53, 71, 15);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("宾客姓名：");
		label_2.setBounds(148, 25, 71, 15);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("消费金额：");
		label_3.setBounds(148, 53, 71, 15);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("公司名称：");
		label_4.setBounds(300, 25, 71, 15);
		panel.add(label_4);
		
		companyLabel = new JLabel("哈尔滨工业大学（威海）");
		companyLabel.setForeground(Color.BLUE);
		companyLabel.setBounds(370, 25, 146, 15);
		panel.add(companyLabel);
		
		userNameLabel = new JLabel("杨闯");
		userNameLabel.setForeground(Color.BLUE);
		userNameLabel.setBounds(218, 25, 80, 15);
		panel.add(userNameLabel);
		
		didCostLabel = new JLabel("999999.0");
		didCostLabel.setForeground(Color.BLUE);
		didCostLabel.setBounds(218, 53, 54, 15);
		panel.add(didCostLabel);
		
		payoffNumLabel = new JLabel("JZ001");
		payoffNumLabel.setForeground(Color.BLUE);
		payoffNumLabel.setBounds(80, 25, 54, 15);
		panel.add(payoffNumLabel);
		
		roomLabel = new JLabel("BZ0001");
		roomLabel.setForeground(Color.BLUE);
		roomLabel.setBounds(80, 53, 54, 15);
		panel.add(roomLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 83, 514, 368);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_5 = new JLabel("应收金额：");
		label_5.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_5.setBounds(31, 41, 80, 33);
		panel_1.add(label_5);
		
		shouldPayLabel = new JLabel("999999.00");
		shouldPayLabel.setForeground(Color.BLUE);
		shouldPayLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		shouldPayLabel.setBounds(121, 42, 124, 33);
		panel_1.add(shouldPayLabel);
		
		JLabel label_7 = new JLabel("已收押金：");
		label_7.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_7.setBounds(272, 41, 80, 33);
		panel_1.add(label_7);
		
		depositLabel = new JLabel("100.00");
		depositLabel.setForeground(Color.BLUE);
		depositLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		depositLabel.setBounds(362, 42, 124, 33);
		panel_1.add(depositLabel);
		
		JLabel label_9 = new JLabel("优惠金额：");
		label_9.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_9.setBounds(31, 108, 80, 33);
		panel_1.add(label_9);
		
		preferentiaLabel = new JLabel("0.00");
		preferentiaLabel.setForeground(Color.BLUE);
		preferentiaLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		preferentiaLabel.setBounds(121, 109, 124, 33);
		panel_1.add(preferentiaLabel);
		
		JLabel label_11 = new JLabel("还需支付：");
		label_11.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_11.setBounds(272, 108, 80, 33);
		panel_1.add(label_11);
		
		restCostLabel = new JLabel("999899.00");
		restCostLabel.setForeground(Color.BLUE);
		restCostLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		restCostLabel.setBounds(362, 109, 124, 33);
		panel_1.add(restCostLabel);
		
		JLabel label_13 = new JLabel("宾客支付：");
		label_13.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_13.setBounds(31, 174, 80, 33);
		panel_1.add(label_13);
		
		textField = new JTextField();
		textField.setText("0.00");
		textField.setBounds(121, 174, 124, 33);
		panel_1.add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				try {
					Float restCost = Float.parseFloat(restCostLabel.getText());
					Float pay = Float.parseFloat(textField.getText());
					float charge = pay - restCost;
					chargeLabel.setText("" + charge);
				} catch (NumberFormatException e) {
					
				}
				
			}
			
		});
		
		JLabel label_14 = new JLabel("找零：");
		label_14.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_14.setBounds(272, 174, 80, 33);
		panel_1.add(label_14);
		
		chargeLabel = new JLabel("0");
		chargeLabel.setForeground(Color.BLUE);
		chargeLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		chargeLabel.setBounds(362, 175, 124, 33);
		panel_1.add(chargeLabel);
		
		JLabel label_16 = new JLabel("特殊：");
		label_16.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_16.setBounds(31, 236, 80, 33);
		panel_1.add(label_16);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"无", "挂账", "免费", "退单"}));
		comboBox.setBounds(121, 236, 124, 33);
		panel_1.add(comboBox);
		final String origin = restCostLabel.getText();
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (((JComboBox)arg0.getSource()).getSelectedIndex() != 0) {
					restCostLabel.setText("0.00");
				} else {
					restCostLabel.setText(origin);
				}
			}
		});
		
		JButton button = new JButton("结账");
		button.setForeground(Color.RED);
		button.setBounds(350, 256, 93, 23);
		panel_1.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 将账单信息保存到数据库中
				try {
					Float payOff = new Float(textField.getText());
					Bill bill = new Bill(user.getId(), status.getId(), cost.getId(), preferential, payOff);
					Integer special = comboBox.getSelectedIndex();
					bill.setSpecial(special);
					String other = (String)textField_1.getText();
					if (other != null) {
						bill.setOther(other);
					}
					
					BillDAO billDAO = new BillDAO();
					billDAO.save(bill);
					
					RoomDAO roomDAO = new RoomDAO();
					room.setStatus(Room.kRoomStatusClean);
					roomDAO.attachDirty(room);
					
					//额外消费
					ServerDAO serverDAO = new ServerDAO();
					List<Server> servers = serverDAO.findByRoomId(room.getId());
					for (Server server : servers) {
						if (server.getFinished() == Server.kServerStateNotFinish) {
							server.setFinished(Server.kServerStateFinished);
							serverDAO.attachDirty(server);
						}
					}
					
					NotificationCenter.postNotification(NotificationCenter.kRoomStatusDidChangeNotification, room.getNumber());
					
					setVisible(false);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton button_1 = new JButton("取消");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		
		button_1.setBounds(350, 305, 93, 23);
		panel_1.add(button_1);
		
		JLabel label_17 = new JLabel("备注：");
		label_17.setFont(new Font("宋体", Font.PLAIN, 16));
		label_17.setBounds(31, 309, 54, 15);
		panel_1.add(label_17);
		
		textField_1 = new JTextField();
		textField_1.setBounds(95, 306, 215, 21);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		this.status = status;
		updatePayoffStatus();
		
		//如果是vip，增加标识信息
		if (user.getType() == User.kUserTypeVIP) {
			JLabel vipLabel = new JLabel("该用户是vip用户，优惠1％");
			vipLabel.setForeground(Color.red);
			vipLabel.setFont(new Font("宋体", Font.PLAIN, 19));
			vipLabel.setBounds(270, 50, 250, 20);
			panel.add(vipLabel);
		}
	}
	
	private void updatePayoffStatus() {
		UserDAO userDAO = new UserDAO();
		user = userDAO.findById(status.getUserId());
		
		RoomDAO roomDAO = new RoomDAO();
		List roomList = roomDAO.findByNumber(status.getRoomId());
		room = (Room)roomList.get(roomList.size() - 1);
		
		CostDAO costDAO = new CostDAO();
		List costList = costDAO.findByRoomId(room.getId());
		cost = (Cost)costList.get(costList.size() - 1);
		
		//设置单号
		DecimalFormat df = new DecimalFormat("000");
		payoffNumLabel.setText(df.format(cost.getId()));
		//设置房间号
		roomLabel.setText(room.getNumber());
		//设置客户名称
		userNameLabel.setText(user.getName());
		//设置公司名称
		companyLabel.setText(user.getCompany());
		//应收金额
		float roomCost = cost.getCost();
		//获取额外消费总额 
		float serviceCost = room.getServiceCost();
		
		float sum = roomCost + serviceCost;
		shouldPayLabel.setText("" + sum);
		//额外消费
		didCostLabel.setText("" + serviceCost);
		//已收押金
		depositLabel.setText("" + status.getDeposit());
		//优惠价格
		boolean isVIP = (user.getType() == 1);
		preferential = 0;
		if (isVIP) {
			preferential = sum * 0.01f;
		}
		int preferential_i = (int)preferential;
		preferentiaLabel.setText("" + preferential_i);
		//还需支付
		float rest = sum - preferential_i - status.getDeposit();
		restCostLabel.setText("" + rest);
	}
}
