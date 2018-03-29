package ltd.hlmr.job;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ltd.hlmr.po.Lab;
import ltd.hlmr.po.LabStatus;
import ltd.hlmr.po.LabStatus.Status;
import ltd.hlmr.po.LabStatusId;
import ltd.hlmr.repository.LabRepository;
import ltd.hlmr.repository.LabStatusRepository;

/**
 * 生成实验室时间段的job
 * 
 * @author
 *
 */
@Component
public class LabStatusJob {
	private Logger logger = LoggerFactory.getLogger(LabStatusJob.class);

	@Autowired
	private LabRepository labRepository;
	@Autowired
	private LabStatusRepository labStatusRepository;

	/**
	 * 生成
	 */
	@Scheduled(cron = "0 0/5 * * * ?") // 每分钟执行一次
	public void general() {
		for (long i = 0L; i < 7; i++) {
			LocalDate now = LocalDate.now();
			now = now.plusDays(i);
			for (Lab lab : labRepository.findAll()) {
				LabStatusId id = new LabStatusId(lab, now.toString(), "上午");
				if (labStatusRepository.findOne(id) == null) {
					LabStatus labStatus = new LabStatus(id, Status.可用);
					labStatusRepository.save(labStatus);
				}
				id = new LabStatusId(lab, now.toString(), "下午");
				if (labStatusRepository.findOne(id) == null) {
					LabStatus labStatus = new LabStatus(id, Status.可用);
					labStatusRepository.save(labStatus);
				}
				id = new LabStatusId(lab, now.toString(), "晚上");
				if (labStatusRepository.findOne(id) == null) {
					LabStatus labStatus = new LabStatus(id, Status.可用);
					labStatusRepository.save(labStatus);
				}
			}
			List<LabStatus> list = labStatusRepository.findByIdBookingDate(now.toString());
			for (LabStatus labStatus : list) {
				logger.debug("预约实验室：" + labStatus);
			}
		}
	}
}
