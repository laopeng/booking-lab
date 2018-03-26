package ltd.hlmr;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import ltd.hlmr.repository.impl.HlmrRepositoryImpl;

/**
 * 自定义数据操作方法生成类
 * 
 * @author
 *
 * @param <R>
 * @param <T>
 * @param <I>
 */
public class HlmrRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {

	public HlmrRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		return new HlmrRepositoryFactory(em);
	}

	private static class HlmrRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {

		private final EntityManager em;

		public HlmrRepositoryFactory(EntityManager em) {
			super(em);
			this.em = em;
		}

		// 设置具体的实现类是BaseRepositoryImpl
		@SuppressWarnings("unchecked")
		@Override
		protected Object getTargetRepository(RepositoryInformation information) {
			return new HlmrRepositoryImpl<T, ID>((Class<T>) information.getDomainType(), em);
		}

		// 设置具体的实现类的class
		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return HlmrRepositoryImpl.class;
		}

	}
}