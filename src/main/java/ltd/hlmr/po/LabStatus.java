package ltd.hlmr.po;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验室预约")
@Entity
public class LabStatus implements Serializable {

	private static final long serialVersionUID = 153803788164653088L;

	@EmbeddedId
	@ApiModelProperty(value = "实验室预约id")
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

	@ApiModelProperty(value = "审核状态")
	private Audit audit;

	@ApiModelProperty(value = "审核日志")
	@OneToMany(mappedBy = "labStatus")
	private List<LabStatusLog> labStatusLogs;

	public enum Audit {
		未审, 通过, 不通过, 过期
	}

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

	public Audit getAudit() {
		String bookingDate = this.getId().getBookingDate();
		if (this.audit != null) {
			if (LocalDate.parse(bookingDate).isBefore(LocalDate.now())) {
				if (this.audit.equals(Audit.未审)) {
					return Audit.过期;
				}
			}
		}
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public List<LabStatusLog> getLabStatusLogs() {
		return labStatusLogs;
	}

	public void setLabStatusLogs(List<LabStatusLog> labStatusLogs) {
		this.labStatusLogs = labStatusLogs;
	}

	@Override
	public String toString() {
		return "LabStatus [id=" + id + ", status=" + status + ", teacher=" + teacher + ", student=" + student + "]";
	}

}
