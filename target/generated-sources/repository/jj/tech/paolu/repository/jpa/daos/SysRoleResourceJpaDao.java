package jj.tech.paolu.repository.jpa.daos;
// Generated 2023年2月24日 上午9:45:45 by Hibernate Tools 6.1.5.Final

//import org.springframework.data.repository.CrudRepository;
import jj.tech.paolu.repository.jpa.SysRoleResourceId;
import jj.tech.paolu.repository.jpa.SysRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Dao object for domain model class SysRoleResource.
 * @see jj.tech.paolu.repository.jpa.SysRoleResource
 * @author Hibernate Tools
 */
public interface SysRoleResourceJpaDao 
	extends JpaRepository<SysRoleResource, SysRoleResourceId>, QuerydslPredicateExecutor<SysRoleResource>
{
}
