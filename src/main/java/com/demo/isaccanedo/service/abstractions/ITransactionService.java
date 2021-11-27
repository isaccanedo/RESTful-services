package com.demo.isaccanedo.service.abstractions;

import java.math.BigDecimal;
import java.util.List;

import com.demo.isaccanedo.model.Transaction;

public interface ITransactionService {

	Transaction createNewTransaction(Long userId, boolean isBuying, String currency, BigDecimal amount);

	int getOperationCountFromLast24Hours(Long userId);

	List<Transaction> findAllByUserId(Long userId);

}
