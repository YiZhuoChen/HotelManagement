package uml.hotel.model;

/**
 * Server entity. @author MyEclipse Persistence Tools
 */

public class Server implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer itemId;
	private Integer count;
	private Integer roomId;

	// Constructors

	/** default constructor */
	public Server() {
	}

	/** full constructor */
	public Server(Integer itemId, Integer count, Integer roomId) {
		this.itemId = itemId;
		this.count = count;
		this.roomId = roomId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

}