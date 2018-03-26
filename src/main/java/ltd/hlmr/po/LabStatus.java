package ltd.hlmr.po;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验室")
@Entity
public class LabStatus implements Serializable {

	private static final long serialVersionUID = 153803788164653088L;

	public LabStatus() {
		super();
	}

	public LabStatus(Lab lab, String bookingDate, String bookingTimeRang, Status status) {
		super();
		this.lab = lab;
		BookingDate = bookingDate;
		BookingTimeRang = bookingTimeRang;
		this.status = status;
	}

	@Id
	@ApiModelProperty(value = "预约实验室")
	@ManyToOne
	private Lab lab;

	@Id
	@ApiModelProperty(value = "预约日期")
	private String BookingDate;

	@Id
	@ApiModelProperty(value = "预约时间段")
	private String BookingTimeRang;

	@ApiModelProperty(value = "状态")
	private Status status;

	public enum Status {
		可用, 不可用
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public String getBookingDate() {
		return BookingDate;
	}

	public void setBookingDate(String bookingDate) {
		BookingDate = bookingDate;
	}

	public String getBookingTimeRang() {
		return BookingTimeRang;
	}

	public void setBookingTimeRang(String bookingTimeRang) {
		BookingTimeRang = bookingTimeRang;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
