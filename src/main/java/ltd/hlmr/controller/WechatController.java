package ltd.hlmr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;

@RestController
@RequestMapping("/wechat")
@Api(tags = "提供给微信调用的接口")
public class WechatController {
	private static final Logger logger = LoggerFactory.getLogger(WechatController.class);
	// private static final String appId = "wx9c6eb91e9f9af205";
	// private static final String secret = "d29875f42f0b03afa79c6cbd162b4b54";

	private static final String appId = "wxcacab1dfed5bc106";
	private static final String secret = "d29875f42f0b03afa79c6cbd162b4b54";
	@Autowired
	private OkHttpClient okHttpClinet;

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

	@GetMapping("/user/token")
	public String getUserAccessToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret + "&code="
				+ code + "&grant_type=authorization_code";
		Request request = new Request.Builder().url(url).build();
		String result = null;
		try {
			okhttp3.Response response = okHttpClinet.newCall(request).execute();
			result = response.body().string();
		} catch (IOException e) {
			logger.error("请求微信用户token接口出错：", e);
		}
		return result;
	}
}
