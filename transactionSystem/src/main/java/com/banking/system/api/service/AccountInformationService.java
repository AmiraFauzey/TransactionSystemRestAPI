package com.banking.system.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.banking.system.api.exceptionhandler.ResourceNotFoundException;
import com.banking.system.api.model.AccountInformation;
import com.banking.system.api.model.AccountInformationPage;
import com.banking.system.api.model.AccountInformationSearchCriteria;
import com.banking.system.api.repository.AccountInformationCriteriaRepository;
import com.banking.system.api.repository.AccountInformationRepository;

@Service
public class AccountInformationService {

	@Autowired
	private AccountInformationRepository accountInfoRepository;
	@Autowired
	private AccountInformationCriteriaRepository accountInformationCriteriaRepository;
	
	public AccountInformation findByAccountId(Integer accountId) {
		return accountInfoRepository.findByAccountId(accountId);
	}

	public Page<AccountInformation> getEmployees(AccountInformationPage accountPage, 
			AccountInformationSearchCriteria accountSearchCriteria) {
		accountSearchCriteria.setAccountNumber("8872838283");
		return accountInformationCriteriaRepository.findAllWithFilters(accountPage, accountSearchCriteria);
	}
	
	public AccountInformation findByDescription(String description) {
		return accountInfoRepository.findByDescription(description);
	}
	
	public AccountInformation updateAccountInfo(AccountInformation accountInfo) {

		if (findByAccountId(accountInfo.getAccountId()) != null) {
			accountInfoRepository.save(accountInfo);
			return findByAccountId(accountInfo.getAccountId());
		} else {
			throw new ResourceNotFoundException("Not found experience with id = " + accountInfo.getAccountId());
		}
	}
}
