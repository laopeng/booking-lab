package ltd.hlmr.po;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "系统用户")
@Entity
@Table(name = "sys_user")
public class User implements UserDetails {

	private static final long serialVersionUID = -8022634575193922294L;

	@Id
	@Column(name = "user_id")
	@ApiModelProperty(value = "用户编号，自动生成，不用传递")
	private String id;

	@ApiModelProperty(value = "账号，不能为空，长度不能超过64个字符", required = true)
	@Column(unique = true)
	private String username;

	@ApiModelProperty(value = "密码，不能为空", required = true)
	@Column(nullable = false)
	private String password;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "用户类型：M=管理员，S=学生，T=管理员")
	private String type;

	@ApiModelProperty(value = "状态：A=可用，F=禁用")
	private String status;

	@ApiModelProperty(value = "创建时间", dataType = "Date", hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(updatable = false)
	private Date createDate;

	@ApiModelProperty(value = "修改时间", dataType = "Date", hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifyDate;

	@ApiModelProperty(value = "密码最后修改时间", dataType = "Date", hidden = true)
	private Date lastPasswordResetDate;

	@ApiModelProperty(value = "用户权限，只用作spring-security编码使用，不作为接口用。", hidden = true)
	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	@ManyToMany
	private List<Role> roles;

	public User() {
	}

	public User(String id, String username, String password, String description, String type, Date createDate,
			String status, List<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.description = description;
		this.type = type;
		this.createDate = createDate;
		this.status = status;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonDeserialize
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	@JsonDeserialize
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		if (!StringUtils.hasText(status) || status.equals("A")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", description=" + description
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", lastPasswordResetDate="
				+ lastPasswordResetDate + "]";
	}

}
