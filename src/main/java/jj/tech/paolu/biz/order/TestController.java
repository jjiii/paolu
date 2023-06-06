package jj.tech.paolu.biz.order;

import static jj.tech.paolu.repository.jooq.Tables.SYS_RESOURCE;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import jj.tech.paolu.repository.jpa.QSysResource;
import jj.tech.paolu.repository.jpa.SysRoleResourceId;
import jj.tech.paolu.repository.jpa.daos.SysResourceJpaDao;
import jj.tech.paolu.repository.jpa.daos.SysRoleResourceJpaDao;
@RestController
public class TestController {
	
	@Autowired DSLContext dsl;
	@Autowired SysRoleResourceJpaDao sysRoleResourceJpaDao;
	@Autowired SysResourceJpaDao sysResourceJpaDao;
	
	@GetMapping("/jpa")
	@Transactional
	public Object jpa() {
		
		SysRoleResourceId id = new SysRoleResourceId(1L,1L);
		
		Object ss = sysRoleResourceJpaDao.findById(id);
		
		return ss;
	}
	
	
	@GetMapping("/jooq")
	@Transactional
	public Object jooq() {
		
		return dsl.select(SYS_RESOURCE.NAME)
				  .from(SYS_RESOURCE)
				  .where(SYS_RESOURCE.NAME.eq("菜单根目录"))
				  .orderBy(SYS_RESOURCE.ID.desc())
				  .fetchAnyMap();
	}
	
	
	@GetMapping("/querydsl")
	public Object querydsl() {
		
		Predicate predicate = QSysResource.sysResource.id.eq(2L);
		
		Object ss = sysResourceJpaDao.findAll(predicate);
		return ss;
	}

	  

}