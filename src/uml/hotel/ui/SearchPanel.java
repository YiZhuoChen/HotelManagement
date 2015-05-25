package uml.hotel.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;

import uml.hotel.dao.RoomDAO;
import uml.hotel.model.Room;
import uml.hotel.model.RoomStatus;
import uml.hotel.model.User;

public class SearchPanel extends JPanel {
	protected Vector<Vector<Object>> data;
	
	public SearchPanel(final Vector<Vector<Object>> data, final JTable table) {
		this.data = data;
		setLayout(null);
	}
	
	public void addRowWithTime(String startTime, String endTime, RoomStatus status, User user, Room room) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//如果用户填了起始时间限制
			if (startTime.length() > 0) {
				Date startDate = df.parse(startTime);
				Date roomStartDate = df.parse(status.getStartTime());
				//房间的入住时间应晚于查询的起始时间
				if (roomStartDate.getTime() < startDate.getTime()) {
					return;
				}
			}
			
			//如果用户填了结束时间限制
			if (endTime.length() > 0) {
				Date endDate = df.parse(endTime);
				Date roomEndDate = df.parse(status.getEndTime());
				//房间的结束时间应早于查询的结束时间
				if (roomEndDate.getTime() > endDate.getTime()) {
					return;
				}
			}
			
			addRow(status, user);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void addRow(RoomStatus status, User user) {
		//获取房间信息
		RoomDAO roomDAO = new RoomDAO();
		Room room = (Room)roomDAO.findByNumber(status.getRoomId()).get(0);
		
		Vector<Object> row = new Vector<Object>();
		//宾客姓名
		row.add(user.getName());
		//联系电话
		row.add(user.getPhoneNum());
		//证件号码
		row.add(user.getIdCard());
		//房间号
		String roomNum = status.getRoomId();
		row.add(roomNum);
		//房间类型
		if (roomNum.startsWith("BD")) {
			row.add("标准单人间");
		} else if (roomNum.startsWith("BS")) {
			row.add("标准双人间");
		} else if (roomNum.startsWith("HD")) {
			row.add("豪华单人间");
		}
		//房间总消费
		row.add(room.getServiceCost() + room.getCost() * status.getLivingDay());
		//房间押金
		row.add(status.getDeposit());
		//房间额外消费
		row.add(room.getServiceCost());
		//入住时间
		row.add(status.getStartTime());
		//结束时间
		row.add(status.getEndTime());
		
		data.add(row);
	}
}
