package uml.hotel.model;

/**
 * Cost entity. @author MyEclipse Persistence Tools
 */

public class Cost {

	// Fields

	private Integer id;
	private Integer roomId;
	private Float cost;
	private Integer discount;

	// Constructors

	/** default constructor */
	public Cost() {
	}


	/** full constructor */
	public Cost(Integer roomId, Float cost, Integer discount) {
		this.roomId = roomId;
		this.cost = cost;
		this.discount = discount;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Float getCost() {
		return this.cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Integer getDiscount() {
		return this.discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

}