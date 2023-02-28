package jj.tech.paolu.repository.jpa.daos;
// Generated 2023年2月28日 下午4:36:21 by Hibernate Tools 6.1.5.Final

//import org.springframework.data.repository.CrudRepository;
import java.lang.Long;
import jj.tech.paolu.repository.jpa.SysResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Dao object for domain model class SysResource.
 * @see jj.tech.paolu.repository.jpa.SysResource
 * @author Hibernate Tools
 */
public interface SysResourceJpaDao 
	extends JpaRepository<SysResource, Long>, QuerydslPredicateExecutor<SysResource>
{
}
