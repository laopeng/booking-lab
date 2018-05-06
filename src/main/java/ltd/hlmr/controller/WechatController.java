package ltd.hlmr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import ltd.hlmr.po.wechat.GraphicMessage;
import ltd.hlmr.po.wechat.TextMessage;
import ltd.hlmr.po.wechat.GraphicMessage.Article;
import ltd.hlmr.util.WechatUtil;

@RestController
@RequestMapping("/wechat")
@Api(tags = "提供给微信调用的接口")
public class WechatController {
	private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

	/**
	 * 验证微信服务器
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@GetMapping
	public String wechatService(String signature, String timestamp, String nonce, String echostr) {
		if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		} else {
			return "";
		}
	}

	/**
	 * 微信消息通知
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param xml
	 * @return
	 */
	@PostMapping(produces = { "application/xml;charset=UTF-8" })
	public GraphicMessage message(HttpServletRequest request, String signature, String timestamp, String nonce,
			@RequestBody TextMessage textMessage) {
		if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
			logger.debug(textMessage.toString());
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			GraphicMessage graphicMessage = new GraphicMessage();
			graphicMessage.setToUserName(textMessage.getFromUserName());
			graphicMessage.setFromUserName(textMessage.getToUserName());
			graphicMessage.setCreateTime(System.currentTimeMillis());
			graphicMessage.setArticleCount(1);
			List<Article> articles = new ArrayList<>();
			Article article = new Article();
			article.setTitle("预约实验室");
			article.setDescription("请点击进入系统操作（回复任意内容则重发此消息）");
			article.setPicUrl(basePath + "wechat/lab.jpg");
			article.setUrl(basePath + "wechat/index.html?openid=" + textMessage.getFromUserName());
			articles.add(article);
			graphicMessage.setArticles(articles);
			graphicMessage.setMsgType("news");
			return graphicMessage;
		} else {
			return null;
		}
	}
}
