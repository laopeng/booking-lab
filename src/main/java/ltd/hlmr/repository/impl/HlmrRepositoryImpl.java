package ltd.hlmr.repository.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ltd.hlmr.repository.HlmrRepository;

/**
 * 自定义数据库操作方法
 * 
 * @author dell
 *
 * @param <T>
 * @param <ID>
 */
public class HlmrRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements HlmrRepository<T, ID> {

	public HlmrRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}

	@Override
	@Transactional
	public void delete(ID[] ids) {
		for (ID id : ids) {
			super.delete(id);
		}
	}

}
