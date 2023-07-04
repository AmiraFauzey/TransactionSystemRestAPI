package com.banking.system.api.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
	
	private Integer customerId;
	private String accountNumber;
	private String description;
	
	
}
