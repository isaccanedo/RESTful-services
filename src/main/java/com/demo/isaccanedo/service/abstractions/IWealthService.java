package com.demo.isaccanedo.service.abstractions;

import java.math.BigDecimal;
import java.util.Map;

import com.demo.isaccanedo.model.Wealth;

public interface IWealthService {
	
	void newWealthRecord(Long userId);

	Wealth findWealth(Long userId);
	
	Map<String, Double> getCurrencyRates();

	void makeWealthExchange(Long userId, String currency, BigDecimal amount, boolean isBuying);
	
	void makeWealthTransaction(Long userId, String currency, BigDecimal amount, boolean isIncrementing);

}
