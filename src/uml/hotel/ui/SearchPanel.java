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
			//����û�������ʼʱ������
			if (startTime.length() > 0) {
				Date startDate = df.parse(startTime);
				Date roomStartDate = df.parse(status.getStartTime());
				//�������סʱ��Ӧ���ڲ�ѯ����ʼʱ��
				if (roomStartDate.getTime() < startDate.getTime()) {
					return;
				}
			}
			
			//����û����˽���ʱ������
			if (endTime.length() > 0) {
				Date endDate = df.parse(endTime);
				Date roomEndDate = df.parse(status.getEndTime());
				//����Ľ���ʱ��Ӧ���ڲ�ѯ�Ľ���ʱ��
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
		//��ȡ������Ϣ
		RoomDAO roomDAO = new RoomDAO();
		Room room = (Room)roomDAO.findByNumber(status.getRoomId()).get(0);
		
		Vector<Object> row = new Vector<Object>();
		//��������
		row.add(user.getName());
		//��ϵ�绰
		row.add(user.getPhoneNum());
		//֤������
		row.add(user.getIdCard());
		//�����
		String roomNum = status.getRoomId();
		row.add(roomNum);
		//��������
		if (roomNum.startsWith("BD")) {
			row.add("��׼���˼�");
		} else if (roomNum.startsWith("BS")) {
			row.add("��׼˫�˼�");
		} else if (roomNum.startsWith("HD")) {
			row.add("�������˼�");
		}
		//����������
		row.add(room.getServiceCost() + room.getCost() * status.getLivingDay());
		//����Ѻ��
		row.add(status.getDeposit());
		//�����������
		row.add(room.getServiceCost());
		//��סʱ��
		row.add(status.getStartTime());
		//����ʱ��
		row.add(status.getEndTime());
		
		data.add(row);
	}
}
