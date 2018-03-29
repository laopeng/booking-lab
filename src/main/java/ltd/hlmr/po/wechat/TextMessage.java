package ltd.hlmr.po.wechat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "文本消息")
@XmlRootElement(name = "xml")
public class TextMessage extends BaseMessage {

	private static final long serialVersionUID = -8924321069129626848L;

	// 消息内容
	private String content;

	public String getContent() {
		return content;
	}

	@XmlElement(name = "Content")
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TextMessage [content=" + content + ", toUserName=" + toUserName + ", fromUserName=" + fromUserName
				+ ", createTime=" + createTime + ", msgType=" + msgType + "]";
	}

}
