package uml.hotel.utils;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import uml.hotel.dao.RoomDAO;
import uml.hotel.model.Room;

public class Utils {
	/**
	 * 通过文件名返回一个图片，该图片自适配给定组件
	 * @param c			给定组件
	 * @param fileName	图片文件名
	 * @return
	 */
	public static ImageIcon getProperIcon(Component c, String fileName) {
		URL image = Utils.class.getClassLoader().getResource(fileName);
		ImageIcon icon = new ImageIcon(image);
		
		//int width = c.getWidth();// + delta.x;
		//int height= c.getHeight();// + delta.y;
		
		Image scaledImage = icon.getImage();//.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		icon.setImage(scaledImage);
		return icon;
	}
	
	/**
	 * 通过文件名返回一个图片，该图片自适配给定组件
	 * @param c			给定组件
	 * @param fileName	图片文件名
	 * @param delta     偏移距离
	 * @return
	 */
	public static ImageIcon getProperIcon(Component c, String fileName, Point delta) {
		URL image = Utils.class.getClassLoader().getResource(fileName);
		ImageIcon icon = new ImageIcon(image);
		
		int width = c.getWidth() + delta.x;
		int height= c.getHeight() + delta.y;
		
		Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		icon.setImage(scaledImage);
		return icon;
	}
	
	/**
	 * 为按钮设置图片和文字
	 * @param btn
	 * @param icon
	 */
	public static void createToolBarItem(JButton btn, Icon icon) {
		btn.setIcon(icon);
//		btn.setText(text);
//		btn.setFont(new Font("宋体", Font.BOLD, 11));
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
	/**
	 * 结合上面两个方法：先根据文件名创建图片Icon，然后为按钮设置图片
	 * @param btn
	 * @param fileName
	 */
	public static void createToolBarItem(JButton btn, String fileName) {
		createToolBarItem(btn, getProperIcon(btn, fileName));
		btn.setContentAreaFilled(false);
		btn.setBorder(new EmptyBorder(3, 3, 3, 3));
	}
	
	/**
	 * 根据房间状态创建按钮
	 * @param btn
	 */
	public static void createRoomButton(JButton btn) {
		updateButtonIconWithStatus(btn, btn.getText());
	}
	
	/**
	 * 根据房间的状态更新按钮的图标
	 * @param btn
	 * @param roomNum
	 */
	public static void updateButtonIconWithStatus(JButton btn, String roomNum) {
		RoomDAO roomDAO = new RoomDAO();
		Room room = (Room)roomDAO.findByNumber(roomNum).get(0);
		int status = room.getStatus();
		switch (status) {
		case Room.kRoomStatusAvaliable:
			createToolBarItem(btn, "pic/room/prov.gif");
			break;
		case Room.kRoomStatusClean:
			createToolBarItem(btn, "pic/room/clean.gif");
			break;
		case Room.kRoomStatusClock:
			createToolBarItem(btn, "pic/room/clock.gif");
			break;
		case Room.kRoomStatusReserved:
			createToolBarItem(btn, "pic/room/rese.gif");
			break;
		case Room.kRoomStatusUnavaliable:
			createToolBarItem(btn, "pic/room/stop.gif");
			break;
		case Room.kRoomStatusUsed:
			createToolBarItem(btn, "pic/room/pree.gif");
			break;
		default:
			break;
		}
	}
}
