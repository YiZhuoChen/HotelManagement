package uml.hotel.model;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */

public class Order implements java.io.Serializable {

	// Fields

	private Integer id;
	private String arriveTime;
	private String orderTime;
	private Integer type;
	private Integer state;
	private String roomNum;
	private String userName;
	private String company;
	private String tel;
	private Integer userFrom;
	private Integer hasReminded;
	
	public static final int kOrderDidNotRemind = 0;
	public static final int kOrderHasReminded = 1;
	
	public static final int kOrderStateOrdering = 0;
	public static final int kOrderStateFinished = 1;
	public static final int kOrderStateCanceled = 2;

	// Constructors

	/** default constructor */
	public Order() {
	}

	/** full constructor */
	public Order(String arriveTime, String orderTime, Integer type,
			Integer state, String roomNum, String userName, String company,
			String tel, Integer userFrom, Integer hasReminded) {
		this.arriveTime = arriveTime;
		this.orderTime = orderTime;
		this.type = type;
		this.state = state;
		this.roomNum = roomNum;
		this.userName = userName;
		this.company = company;
		this.tel = tel;
		this.userFrom = userFrom;
		this.hasReminded = hasReminded;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArriveTime() {
		return this.arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getUserFrom() {
		return this.userFrom;
	}

	public void setUserFrom(Integer userFrom) {
		this.userFrom = userFrom;
	}

	public Integer getHasReminded() {
		return this.hasReminded;
	}

	public void setHasReminded(Integer hasReminded) {
		this.hasReminded = hasReminded;
	}

}