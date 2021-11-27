package com.demo.isaccanedo.response;

import java.util.List;

import com.demo.isaccanedo.model.Transaction;

import lombok.Data;

@Data
public class FindAllTransactionsByUserResponse {
	private List<Transaction> transactionList;
}
