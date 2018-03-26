package ltd.hlmr.po;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验室")
@Entity
public class Lab implements Serializable {

	private static final long serialVersionUID = 3300279216721781294L;

	public Lab() {
		super();
	}

	public Lab(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "lab_id")
	@ApiModelProperty(value = "实验室编号，23位主键，自动生成，不用传递。", hidden = true)
	private String id;

	@ApiModelProperty(value = "实验室名称")
	@NotNull
	private String name;

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

}
