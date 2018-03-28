package ltd.hlmr.repository;

import java.util.List;

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
}
