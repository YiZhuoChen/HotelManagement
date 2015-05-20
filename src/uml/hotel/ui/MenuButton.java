package uml.hotel.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;


public class MenuButton extends JToggleButton {

	private JPopupMenu menu;
	
	private boolean reverse;
	private int height;
	private String showingText;
	
	public MenuButton() {
		this("", false);
//		super();
//		
//		setText("▲");
//		setHorizontalTextPosition(SwingConstants.RIGHT);
//		addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (isSelected()) {
//					setText("��");
//					menu.show(MenuButton.this, 0, getHeight());
//				} else {
//					setText("▲");
//					menu.setVisible(false);
//				}
//			}
//		});
	}
	
	public MenuButton(final String text) {
		this(text, false);
	}
	
	public MenuButton(final String text, final boolean reverse) {
		super();
		
		this.showingText = text;
		this.reverse = reverse;
		final String arrow = reverse ? "▼ " : "▲ ";
		final String reverseArrow = reverse ? "▲ " : "▼ ";
		height = reverse ? -menu.getHeight() : getHeight();
		
		super.setText(arrow + showingText);
		setHorizontalTextPosition(SwingConstants.RIGHT);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isSelected()) {
					MenuButton.super.setText(reverseArrow + showingText);
					menu.show(MenuButton.this, 0, height);
				} else {
					MenuButton.super.setText(arrow +showingText);
					menu.setVisible(false);
				}
			}
		});
	}
	
	public void setMenu(JPopupMenu menu) {
		this.menu = menu;
		this.menu.setPopupSize(this.getWidth(), this.menu.getPreferredSize().height);
		
		height = reverse ? -menu.getHeight() : getHeight();
	}
	
	@Override
	public void setText(String text) {
		showingText = text;
		String arrow = reverse ? "▼ " : "▲ ";
		super.setText(arrow + text);
	}

}
