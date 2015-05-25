package uml.hotel.model;

import java.util.List;

import uml.hotel.dao.ServerDAO;
import uml.hotel.dao.ServerItemDAO;

/**
 * Room entity. @author MyEclipse Persistence Tools
 */

public class Room {

	// Fields

	private Integer id;
	private Integer status;
	private String number;
	private Integer type;
	private String phoneNum;
	private Integer cost;
	private Integer peopleCount;
	private Integer location;

	/**����ס*/
	public static final int kRoomStatusUsed = 0;
	/**����ס*/
	public static final int kRoomStatusAvaliable = 1;
	/**��Ԥ��*/
	public static final int kRoomStatusReserved = 2;
	/**�ӵ�*/
	public static final int kRoomStatusClock = 3;
	/**����*/
	public static final int kRoomStatusClean = 4;
	/**ͣ��*/
	public static final int kRoomStatusUnavaliable = 5;
	
	
	/**��׼���˼�*/
	public static final int kRoomTypeStandardSignle = 2;
	/**��׼˫�˼�*/
	public static final int kRoomTypeStandardDouble = 3;
	/**������*/
	public static final int kRoomTypeLuxurySingle = 4;
	
	// Constructors

	/** default constructor */
	public Room() {
	}

	/** full constructor */
	public Room(Integer status, String number, Integer type, String phoneNum,
			Integer cost, Integer peopleCount) {
		this.status = status;
		this.number = number;
		this.type = type;
		this.phoneNum = phoneNum;
		this.cost = cost;
		this.peopleCount = peopleCount;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getCost() {
		return this.cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getPeopleCount() {
		return this.peopleCount;
	}

	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}
	
	public Integer getLocation() {
		return this.location;
	}
	
	public void setLocation(Integer location) {
		this.location = location;
	}
	
	/**
	 * ��ȡ�÷���Ķ��������ܶ�
	 * @return
	 */
	public float getServiceCost() {
		float serviceCost = 0;
		ServerDAO serverDAO = new ServerDAO();
		List list = serverDAO.findByRoomId(getId());
		//���Ϸ������
		for (Object object : list) {
			Server server = (Server)object;
			int itemID = server.getItemId();
			ServerItemDAO itemDAO = new ServerItemDAO();
			ServerItem item = itemDAO.findById(itemID);
			//���۳�������
			serviceCost += item.getCost() * server.getCount();
		}
		return serviceCost;
	}

}