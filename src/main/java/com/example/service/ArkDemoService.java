package com.example.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;

import com.example.model.Fund;
import com.example.model.Investor;
import com.example.model.Transaction;
import com.example.repository.FundRepository;
import com.example.repository.InvestorRepository;
import com.example.repository.TransactionRepository;

@Service
public class ArkDemoService {
	@Autowired
	FundRepository fundRepository;
	
	@Autowired
	InvestorRepository investorRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	//Fund CRUD
	public Fund getFundById(long id)
	{
		return fundRepository.findById(id).get();
	}
	public void saveOrUpdateFund(Fund fund) {
		fundRepository.save(fund);
	}
	public List<Fund> getAllFund() {
		List<Fund> funds = new ArrayList<Fund>();
		fundRepository.findAll().forEach(fund -> funds.add(fund));
		return funds;
	}
	public void deleteFund(long id) {
		fundRepository.deleteById(id);
	}
	public List<Transaction> getFundTransactions(long id) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactionRepository.findByFund_Id(id).forEach(transaction -> transactions.add(transaction));
		return transactions;
	}
	public BigDecimal getFundBalance(long id) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactionRepository.findByFund_Id(id).forEach(transaction -> transactions.add(transaction));
		BigDecimal totalBalance = BigDecimal.ZERO;
		for (Transaction t : transactions) {
			totalBalance = totalBalance.add(t.getAmount());
		}
		return totalBalance;
	}
	public Set<Investor> getFundInvestors(long id){
		Set<Investor> investors = new LinkedHashSet<Investor>();
		transactionRepository.findByFund_Id(id).forEach(transaction -> investors.add(transaction.getInvestor()));
		return investors;
	}
	
	//Transaction CRUD
	public Transaction getTransactionById(long id)
	{
		return transactionRepository.findById(id).get();
	}
	public void saveOrUpdateTransaction(Transaction transactionRequest, long fundId, long investorId) throws NoSuchElementException{
		Fund fund = fundRepository.findById(fundId).get();
		Investor investor = investorRepository.findById(investorId).get();
		transactionRequest.setFund(fund);
		transactionRequest.setInvestor(investor);
		if(transactionRequest.getDate()==null) {
			transactionRequest.setDate(new Date());
		}
		transactionRepository.save(transactionRequest);
	}
	public List<Transaction> getAllTransaction() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactionRepository.findAll().forEach(transaction -> transactions.add(transaction));
		return transactions;
	}
	public void deleteTransaction(long id) {
		transactionRepository.deleteById(id);
	}
	
	
	//Investor CRUD
	public Investor getInvestorById(long id)
	{
		return investorRepository.findById(id).get();
	}
	public void saveOrUpdateInvestor(Investor investor) {
		investorRepository.save(investor);
	}
	public List<Investor> getAllInvestor() {
		List<Investor> investors = new ArrayList<Investor>();
		investorRepository.findAll().forEach(investor -> investors.add(investor));
		return investors;
	}
	public void deleteInvestor(long id) {
		investorRepository.deleteById(id);
	}
	
	public List<Transaction> getInvestorTransactions(long id) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactionRepository.findByInvestor_Id(id).forEach(transaction -> transactions.add(transaction));
		return transactions;
	}
	public BigDecimal getInvestorBalance(long id) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactionRepository.findByInvestor_Id(id).forEach(transaction -> transactions.add(transaction));
		BigDecimal totalBalance = BigDecimal.ZERO;
		for (Transaction t : transactions) {
			totalBalance = totalBalance.add(t.getAmount());
		}
		return totalBalance;
	}
	public Set<Fund> getInvestorFunds(long id){
		Set<Fund> funds = new LinkedHashSet<Fund>();
		transactionRepository.findByInvestor_Id(id).forEach(transaction -> funds.add(transaction.getFund()));
		return funds;
	}
}
