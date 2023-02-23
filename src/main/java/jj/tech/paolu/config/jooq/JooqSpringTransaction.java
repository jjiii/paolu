package jj.tech.paolu.config.jooq;

import org.jooq.Transaction;
import org.springframework.transaction.TransactionStatus;

/**
 * @ 直接复制 org.springframework.boot.autoconfigure.jooq.SpringTransaction类
 * 因为不是public，只能自己写一个
 * @author Dou
 */
public class JooqSpringTransaction implements Transaction{
	// Based on the jOOQ-spring-example from https://github.com/jOOQ/jOOQ

	private final TransactionStatus transactionStatus;

	JooqSpringTransaction(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	TransactionStatus getTxStatus() {
		return this.transactionStatus;
	}
}
