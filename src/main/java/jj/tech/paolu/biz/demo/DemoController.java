package jj.tech.paolu.biz.demo;

import static jj.tech.paolu.repository.jooq.Tables.SYS_ADMIN;
import static jj.tech.paolu.repository.jooq.Tables.SYS_ROLE;
import static jj.tech.paolu.repository.jooq.Tables.SYS_ROLE_ADMIN;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jj.tech.paolu.repository.jooq.tables.SysRoleAdmin;
import jj.tech.paolu.repository.jooq.tables.pojos.SysAdmin;
import jj.tech.paolu.repository.jooq.tables.records.SysRoleAdminRecord;
import jj.tech.paolu.utils.IDHelp;


@RestController
public class DemoController {

  @Value("${server.port}")
  String port;

  @Autowired DSLContext dsl;
  @Autowired AService aService;
  @Autowired BService bService;
  
//  @Autowired SysAdminDao sysAdminDaos;
  
  
  /**
   * JOOQ默认嵌入式事务
   * @return
   */
  @GetMapping("/")
  @Transactional
  public Object mian() {
	  aService.aa();
	  try {
	  aService.bb();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
      return "ok";
  }
  
  /**
   * JOOQ默认嵌入式事务
   * @return
   */
  @GetMapping("/t")
  @Transactional
  public Object t() {
	  aService.aa();
	  try {
	  aService.cc();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
      return "ok";
  }
  
  
  

  @GetMapping("/get")
  public Object get(String username, String password) {
	  
//	  //获取实际jdbc连接
//	  Object  r = 
//	  dslContext.connectionResult(c ->{
//		   Object result =
//				 DSL.using(c)
//			       .select(USER_INFO.USER_NAME)
//			       .from(USER_INFO)
//			       .where(USER_INFO.USER_NAME.eq(username))
//		           .and(USER_INFO.PASSWORD.eq(password))
//		           .orderBy(USER_INFO.ID.desc())
//		           .fetchAnyMap();
//		   return result;
//		});
//	   return r;
	  return "OK";
    
  }
  
  
  @RequestMapping("/getById/{id}")
  public Object getById(@PathVariable Long id) {
//	SysAdmin result =  sysAdminDaos.fetchOneById(id);
//    return result;
	  return "OK";
  }
  

  @RequestMapping("/add")
  public Object add() {

//    if (bean.getId() == null) {
//    	IDHelp id = IDHelp.getInstance(1,1);
//      bean.setId(id.nextId());
//    }
//    int i =
//        dslContext
//            .insertInto(USER_INFO)
//            .set(USER_INFO.ID, bean.getId())
//            .set(USER_INFO.REAL_NAME, bean.getRealName())
//            .set(USER_INFO.USER_NAME, bean.getUserName())
//            .set(USER_INFO.PASSWORD, bean.getPassword())
//            .set(USER_INFO.HEAD_IMG, bean.getHeadImg())
//            .execute();
	  
//	  UserInfoRecord r = dslContext.newRecord(USER_INFO); 
//	  r.setUserName("ssss");
//	  r.setRealName("bbbbb");
//	  r.setPassword("ssss");
//	  r.setPassword("ssss");
//	  r.store();
//	  System.out.println(r.getUserName());
//	  r.setHeadImg((String)null);
//	  int i = r.store();

//    return i;
	  return "";
  }

  @RequestMapping("/updata/{id}")
  public Object updata(@PathVariable Long id) {
//    int ok =
//        dslContext
//            .update(USER_INFO)
//            .set(USER_INFO.ID, 1L)
//            .set(USER_INFO.USER_NAME, "")
//            .set(USER_INFO.PASSWORD, "")
//            .set(USER_INFO.HEAD_IMG, (String)null)
//            .where(USER_INFO.ID.eq(id))
//            .execute();
//    dslContext.newRecord(USER_INFO);
    return "OK";
  }

  @RequestMapping("/ss")
  public Object ss() {
	  LocalDateTime d = LocalDateTime.now();
	  HashMap<String,Object> h = new HashMap<String,Object>();
	  IDHelp id = IDHelp.getInstance(0, 0);
	  Long i = id.nextId();
	  System.out.println(i);
	  h.put("s", i);
	  h.put("d", d);
	  
//	  U s = new U();
	  SysAdmin s = 
		dsl.selectFrom(SYS_ADMIN)
        .where(SYS_ADMIN.USERNAME.eq("admin"))
        .fetchOne().into(SysAdmin.class);
	  
	  Table<?> ur = 
		dsl.select(SYS_ROLE.ID)
  		.from(SYS_ROLE)
  		.where(SYS_ROLE.ID.eq(1L))
  		.asTable("ur");
      
      Field<Long> role_id = ur.field("role_id").coerce(Long.class);
      SysRoleAdmin r = SYS_ROLE_ADMIN.as("r");

	Result<Record1<SysRoleAdminRecord>>  bb =
	    dsl.select(r).from(ur)
	  	.leftJoin(r)
	  	.on(r.ID.equal(role_id)).fetch();
	
	    return bb.intoMaps(); 
	  }
  
  class U{
	  Long i = IDHelp.getInstance(0, 0).nextId();
	  
//	  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CST")//中国时区
//	  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")//即标准时区GMT+8
	  LocalDateTime d = LocalDateTime.now();
	
	public Long getI() {
		return i;
	}
	public void setI(Long i) {
		this.i = i;
	}
	public LocalDateTime getD() {
		return d;
	}
	public void setD(LocalDateTime d) {
		this.d = d;
	}
	  
	  
  }
  
}
