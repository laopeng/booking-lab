package ltd.hlmr.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验室预约审核日志")
@Entity
public class LabStatusLog implements Serializable {

	private static final long serialVersionUID = 6356788734063848580L;

	@Id
	@ApiModelProperty(value = "日志id")
	private String Id;

	@ApiModelProperty(value = "学生帐号")
	private String studentUsername;

	@ApiModelProperty(value = "审核老师")
	private String teacherName;

	@ApiModelProperty(value = "日志内容")
	private String content;

	@ApiModelProperty(value = "操作时间")
	private Date auditTime;

	@ApiModelProperty(value = "预约的实验室")
	@ManyToOne
	@JsonIgnore
	private LabStatus labStatus;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getStudentUsername() {
		return studentUsername;
	}

	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public LabStatus getLabStatus() {
		return labStatus;
	}

	public void setLabStatus(LabStatus labStatus) {
		this.labStatus = labStatus;
	}

}
