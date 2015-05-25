package uml.hotel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import sun.tools.tree.ThisExpression;
import uml.hotel.dao.OrderDAO;
import uml.hotel.dao.RoomDAO;
import uml.hotel.dao.RoomStatusDAO;
import uml.hotel.model.Order;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.notification.NotificationCenter;

public class RoomStateTipTimer extends TimerTask {
	
	private RoomDAO roomDAO;
	
	public static void main(String[] args) {
		new Timer().schedule(new RoomStateTipTimer(), new Date(), 1000);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("定时器工作。。。");
		
		roomDAO = new RoomDAO();
		
		checkUsedRooms();
		checkReservedRooms();
		updateReservationInfo();
	}
	
	public void checkUsedRooms() {
		List<Room> usedRooms = roomDAO.findByStatus(Room.kRoomStatusUsed);
		RoomStatusDAO statusDAO = new RoomStatusDAO();
		
		for (Room room : usedRooms) {
			List<RoomStatus> statusList = statusDAO.findByRoomId(room.getNumber());
			RoomStatus status = statusList.get(statusList.size() - 1);
			if (status.getHasReminded() == RoomStatus.kStatusHasReminded) {
				continue;
			}
			String endTime = status.getEndTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date endDate = df.parse(endTime);
				Date now = new Date();
				
				double dis = endDate.getTime() - now.getTime();
				if (dis < 0) {
					continue;
				}
				dis = dis / (3600 * 24 * 1000.0);
//				System.out.println("enddate = " + endDate + "\nnow = " + now + "\nday = " + dis);
				int day = (int) (dis + 0.5);
//				System.out.println("enddate = " + endDate + "\nnow = " + now + "\nday = " + day);
				if (day <= 1) {
					JOptionPane.showMessageDialog(null, new String("客房" + room.getNumber() + "还有1天就该结账了"));
					status.setHasReminded(RoomStatus.kStatusHasReminded);
					statusDAO.attachDirty(status);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	//for
	}
	
	public void checkReservedRooms() {
		List<Room> reservedRooms = roomDAO.findByStatus(Room.kRoomStatusReserved);
		OrderDAO orderDAO = new OrderDAO();
		
		for (Room room : reservedRooms) {
			List<Order> orders = orderDAO.findByRoomNum(room.getNumber());
			Order order = orders.get(orders.size() - 1);
			if (order.getHasReminded() == Order.kOrderHasReminded) {
				continue;
			}
			
			String arriveTime = order.getArriveTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date endDate = df.parse(arriveTime);
				Date now = new Date();
				
				double dis = endDate.getTime() - now.getTime();
				if (dis < 0) {
					continue;
				}
				dis = dis / (3600 * 24 * 1000.0);
//				System.out.println("enddate = " + endDate + "\nnow = " + now + "\nday = " + dis);
				int day = (int) (dis + 0.5);
//				System.out.println("enddate = " + endDate + "\nnow = " + now + "\nday = " + day);
				if (day <= 1) {
					JOptionPane.showMessageDialog(null, new String("预定房间" + room.getNumber() + "的客人还有1天就要到了"));
					order.setHasReminded(Order.kOrderHasReminded);
					orderDAO.attachDirty(order);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateReservationInfo() {
		OrderDAO orderDAO = new OrderDAO();
		List<Order> orders = orderDAO.findByState(Order.kOrderStateOrdering);
		for (Order order : orders) {
			String arriveTime = order.getArriveTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date arriveDate = df.parse(arriveTime);
				Date now = new Date();
				if (arriveDate.before(now)) {
					order.setState(Order.kOrderStateCanceled);
					orderDAO.attachDirty(order);
					
					//发通知：预定状态改变
					NotificationCenter.postNotification(NotificationCenter.kReservationStateDidChangeNotification, null);
					JOptionPane.showMessageDialog(null, "预定房间" + order.getRoomNum() + "已过期，自动设为取消状态");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
