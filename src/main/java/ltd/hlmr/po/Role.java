package ltd.hlmr.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "系统角色")
@Entity
@Table(name = "sys_role")
public class Role implements Serializable {

	private static final long serialVersionUID = -7814338662311421666L;

	@Id
	@Column(name = "role_id")
	@ApiModelProperty(value = "用户编号，23位主键，自动生成，不用传递。", hidden = true)
	private String id;

	@ApiModelProperty(value = "角色code，不能为空，长度不能超过64个字符", required = true)
	private String code;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "创建时间", dataType = "java.util.Date", hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;

	@ApiModelProperty(value = "修改时间", dataType = "java.util.Date", hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifyDate;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	@ManyToMany
	private List<Authority> authorities;

	public Role() {
	}

	public Role(String id, String code, String description, Date createDate, List<Authority> authorities) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.createDate = createDate;
		this.authorities = authorities;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@JsonIgnore
	public List<Authority> getAuthorities() {
		return authorities;
	}

	@JsonDeserialize
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", code=" + code + ", description=" + description + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + "]";
	}

}
