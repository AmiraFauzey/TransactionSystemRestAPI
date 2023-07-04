package com.banking.system.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInformationSearchCriteria {
	
	private Integer customerId;
	private String accountNumber;
	private String description;
}
