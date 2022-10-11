package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.model.Fund;
import com.example.model.Investor;
import com.example.model.Transaction;
import com.example.service.ArkDemoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
public class ArkDemoController {
	@Autowired
	ArkDemoService service;
	
	//fund CRUD
	@GetMapping("/fund")
	private List<Fund> getAllFund(){
		return service.getAllFund();
	}
	
	@GetMapping("/fund/{id}")
	private Fund getFund(@PathVariable("id") long id){
		return service.getFundById(id);
	}
	
	
	@PostMapping("/fund")
	private long saveFund(@RequestBody Fund fund){
		service.saveOrUpdateFund(fund);
		return fund.getId();
	}
	
//	@DeleteMapping("/fund/{id}")
//	private void deleteFund(@PathVariable("id") long id) {
//		service.deleteFund(id);
//	}
	
	@GetMapping("/fund/{id}/transactions")
	private List<Transaction> getFundTransactions(@PathVariable("id") long id){
		return service.getFundTransactions(id);
	}
	
	@GetMapping("/fund/{id}/balance")
	private BigDecimal getFundBalance(@PathVariable("id") long id) {
		return service.getFundBalance(id);
	}
	
	@GetMapping("/fund/{id}/investors")
	private Set<Investor> getFundInvestors(@PathVariable("id") long id){
		return service.getFundInvestors(id);
	}
	
	//investor CRUD
	@GetMapping("/investor")
	private List<Investor> getAllInvestor(){
		return service.getAllInvestor();
	}
	
	@GetMapping("/investor/{id}")
	private Investor getInvestor(@PathVariable("id") long id){
		return service.getInvestorById(id);
	}
	
	@GetMapping("/investor/{id}/transactions")
	private List<Transaction> getInvestorTransactions(@PathVariable("id") long id){
		return service.getInvestorTransactions(id);
	}
	
	@GetMapping("/investor/{id}/balance")
	private BigDecimal getInvestorBalance(@PathVariable("id") long id) {
		return service.getInvestorBalance(id);
	}
	
	@GetMapping("/investor/{id}/funds")
	private Set<Fund> getInvestorFunds(@PathVariable("id") long id){
		return service.getInvestorFunds(id);
	}
	
	@PostMapping("/investor")
	private long saveInvestor(@RequestBody Investor investor){
		service.saveOrUpdateInvestor(investor);
		return investor.getId();
	}
	
//	@DeleteMapping("/investor/{id}")
//	private void deleteInvestor(@PathVariable("id") long id) {
//		service.deleteInvestor(id);
//	}
	
	//transaction CRUD
	@GetMapping("/transaction")
	private List<Transaction> getAllTransaction(){
		return service.getAllTransaction();
	}
	
	@GetMapping("/transaction/{id}")
	private Transaction getTransaction(@PathVariable("id") long id){
		return service.getTransactionById(id);
	}
	
	
	@PostMapping("/transaction/{fundId}/{investorId}")
	private long saveTransaction(@RequestBody Transaction transaction, @PathVariable("fundId") long fundId, @PathVariable("investorId") long investorId){
		if(transaction.getType().equals("Contribution") || transaction.getType().equals("Interest Income")) {
			if(!(transaction.getAmount().compareTo(BigDecimal.ZERO) > 0)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit Transactions Must Be Positive");
			}
		}else
		if(transaction.getType().equals("Distribution") || transaction.getType().equals("General Expense") || transaction.getType().equals("Management Fee")) {
			if(!(transaction.getAmount().compareTo(BigDecimal.ZERO)<0)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debit Transactions Must Be Negative");
			}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Transaction Type");
		}
		try {
		service.saveOrUpdateTransaction(transaction, fundId, investorId);
		}catch (NoSuchElementException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Such Investor or Fund");
		}
		return transaction.getId();
	}
	
	@DeleteMapping("/transaction/{id}")
	private void deleteTransaction(@PathVariable("id") long id){
		service.deleteTransaction(id);
	}
}
