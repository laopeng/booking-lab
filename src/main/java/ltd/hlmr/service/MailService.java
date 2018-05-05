package ltd.hlmr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${mail.from}")
	private String from;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * 发送简单邮件
	 * 
	 * @param to
	 *            收件人
	 * @param subject
	 *            标题
	 * @param content
	 *            内容
	 */
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);

		try {
			mailSender.send(message);
			logger.info("简单邮件已经发送。");
		} catch (MailException e) {
			logger.error("发送简单邮件时发生异常！", e);
			throw e;
		}
	}
}