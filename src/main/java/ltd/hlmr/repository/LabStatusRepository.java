package ltd.hlmr.repository;

import java.util.List;

import org.springframework.data.domain.Sort;

import ltd.hlmr.po.LabStatus;
import ltd.hlmr.po.LabStatusId;

public interface LabStatusRepository extends HlmrRepository<LabStatus, LabStatusId> {
	/**
	 * 通过预约时间查询实验室列表
	 * 
	 * @param bookingDate
	 * @return
	 */
	List<LabStatus> findByIdBookingDate(String bookingDate);

	/**
	 * 根据实验室名字查询大于等于当天的情况
	 * 
	 * @param name
	 * @param now
	 * @param sort
	 * @return
	 */
	List<LabStatus> findByIdLabNameAndIdBookingDateGreaterThanEqual(String name, String now, Sort sort);
}
