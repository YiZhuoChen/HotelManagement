package uml.hotel.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import uml.hotel.utils.Utils;

public class BusinessSuiteJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public BusinessSuiteJPanel() {
        setLayout(null);
		
		JButton btnNewButton = new JButton("SW0001");
		btnNewButton.setBounds(10, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton, "pic/room/prov.gif");
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("SW0002");
		btnNewButton_1.setBounds(103, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton_1, "pic/room/prov.gif");
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("SW0003");
		btnNewButton_2.setBounds(196, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton_2, "pic/room/prov.gif");
		add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("SW0004");
		btnNewButton_3.setBounds(289, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton_3, "pic/room/prov.gif");
		add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("SW0005");
		btnNewButton_4.setBounds(382, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton_4, "pic/room/prov.gif");
		add(btnNewButton_4);

	}

}
