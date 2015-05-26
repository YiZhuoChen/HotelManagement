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
	private Integer finished;
	
	public static final int kServerStateNotFinish = 0;
	public static final int kServerStateFinished = 1;

	// Constructors

	/** default constructor */
	public Server() {
	}

	/** full constructor */
	public Server(Integer itemId, Integer count, Integer roomId, Integer finished) {
		this.itemId = itemId;
		this.count = count;
		this.roomId = roomId;
		this.finished = finished;
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
	
	public Integer getFinished() {
		return this.finished;
	}
	
	public void setFinished(Integer finished) {
		this.finished = finished;
	}

}