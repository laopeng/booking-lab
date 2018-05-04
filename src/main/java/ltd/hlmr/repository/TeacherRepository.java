package ltd.hlmr.repository;

import ltd.hlmr.po.Teacher;
import java.lang.String;

public interface TeacherRepository extends HlmrRepository<Teacher, String> {
	/**
	 * 通过教师名字查询教师
	 * 
	 * @param name
	 * @return
	 */
	Teacher findByName(String name);
	
	Teacher findByUserUsername(String username);
}
