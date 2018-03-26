package ltd.hlmr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@Api(tags = "登录认证")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private OkHttpClient okHttpClinet;

	@GetMapping("/token")
	@ApiOperation(value = "根据账号和密码获取token，token在48小时内有效,过期后请再次调用。")
	@ResponseBody
	public String createAuthenticationToken(HttpServletRequest request, @RequestParam String username, String password)
			throws IOException {
		if (!StringUtils.hasText(password)) {
			password = username;
		}
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		String credential = Credentials.basic("boking-lab", "jklkiojjjfokkdsdfk452254d554551dfsdfgg");
		Request request2 = new Request.Builder().header("Authorization", credential)
				.url(basePath + "oauth/token?grant_type=password&username=" + username + "&password=" + password)
				.post(okhttp3.internal.Util.EMPTY_REQUEST).build();
		Response response = okHttpClinet.newCall(request2).execute();
		String result = response.body().string();
		logger.debug(result);
		return result;
	}

}