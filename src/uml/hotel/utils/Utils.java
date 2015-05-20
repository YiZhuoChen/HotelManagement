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
	 * ͨ���ļ�������һ��ͼƬ����ͼƬ������������
	 * @param c			�������
	 * @param fileName	ͼƬ�ļ���
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
	 * ͨ���ļ�������һ��ͼƬ����ͼƬ������������
	 * @param c			�������
	 * @param fileName	ͼƬ�ļ���
	 * @param delta     ƫ�ƾ���
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
	 * Ϊ��ť����ͼƬ������
	 * @param btn
	 * @param icon
	 */
	public static void createToolBarItem(JButton btn, Icon icon) {
		btn.setIcon(icon);
//		btn.setText(text);
//		btn.setFont(new Font("����", Font.BOLD, 11));
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
	/**
	 * ������������������ȸ����ļ�������ͼƬIcon��Ȼ��Ϊ��ť����ͼƬ
	 * @param btn
	 * @param fileName
	 */
	public static void createToolBarItem(JButton btn, String fileName) {
		createToolBarItem(btn, getProperIcon(btn, fileName));
		btn.setContentAreaFilled(false);
		btn.setBorder(new EmptyBorder(3, 3, 3, 3));
	}
	
	/**
	 * ���ݷ���״̬������ť
	 * @param btn
	 */
	public static void createRoomButton(JButton btn) {
		updateButtonIconWithStatus(btn, btn.getText());
	}
	
	/**
	 * ���ݷ����״̬���°�ť��ͼ��
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
