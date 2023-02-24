package jj.tech.paolu.repository.jpa.daos;
// Generated 2023年2月24日 上午9:45:45 by Hibernate Tools 6.1.5.Final

//import org.springframework.data.repository.CrudRepository;
import java.lang.Long;
import jj.tech.paolu.repository.jpa.SysAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Dao object for domain model class SysAdmin.
 * @see jj.tech.paolu.repository.jpa.SysAdmin
 * @author Hibernate Tools
 */
public interface SysAdminJpaDao 
	extends JpaRepository<SysAdmin, Long>, QuerydslPredicateExecutor<SysAdmin>
{
}
