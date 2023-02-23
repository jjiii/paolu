package jj.tech.paolu.config.jooq;

import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.tools.JooqLogger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 修改默认事务传播行为成：PROPAGATION_REQUIRED
 * 参考 org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider
 * 修改TransactionDefinition.PROPAGATION_NESTED 为 PROPAGATION_REQUIRED
 * @author Dou
 *
 */
public class JooqSpringTransactionProvider implements TransactionProvider{
	
	// Based on the jOOQ-spring-example from https://github.com/jOOQ/jOOQ
	
	private static final JooqLogger log = JooqLogger.getLogger(JooqSpringTransactionProvider.class);

	private final PlatformTransactionManager transactionManager;

	public JooqSpringTransactionProvider(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public void begin(TransactionContext context) {
		log.info("--> begin transaction --->");
		TransactionDefinition definition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_NESTED); //PROPAGATION_REQUIRED
		TransactionStatus status = this.transactionManager.getTransaction(definition);
		context.transaction(new JooqSpringTransaction(status));
	}

	@Override
	public void commit(TransactionContext ctx) {
		log.info("--> commit transaction --->");
		this.transactionManager.commit(getTransactionStatus(ctx));
	}

	@Override
	public void rollback(TransactionContext ctx) {
		log.error("<-- rollback transaction <---");
		this.transactionManager.rollback(getTransactionStatus(ctx));
	}

	private TransactionStatus getTransactionStatus(TransactionContext ctx) {
		JooqSpringTransaction transaction = (JooqSpringTransaction) ctx.transaction();
		return transaction.getTxStatus();
	}

}
