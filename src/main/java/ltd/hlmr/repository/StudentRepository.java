package ltd.hlmr.repository;

import ltd.hlmr.po.Student;

public interface StudentRepository extends HlmrRepository<Student, String> {
	Student findByUserUsername(String username);
}
