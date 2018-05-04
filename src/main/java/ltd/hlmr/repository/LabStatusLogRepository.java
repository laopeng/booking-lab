package ltd.hlmr.repository;

import java.util.List;

import ltd.hlmr.po.LabStatusId;
import ltd.hlmr.po.LabStatusLog;

public interface LabStatusLogRepository extends HlmrRepository<LabStatusLog, String> {
	List<LabStatusLog> findByLabStatusId(LabStatusId labStatusId);
}
