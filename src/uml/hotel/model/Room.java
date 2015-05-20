package uml.hotel.model;

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

	/**已入住*/
	public static final int kRoomStatusUsed = 0;
	/**可入住*/
	public static final int kRoomStatusAvaliable = 1;
	/**被预定*/
	public static final int kRoomStatusReserved = 2;
	/**钟点*/
	public static final int kRoomStatusClock = 3;
	/**清理*/
	public static final int kRoomStatusClean = 4;
	/**停用*/
	public static final int kRoomStatusUnavaliable = 5;
	
	
	/**标准单人间*/
	public static final int kRoomTypeStandardSignle = 2;
	/**标准双人间*/
	public static final int kRoomTypeStandardDouble = 3;
	/**豪华间*/
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

}