package ltd.hlmr.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface HlmrRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
	/**
	 * 根据id列表删除数据
	 * 
	 * @param ids
	 */
	void delete(ID[] ids);
}
