package uml.hotel.model;

/**
 * ServerItem entity. @author MyEclipse Persistence Tools
 */

public class ServerItem implements java.io.Serializable {

	// Fields

	private Integer id;
	private String content;
	private Float cost;

	// Constructors

	/** default constructor */
	public ServerItem() {
	}

	/** full constructor */
	public ServerItem(String content, Float cost) {
		this.content = content;
		this.cost = cost;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Float getCost() {
		return this.cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

}