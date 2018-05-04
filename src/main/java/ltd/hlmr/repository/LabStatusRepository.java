package ltd.hlmr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ltd.hlmr.po.LabStatus;
import ltd.hlmr.po.LabStatusId;
import ltd.hlmr.po.Student;

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

	/**
	 * 根据学生查询大于等于当天的预约情况
	 * 
	 * @param student
	 * @param now
	 * @return
	 */
	List<LabStatus> findByStudentAndIdBookingDateGreaterThanEqual(Student student, String now);

	/**
	 * 按照实验室id查询实验室预约情况
	 * 
	 * @param labId
	 * @param pageable
	 * @return
	 */
	Page<LabStatus> findByIdLabId(String labId, Pageable pageable);
}
