package uml.hotel.model;

/**
 * Admin entity. @author MyEclipse Persistence Tools
 */

public class Admin {

	// Fields

	private Integer id;
	private Integer type;
	private String username;
	private String password;
	
	public static int kAdminTypeNormal = 1;
	public static int kAdminTypeSuper = 0;

	// Constructors

	/** default constructor */
	public Admin() {
	}

	/** full constructor */
	public Admin(Integer id, Integer type, String username, String password) {
		this.id = id;
		this.type = type;
		this.username = username;
		this.password = password;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}