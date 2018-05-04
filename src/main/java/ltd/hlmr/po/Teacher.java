package ltd.hlmr.po;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "指导教师")
@Entity
public class Teacher implements Serializable {

	private static final long serialVersionUID = -8901401838045907063L;

	public Teacher() {
		super();
	}

	public Teacher(String id, String name, String phone, String email, User user) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.user = user;
	}

	@Id
	@Column(name = "teacher_id")
	@ApiModelProperty(value = "教师编号，23位主键，自动生成，不用传递。", hidden = true)
	private String id;

	@ApiModelProperty(value = "姓名")
	@NotNull
	@Column(unique = true)
	private String name;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "邮箱")
	@NotNull
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@ApiModelProperty(value = "账号信息", hidden = true)
	private User user;

	@Transient
	private String key;

	@Transient
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	@JsonDeserialize
	public void setUser(User user) {
		this.user = user;
	}

	public String getKey() {
		return this.name;
	}

	public String getValue() {
		return this.name;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", user=" + user
				+ "]";
	}

}
