package ltd.hlmr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ltd.hlmr.po.LabStatusLog;
import java.lang.String;

public interface LabStatusLogRepository extends HlmrRepository<LabStatusLog, String> {
	Page<LabStatusLog> findByStudentUsername(String studentUsername, Pageable pageable);
}
