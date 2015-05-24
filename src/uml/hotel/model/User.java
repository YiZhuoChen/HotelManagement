package uml.hotel.model;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String phoneNum;
	private String idCard;
	private Integer gender;
	private Integer type;
	private Integer userFrom;
	private String location;
	private String company;
	private String other;
	
	/**普通宾客*/
	public static final int kUserTypeNormal = 0;
	/**VIP宾客*/
	public static final int kUserTypeVIP = 1;
	
	/**普通由来宾客*/
	public static final int kUserFromNormal = 0;
	/**网站*/
	public static final int kUserFromWebsite = 1;
	/**旅行社*/
	public static final int kUserFromTravelAgency = 2;
	/**其它*/
	public static final int kUserFromOther = 3;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String name, String phoneNum, String idCard, Integer gender,
			Integer type, Integer userFrom, String location, String company,
			String other) {
		this.name = name;
		this.phoneNum = phoneNum;
		this.idCard = idCard;
		this.gender = gender;
		this.type = type;
		this.userFrom = userFrom;
		this.location = location;
		this.company = company;
		this.other = other;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserFrom() {
		return this.userFrom;
	}

	public void setUserFrom(Integer userFrom) {
		this.userFrom = userFrom;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String toString() {
		return this.name + this.company + this.idCard + this.location + this.phoneNum;
		
	}

}