package uml.hotel.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;


import sun.nio.cs.StandardCharsets;
import uml.hotel.dao.CostDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.ServerDAO;
import uml.hotel.dao.ServerItemDAO;
import uml.hotel.dao.UserDAO;
import uml.hotel.model.Cost;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.Server;
import uml.hotel.model.ServerItem;
import uml.hotel.model.User;
import uml.hotel.utils.CustomTableModel;
import uml.hotel.utils.HeaderRenderer;

public class ConsumeFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable rightTable;
	private JTable leftTable;
	
	private static int consumeNumber = 1;	//��¼��ǰ�������
	
	//labels
	private JLabel consumeNumberLabel;	//�������
	private JLabel roomNumberLabel;		//������
	private JLabel customerNameLabel;	//��������

	private Vector<Vector<Object>> leftTableData;
	private Vector<Vector<Object>> rightTableData;
	
	private RoomStatus status;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsumeFrame frame = new ConsumeFrame();
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
	public ConsumeFrame() {
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "�����������", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 108, 564, 116);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_5 = new JLabel("��������");
		label_5.setBounds(10, 41, 54, 15);
		panel.add(label_5);
		
		textField = new JTextField();
		textField.setBounds(74, 38, 104, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel label_6 = new JLabel("���ѵ���");
		label_6.setBounds(193, 41, 54, 15);
		panel.add(label_6);
		
		textField_1 = new JTextField();
		textField_1.setBounds(257, 38, 104, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_7 = new JLabel("��������");
		label_7.setBounds(386, 41, 54, 15);
		panel.add(label_7);
		
		textField_2 = new JTextField();
		textField_2.setBounds(450, 38, 104, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton button = new JButton("���������Ŀ");
		button.setBounds(207, 83, 128, 23);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//�������
				if (textField.getText() == null || textField_1.getText() == null || textField_2.getText() == null) {
					return;
				}
				
				String content = textField.getText();
				try {
					float cost = Float.parseFloat(textField_1.getText());
					int count = Integer.parseInt(textField_2.getText());
					
					//��ӵ��ұ��б�ʾ�˿�ѡ����Щ������
					Vector<Object> row = new Vector<Object>();
					row.add(content);
					row.add(cost);
					row.add(count);
					rightTableData.add(row);
					//��������Դ
					((CustomTableModel)(rightTable.getModel())).setData(rightTableData);
					
					//��ӵ�����б�ʾ�Ժ���Щ������ɸ���
					ServerItem item = new ServerItem(content, cost);
					ServerItemDAO dao = new ServerItemDAO();
					dao.save(item);
					
					updateLeftTableModel();
					//��������Դ
					((CustomTableModel)(leftTable.getModel())).setData(leftTableData);
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
				
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "����������Ϣ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 10, 564, 71);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("�������");
		label.setBounds(46, 30, 54, 15);
		panel_1.add(label);
		
		consumeNumberLabel = new JLabel("20140525003");
		consumeNumberLabel.setBounds(115, 30, 108, 15);
		panel_1.add(consumeNumberLabel);
		
		JLabel label_2 = new JLabel("������");
		label_2.setBounds(233, 30, 54, 15);
		panel_1.add(label_2);
		
		roomNumberLabel = new JLabel("BD0001");
		roomNumberLabel.setBounds(297, 30, 73, 15);
		panel_1.add(roomNumberLabel);
		
		JLabel label_3 = new JLabel("��������");
		label_3.setBounds(380, 30, 54, 15);
		panel_1.add(label_3);
		
		customerNameLabel = new JLabel("�");
		customerNameLabel.setBounds(444, 30, 110, 15);
		panel_1.add(customerNameLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "���Ĭ������", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 234, 564, 277);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		String[] leftColumnNames = {"��������", "���ѵ���"};
		String[] rightColumnNames = {"��������", "����", "����"};
		
		//��ʼ������б������
		leftTableData = new Vector<Vector<Object>>();
		updateLeftTableModel();
		
		CustomTableModel leftTableModel = new CustomTableModel();
		leftTableModel.setColumnNames(leftColumnNames);
		leftTableModel.setData(leftTableData);
		leftTable = new JTable(leftTableModel);
		//��ѡ
		leftTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		leftTable.getTableHeader().setDefaultRenderer(new HeaderRenderer(leftTable));
		
		CustomTableModel rightTableModel = new CustomTableModel();
		rightTableModel.setColumnNames(rightColumnNames);
		rightTable = new JTable(rightTableModel);
		rightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rightTable.getTableHeader().setDefaultRenderer(new HeaderRenderer(rightTable));
		//��ʼ���Ҳ��б�����
		rightTableData = new Vector<Vector<Object>>();
		
		JScrollPane scrollPane = new JScrollPane(leftTable);
		scrollPane.setBounds(20, 20, 205, 237);
		panel_2.add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane(rightTable);
		scrollPane_1.setBounds(330, 20, 215, 237);
		panel_2.add(scrollPane_1);
		
		JButton button_1 = new JButton("���");
		button_1.setBounds(231, 82, 93, 23);
		panel_2.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = leftTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "������ѡ��һ������Ӽ�¼");
					return;
				}
				
				String name = (String)leftTable.getValueAt(selectedRow, 0);
				float price = (Float)leftTable.getValueAt(selectedRow, 1);
				boolean hasSame = false;
				//������ظ�
				for (int i = 0; i < rightTableData.size(); i++) {
					Vector<Object> rightRow = rightTableData.get(i);
					if (name.equals(rightRow.get(0))) {
						int num = (Integer)rightTable.getModel().getValueAt(i, 2);
						rightTable.getModel().setValueAt(num+1, i, 2);
						hasSame = true;
						break;
					}
				}
				
				if (!hasSame) {
					Vector<Object> newRow = new Vector<Object>();
					newRow.add(name);
					newRow.add(price);
					newRow.add(1);
					rightTableData.add(newRow);
					//��������Դ
					((CustomTableModel)(rightTable.getModel())).setData(rightTableData);
				}
			}
		});
		
		JButton button_2 = new JButton("ɾ��");
		button_2.setBounds(231, 186, 93, 23);
		panel_2.add(button_2);
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "����ұ�ѡ��һ����ɾ����¼");
					return;
				}
				
				
				rightTableData.remove(selectedRow);
				((CustomTableModel)rightTable.getModel()).setData(rightTableData);
			}
		});
		
		
		JButton okButton = new JButton("ȷ�����");
		okButton.setBounds(160, 530, 93, 23);
		contentPane.add(okButton);
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Room room = (Room)new RoomDAO().findByNumber(status.getRoomId()).get(0);
				ServerDAO serverDAO = new ServerDAO();
				List<Server> serverList = serverDAO.findByRoomId(room.getId());
				//�������֮ǰ�����Ѽ�¼
				if (serverList != null && serverList.size() > 0) {
					for (Server server : serverList) {
						serverDAO.delete(server);
					}
				}
				
				//��ӵ�ǰ�����Ѽ�¼
				ServerItemDAO itemDAO = new ServerItemDAO();
				
				for (Vector<Object> row : rightTableData) {
					String content = (String)row.get(0);
					int count = (Integer)row.get(2);
					
					ServerItem item = (ServerItem)itemDAO.findByContent(content).get(0);
					
					Server server = new Server(item.getId(), count, room.getId());
					serverDAO.save(server);
				}
				
				//���ؽ���
				setVisible(false);
			}
		});
		
		JButton cancelButton = new JButton("ȡ��");
		cancelButton.setBounds(340, 530, 93, 23);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
	}
	
	public ConsumeFrame(RoomStatus status) {
		this();
		
		this.status = status;
		
		//���ѱ��
		DecimalFormat df = new DecimalFormat("000");
		String suffix = df.format(consumeNumber);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(now);
		consumeNumberLabel.setText(dateString + suffix);
		
		//������
		roomNumberLabel.setText(status.getRoomId());
		
		//��������
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findById(status.getUserId());
		customerNameLabel.setText(user.getName());
		
		//��ѯ��ǰ�����嵥
		RoomDAO roomDAO = new RoomDAO();
		Room room = (Room)roomDAO.findByNumber(status.getRoomId()).get(0);
		
		ServerDAO serverDAO = new ServerDAO();
		List<Server> list = serverDAO.findByRoomId(room.getId());
		//���Ϸ������
		for (Server server : list) {
			int count = server.getCount();
			
			int itemID = server.getItemId();
			ServerItemDAO itemDAO = new ServerItemDAO();
			ServerItem item = itemDAO.findById(itemID);
			
			String content = item.getContent();
			float cost = item.getCost();
			
			Vector<Object> row = new Vector<Object>();
			row.add(content);
			row.add(cost);
			row.add(count);
			rightTableData.add(row);
		}
		
		//��������Դ
		((CustomTableModel)(rightTable.getModel())).setData(rightTableData);
	}
	
	
	public void updateLeftTableModel() {
		ServerItemDAO itemDAO = new ServerItemDAO();
		List<ServerItem> items = itemDAO.findAll();
		for (ServerItem serverItem : items) {
			Vector<Object> row = new Vector<Object>();
			row.add(serverItem.getContent());
			row.add(serverItem.getCost());
			leftTableData.add(row);
		}
	
		
	}
	
}
