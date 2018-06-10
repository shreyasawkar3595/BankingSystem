package com.wissen.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.wissen.model.Account;
import com.wissen.model.Response;
import com.wissen.model.Transfer;
import com.wissen.model.TransferRequest;
import com.wissen.repository.AccountRepository;
import com.wissen.repository.TransferRepository;
import com.wissen.repository.UserRepository;

@Cacheable(false)
@Service
public class TransferService {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TransferRepository transferRepository;

	
	Map<String, Object>map= new ConcurrentHashMap<>();

	@Transactional
	public Response doTransaction(TransferRequest transferRequest) {
						
		map.putIfAbsent(transferRequest.getFromAccount(), new Object());
		map.putIfAbsent(transferRequest.getToAccount(), new Object());
		
		synchronized (map.get(transferRequest.getFromAccount())) {
			synchronized (map.get(transferRequest.getToAccount())) {
			
			System.out.println(transferRequest);
			
			Response response = new Response();
			//em.flush();
			Account fromAccount = accountRepository.findOne(transferRequest.getFromAccount());
			Account toAccount = accountRepository.findOne(transferRequest.getToAccount());
			System.out.println(fromAccount+"-"+toAccount);
				
		if (fromAccount.getBalance() >= transferRequest.getAmount()) {
			System.out.println(fromAccount.getBalance()+">="+transferRequest.getAmount());
			//OTP
			
			toAccount.setBalance(toAccount.getBalance() + transferRequest.getAmount());

			System.out.println("sleep started");
			try {
				Thread.sleep(5000);
			}catch(Exception e) {
				e.getMessage();
			}
			
			System.out.println("sleep ended");			
			fromAccount.setBalance(fromAccount.getBalance() - transferRequest.getAmount());

			Transfer transfer = new Transfer();
			System.out.println(fromAccount.getAccNum() + "  " + toAccount.getAccNum());
			transfer.setFromAccount(fromAccount);
			transfer.setToAccount(toAccount);
			transfer.setAmount(transferRequest.getAmount());
			transfer.setDescription(transferRequest.getDescription());
			fromAccount.getTransfersfrom().add(transfer);
			System.out.println(fromAccount+"-"+toAccount);
			accountRepository.saveAndFlush(fromAccount);
			accountRepository.saveAndFlush(toAccount);
			
			//em.clear();
			response.setStatus("SUCCESS");
			response.setError("NONE");
		} else {
			response.setStatus("FAILED");
			response.setError("INSUFFICIENT BALANCE");
			
		}
		
		System.out.println("transaction completed");
		return response;
		}
		}
	}
	

}

