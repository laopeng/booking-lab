package ltd.hlmr.repository;

import ltd.hlmr.po.Teacher;
import java.lang.String;

public interface TeacherRepository extends HlmrRepository<Teacher, String> {
	Teacher findByName(String name);
}
