package ltd.hlmr.po;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "学生")
@Entity
public class Student implements Serializable {

	private static final long serialVersionUID = 6986095148168660410L;

	public Student() {
		super();
	}

	public Student(String id, String number, String subject, String name, String phone, User user) {
		super();
		this.id = id;
		this.number = number;
		this.subject = subject;
		this.name = name;
		this.phone = phone;
		this.user = user;
	}

	@Id
	@Column(name = "student_id")
	@ApiModelProperty(value = "学生编号，23位主键，自动生成，不用传递。", hidden = true)
	private String id;

	@ApiModelProperty(value = "学号，不能为空", required = true)
	@NotNull
	private String number;

	@ApiModelProperty(value = "专业")
	private String subject;

	@ApiModelProperty(value = "姓名")
	@NotNull
	private String name;

	@ApiModelProperty(value = "电话")
	@NotNull
	private String phone;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", number=" + number + ", subject=" + subject + ", name=" + name + ", phone="
				+ phone + "]";
	}

}
