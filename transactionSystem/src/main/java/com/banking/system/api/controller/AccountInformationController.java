package com.banking.system.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.system.api.model.AccountInformation;
import com.banking.system.api.model.AccountInformationPage;
import com.banking.system.api.model.AccountInformationSearchCriteria;
import com.banking.system.api.service.AccountInformationService;

@RestController
@RequestMapping("/accountInformation")
public class AccountInformationController {

	@Autowired
	private AccountInformationService accountService;

	@GetMapping
	public ResponseEntity<Page<AccountInformation>> getEmployees(AccountInformationPage accountInfoPage,
			AccountInformationSearchCriteria accountSearchCriteria) {
		return new ResponseEntity<>(accountService.getEmployees(accountInfoPage, accountSearchCriteria), HttpStatus.OK);
	}

	// 2.update account information
	@PostMapping(value = "/updateAccount/{accountId}")
	public ResponseEntity<AccountInformation> updateCompanyInfo(@PathVariable Integer accountId,
			@RequestBody AccountInformation accountInformation) throws Exception {
		accountInformation.setAccountId(accountId);
		AccountInformation updateAccountInfo = accountService.updateAccountInfo(accountInformation);

		return new ResponseEntity<>(updateAccountInfo, HttpStatus.OK);
	}

	// 3.view company account by description
	@GetMapping(value = "/viewcDescription" + "/{description}")
	public ResponseEntity<AccountInformation> getAccountDescription(@PathVariable String description) {
		AccountInformation viewAccountDescription = accountService.findByDescription(description);
		return new ResponseEntity<>(viewAccountDescription, HttpStatus.OK);
	}

}
