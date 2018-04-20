package ltd.hlmr.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	@Column(unique = true)
	private String number;

	@ApiModelProperty(value = "专业")
	@NotNull
	private String subject;

	@ApiModelProperty(value = "姓名")
	@NotNull
	private String name;

	@ApiModelProperty(value = "电话")
	@NotNull
	@Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "电话的格式不合法")
	private String phone;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@Valid
	private User user;

	@ApiModelProperty(value = "在此时间内被禁止使用", hidden = true)
	private LocalDateTime disableDateTime;

	@Transient
	@ApiModelProperty(value = "是否被禁用", hidden = true)
	private Boolean isDisable;

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

	public LocalDateTime getDisableDateTime() {
		return disableDateTime;
	}

	public void setDisableDateTime(LocalDateTime disableDateTime) {
		this.disableDateTime = disableDateTime;
	}

	/**
	 * 判断学生在某个时间段是否被禁用系统
	 * 
	 * @return
	 */
	public Boolean getIsDisable() {
		if (LocalDateTime.now().isBefore(disableDateTime)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", number=" + number + ", subject=" + subject + ", name=" + name + ", phone="
				+ phone + "]";
	}

}
