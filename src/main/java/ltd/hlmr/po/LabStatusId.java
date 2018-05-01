package ltd.hlmr.po;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验室预约id")
@Embeddable
public class LabStatusId implements Serializable {

	private static final long serialVersionUID = -8733991768975193348L;

	@ApiModelProperty(value = "实验室")
	@ManyToOne
	private Lab lab;

	@ApiModelProperty(value = "预约日期")
	private String bookingDate;

	@ApiModelProperty(value = "预约时间段")
	private String bookingTimeRang;

	public LabStatusId() {
		super();
	}

	public LabStatusId(Lab lab, String bookingDate, String bookingTimeRang) {
		super();
		this.lab = lab;
		this.bookingDate = bookingDate;
		this.bookingTimeRang = bookingTimeRang;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookingTimeRang() {
		return bookingTimeRang;
	}

	public void setBookingTimeRang(String bookingTimeRang) {
		this.bookingTimeRang = bookingTimeRang;
	}

	@Override
	public String toString() {
		return "LabStatusId [lab=" + lab + ", bookingDate=" + bookingDate + ", bookingTimeRang=" + bookingTimeRang
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result + ((bookingTimeRang == null) ? 0 : bookingTimeRang.hashCode());
		result = prime * result + ((lab == null) ? 0 : lab.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabStatusId other = (LabStatusId) obj;
		if (bookingDate == null) {
			if (other.bookingDate != null)
				return false;
		} else if (!bookingDate.equals(other.bookingDate))
			return false;
		if (bookingTimeRang == null) {
			if (other.bookingTimeRang != null)
				return false;
		} else if (!bookingTimeRang.equals(other.bookingTimeRang))
			return false;
		if (lab == null) {
			if (other.lab != null)
				return false;
		} else if (!lab.equals(other.lab))
			return false;
		return true;
	}

}
