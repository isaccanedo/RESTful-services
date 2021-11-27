package com.demo.isaccanedo.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.isaccanedo.exception.BadRequestException;
import com.demo.isaccanedo.exception.InsufficientFundsException;
import com.demo.isaccanedo.model.Wealth;
import com.demo.isaccanedo.repository.WealthRepository;
import com.demo.isaccanedo.service.concretions.WealthService;

@RunWith(SpringJUnit4ClassRunner.class)
public class WealthServiceTest {

	@MockBean
	private WealthRepository repository;

	private WealthService service;

	private Wealth mockedWealth;
	private Long mockedUserId;

	@Before
	public void setUp() {
		service = new WealthService(repository);

		Map<String, BigDecimal> mockedWealthMap = new HashMap<>();
		mockedWealthMap.put("USD", BigDecimal.valueOf(2500));
		mockedWealthMap.put("TRY", BigDecimal.valueOf(2000));
		mockedWealthMap.put("EUR", BigDecimal.valueOf(3000));
		mockedWealthMap.put("AUD", BigDecimal.ZERO);

		this.mockedUserId = 5125L;
		this.mockedWealth = new Wealth(mockedUserId, mockedWealthMap);

		Mockito.when(repository.findById(mockedUserId)).thenReturn(Optional.of(mockedWealth));
	}

	@Test
	public void newWealthRecord() {
		service.newWealthRecord(25161L);
	}

	@Test
	public void makeWealthExchange() {
		service.makeWealthExchange(mockedUserId, "USD", BigDecimal.valueOf(150), true);
		service.makeWealthExchange(mockedUserId, "USD", BigDecimal.valueOf(250), false);
	}
	
	@Test(expected = InsufficientFundsException.class)
	public void makeWealthExchange_InsufficientFunds_Sell() {
		service.makeWealthExchange(mockedUserId, "USD", BigDecimal.valueOf(3000), false);
	}
	
	@Test(expected = InsufficientFundsException.class)
	public void makeWealthExchange_InsufficientFunds_Buy() {
		service.makeWealthExchange(mockedUserId, "USD", BigDecimal.valueOf(3000), true);
	}
	
	@Test(expected = BadRequestException.class)
	public void makeWealthExchange_InvalidCurrency() {
		service.makeWealthExchange(mockedUserId, "XSD", BigDecimal.valueOf(250), false);
	}

	@Test
	public void makeWealthTransaction() {
		service.makeWealthTransaction(mockedUserId, "EUR", BigDecimal.valueOf(2516), true);
		service.makeWealthTransaction(mockedUserId, "TRY", BigDecimal.valueOf(1000), false);
	}
	
	@Test(expected = InsufficientFundsException.class)
	public void makeWealthTransaction_InsufficientFunds() {
		service.makeWealthTransaction(mockedUserId, "TRY", BigDecimal.valueOf(5000), false);
	}
	
	@Test(expected = BadRequestException.class)
	public void makeWealthTransaction_InvalidCurrency() {
		service.makeWealthTransaction(mockedUserId, "DTD", BigDecimal.valueOf(250), false);
	}

	@Test
	public void findWealth() {
		service.findWealth(mockedUserId);
	}

}
