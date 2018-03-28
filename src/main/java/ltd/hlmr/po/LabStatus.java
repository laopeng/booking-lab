package ltd.hlmr.po;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验室预约")
@Entity
public class LabStatus implements Serializable {

	private static final long serialVersionUID = 153803788164653088L;

	@EmbeddedId
	private LabStatusId id;

	@ApiModelProperty(value = "状态")
	private Status status;

	public enum Status {
		可用, 不可用
	}

	@ApiModelProperty(value = "指导教师")
	@ManyToOne
	private Teacher teacher;

	@ApiModelProperty(value = "预约学生")
	@ManyToOne
	private Student student;

	public LabStatus() {
		super();
	}

	public LabStatus(LabStatusId id, Status status) {
		super();
		this.id = id;
		this.status = status;
	}

	public LabStatusId getId() {
		return id;
	}

	public void setId(LabStatusId id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "LabStatus [id=" + id + ", status=" + status + ", teacher=" + teacher + ", student=" + student + "]";
	}

}
