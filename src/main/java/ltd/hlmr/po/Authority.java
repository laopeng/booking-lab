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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "系统权限")
@Entity
@Table(name = "sys_authority")
public class Authority implements Serializable {
	private static final long serialVersionUID = -5604640071174433644L;

	@Id
	@Column(name = "authority_id")
	@ApiModelProperty(value = "用户编号，23位主键，自动生成，不用传递", hidden = true)
	private String id;

	@ApiModelProperty(value = "权限code，不能为空，长度不能超过64个字符", required = true)
	@Column(unique = true)
	private String code;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "创建时间", dataType = "java.util.Date", hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;

	@ApiModelProperty(value = "修改时间", dataType = "java.util.Date", hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifyDate;

	@ManyToMany(mappedBy = "authorities")
	@JsonIgnore
	private List<Role> roles;

	public Authority() {
	}

	public Authority(String id, String code, String description, Date createDate) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.createDate = createDate;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", code=" + code + ", description=" + description + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + "]";
	}

}
