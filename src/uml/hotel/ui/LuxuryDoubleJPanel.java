package uml.hotel.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import uml.hotel.utils.Utils;

public class LuxuryDoubleJPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public LuxuryDoubleJPanel() {
        setLayout(null);
		
		JButton btnNewButton = new JButton("HS0001");
		btnNewButton.setBounds(10, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton, "pic/room/prov.gif");
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("HS0002");
		btnNewButton_1.setBounds(103, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton_1, "pic/room/prov.gif");
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("HS0003");
		btnNewButton_2.setBounds(196, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton_2, "pic/room/prov.gif");
		add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("HS0004");
		btnNewButton_3.setBounds(289, 25, 83, 68);
		Utils.createToolBarItem(btnNewButton_3, "pic/room/prov.gif");
		add(btnNewButton_3);
		

	}

}
