package uml.hotel.ui;

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import uml.hotel.utils.CustomTableModel;
import uml.hotel.utils.HeaderRenderer;

public class BillQueryFrame extends JFrame {

	private JPanel contentPane;
	
	private JTable table;
	private Vector<Vector<Object>> data = new Vector<Vector<Object>>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillQueryFrame frame = new BillQueryFrame();
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
	public BillQueryFrame() {
		setTitle("营业查询");
		setBounds(100, 100, 850, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane searchTabbedPane = new JTabbedPane();
		searchTabbedPane.setBounds(10, 0, 814, 100);
		contentPane.add(searchTabbedPane);
		
		
		String[] columnNames = {"宾客姓名", "联系电话", "证件号码", "房间号", "房间类型", "房间总消费", "房间押金", "房间额外消费", "入住时间", "结束时间"};
		CustomTableModel model = new CustomTableModel();
		model.setColumnNames(columnNames);
		table = new JTable(model);
		table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
		//不使用自动放缩，当表格过长时横向滚动
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int[] columnWidth = {80, 80, 80, 80, 80, 80, 80, 100, 180, 180};
		for (int i = 0; i < columnWidth.length; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 96, 814, 161);
		contentPane.add(scrollPane);
		
		
		searchTabbedPane.add("按日期查询", new SearchByDatePanel(data, table));
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 267, 814, 84);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.add("账单信息", panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("账单总数：");
		label.setBounds(10, 10, 65, 15);
		panel_1.add(label);
		
		JLabel billCountLabel = new JLabel("1");
		billCountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		billCountLabel.setBounds(80, 10, 46, 15);
		panel_1.add(billCountLabel);
		
		JLabel label_2 = new JLabel("账单消费总额：");
		label_2.setBounds(136, 10, 95, 15);
		panel_1.add(label_2);
		
		JLabel billCostLabel = new JLabel("1");
		billCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		billCostLabel.setBounds(230, 10, 46, 15);
		panel_1.add(billCostLabel);
		
		JLabel label_1 = new JLabel("\u989D\u5916\u6D88\u8D39\u603B\u989D\uFF1A");
		label_1.setBounds(286, 10, 95, 15);
		panel_1.add(label_1);
		
		JLabel serviceCostLabel = new JLabel("1");
		serviceCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		serviceCostLabel.setBounds(380, 10, 46, 15);
		panel_1.add(serviceCostLabel);
		
		JLabel roomCostLabel = new JLabel("1");
		roomCostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		roomCostLabel.setBounds(530, 10, 46, 15);
		panel_1.add(roomCostLabel);
		
		JLabel label_4 = new JLabel("\u623F\u95F4\u6D88\u8D39\u603B\u989D\uFF1A");
		label_4.setBounds(436, 10, 95, 15);
		panel_1.add(label_4);
		
		JLabel rateLabel = new JLabel("1");
		rateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		rateLabel.setBounds(656, 10, 46, 15);
		panel_1.add(rateLabel);
		
		JLabel label_5 = new JLabel("\u5165\u4F4F\u7387\uFF1A");
		label_5.setBounds(586, 10, 65, 15);
		panel_1.add(label_5);
		
	}
	
}
