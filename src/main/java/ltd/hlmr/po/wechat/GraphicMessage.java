package ltd.hlmr.po.wechat;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "图文消息列表")
@XmlRootElement(name = "xml")
public class GraphicMessage extends BaseMessage {

	private static final long serialVersionUID = -2503343063710300693L;

	@ApiModelProperty("图文消息个数，限制为8条以内")
	private Integer articleCount;

	@ApiModelProperty("多条图文消息信息，默认第一个item为大图")
	private List<Article> articles;

	public Integer getArticleCount() {
		return articleCount;
	}

	@XmlElement(name = "ArticleCount")
	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	@XmlElementWrapper(name="Articles")
	@XmlElement(name = "item")
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "GraphicMessage [articleCount=" + articleCount + ", articles=" + articles + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime=" + createTime + ", msgType=" + msgType + "]";
	}

	@ApiModel(description = "图文消息")
	public static class Article {
		@ApiModelProperty("图文消息标题")
		private String title;

		@ApiModelProperty("图文消息描述")
		private String description;

		@ApiModelProperty("图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200")
		private String picUrl;

		@ApiModelProperty("点击图文消息跳转链接")
		private String url;

		public String getTitle() {
			return title;
		}

		@XmlElement(name = "Title")
		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		@XmlElement(name = "Description")
		public void setDescription(String description) {
			this.description = description;
		}

		public String getPicUrl() {
			return picUrl;
		}

		@XmlElement(name = "PicUrl")
		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getUrl() {
			return url;
		}

		@XmlElement(name = "Url")
		public void setUrl(String url) {
			this.url = url;
		}

		@Override
		public String toString() {
			return "Article [title=" + title + ", description=" + description + ", picUrl=" + picUrl + ", url=" + url
					+ "]";
		}

	}

}
