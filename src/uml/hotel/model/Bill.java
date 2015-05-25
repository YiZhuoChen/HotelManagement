package uml.hotel.model;

import java.util.Vector;

/**
 * Bill entity. @author MyEclipse Persistence Tools
 */

public class Bill {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer roomId;
	private Integer costId;
	private Float preferential;
	private Float payoff;
	private Integer special;
	private String other;
	
	/**Œﬁ*/
	public static final int kBillSpecialNone = 0;
	/**π“’À*/
	public static final int kBillSpecialGZ = 1;
	/**ÕÀµ•*/
	public static final int kBillSpecialCancel = 2;
	/**√‚∑—*/
	public static final int kBillSpecialFree = 3;

	// Constructors

	/** default constructor */
	public Bill() {
	}

	/** minimal constructor */
	public Bill(Integer userId, Integer roomId, Integer costId,
			Float preferential, Float payoff) {
		this.userId = userId;
		this.roomId = roomId;
		this.costId = costId;
		this.preferential = preferential;
		this.payoff = payoff;
	}

	/** full constructor */
	public Bill(Integer userId, Integer roomId, Integer costId,
			Float preferential, Float payoff, Integer special, String other) {
		this.userId = userId;
		this.roomId = roomId;
		this.costId = costId;
		this.preferential = preferential;
		this.payoff = payoff;
		this.special = special;
		this.other = other;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getCostId() {
		return this.costId;
	}

	public void setCostId(Integer costId) {
		this.costId = costId;
	}

	public Float getPreferential() {
		return this.preferential;
	}

	public void setPreferential(Float preferential) {
		this.preferential = preferential;
	}

	public Float getPayoff() {
		return this.payoff;
	}

	public void setPayoff(Float payoff) {
		this.payoff = payoff;
	}

	public Integer getSpecial() {
		return this.special;
	}

	public void setSpecial(Integer special) {
		this.special = special;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}
}