package uml.hotel.model;

/**
 * Server entity. @author MyEclipse Persistence Tools
 */

public class Server {

	// Fields

	private Integer id;
	private Float cost;
	private String content;
	private Integer itemId;

	// Constructors

	/** default constructor */
	public Server() {
	}

	/** full constructor */
	public Server(Float cost, String content) {
		this.cost = cost;
		this.content = content;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getCost() {
		return this.cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getItemId() {
		return this.itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
}