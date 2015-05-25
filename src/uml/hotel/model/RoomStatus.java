package uml.hotel.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * RoomStatus entity. @author MyEclipse Persistence Tools
 */

public class RoomStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String startTime;
	private String endTime;
	private String roomId;
	private Integer deposit;
	private String time;
	private Integer hasReminded;
	private Integer type;
	
	public static final int kStatusDidNotRemind = 0;
	public static final int kStatusHasReminded = 1;

	// Constructors

	/** default constructor */
	public RoomStatus() {
	}

	/** full constructor */
	public RoomStatus(Integer userId, String startTime, String endTime,
			String roomId, Integer deposit, String time, Integer hasReminded,
			Integer type) {
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomId = roomId;
		this.deposit = deposit;
		this.time = time;
		this.hasReminded = hasReminded;
		this.type = type;
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

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getHasReminded() {
		return this.hasReminded;
	}

	public void setHasReminded(Integer hasReminded) {
		this.hasReminded = hasReminded;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public long getLivingDay() {
		String toString = getEndTime();
		String beginString = getStartTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date toDate = df.parse(toString);
			Date beginDate = df.parse(beginString);
			long day = (toDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			return day;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}