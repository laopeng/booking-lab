package ltd.hlmr.po.wechat;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "基础消息")
public class BaseMessage implements Serializable {
	private static final long serialVersionUID = -2447954342002959551L;
	// 接收方
	protected String toUserName;
	// 发送方
	protected String fromUserName;
	// 消息的创建时间
	protected Long createTime;
	// 消息类型
	protected String msgType;

	public String getToUserName() {
		return toUserName;
	}

	@XmlElement(name = "ToUserName")
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	@XmlElement(name = "FromUserName")
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	@XmlElement(name = "CreateTime")
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	@XmlElement(name = "MsgType")
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Override
	public String toString() {
		return "BaseMessage [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", createTime=" + createTime
				+ ", msgType=" + msgType + "]";
	}

}
