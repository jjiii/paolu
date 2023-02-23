package jj.tech.paolu.biz.demo;

//import static tech.bcnew.jooq.repository.Tables.SYS_ADMIN;

import java.sql.Connection;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AService {
	
	
	@Autowired DSLContext dsl;
	
	@Transactional
	public void aa(){
//		dsl.insertInto(SYS_ADMIN)
//		.set(SYS_ADMIN.REALNAME, "1")
//		.set(SYS_ADMIN.USERNAME,"1")
//		.set(SYS_ADMIN.PASSWORD,"1")
//		.set(SYS_ADMIN.IS_LOCKED, (byte)0)
//		.execute();
	}
	
	
	
	public void bb(){
		
		dsl.transaction(tx->{

//			tx.dsl().insertInto(SYS_ADMIN)
//			.set(SYS_ADMIN.REALNAME, "2")
//			.set(SYS_ADMIN.USERNAME,"SS")
//			.set(SYS_ADMIN.PASSWORD,"SS")
//			.set(SYS_ADMIN.IS_LOCKED, (byte)0)
//			.execute();
//
//			tx.dsl().insertInto(SYS_ADMIN)
//			.set(SYS_ADMIN.REALNAME, "2")
//			.set(SYS_ADMIN.USERNAME,"SS")
//			.set(SYS_ADMIN.PASSWORD,"SS")
//			.set(SYS_ADMIN.IS_LOCKED, (byte)0 )
//			.execute();
			int i = 1/0;
		});
		
		
		
	}
	
	@Transactional(propagation = Propagation.NESTED)
	public void cc(){


//			dsl.insertInto(SYS_ADMIN)
//			.set(SYS_ADMIN.REALNAME, "2")
//			.set(SYS_ADMIN.USERNAME,"SS")
//			.set(SYS_ADMIN.PASSWORD,"SS")
//			.set(SYS_ADMIN.IS_LOCKED, (byte)0)
//			.execute();
//
//			dsl.insertInto(SYS_ADMIN)
//			.set(SYS_ADMIN.REALNAME, "2")
//			.set(SYS_ADMIN.USERNAME,"SS")
//			.set(SYS_ADMIN.PASSWORD,"SS")
//			.set(SYS_ADMIN.IS_LOCKED, (byte)0 )
//			.execute();
			int i = 1/0;
	
		
		
		
	}
	
	
}
