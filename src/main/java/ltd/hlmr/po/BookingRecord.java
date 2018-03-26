package ltd.hlmr.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "预约记录")
@Entity
public class BookingRecord implements Serializable {

	private static final long serialVersionUID = 8347470028329809661L;

	@Id
	@Column(name = "record_id")
	@ApiModelProperty(value = "编号，23位主键，自动生成，不用传递。", hidden = true)
	private String id;

	@ApiModelProperty(value = "预约学生")
	@ManyToOne
	private Student student;

	@ApiModelProperty(value = "指导教师")
	@ManyToOne
	private Teacher teacher;

	@ApiModelProperty(value = "审核状态")
	private Status status;

	public enum Status {
		审核不通过, 审核通过
	}

	@ApiModelProperty(value = "取消的类型")
	private CancelType cancelType;

	public enum CancelType {
		通过后取消, 未审核时取消
	}

}
