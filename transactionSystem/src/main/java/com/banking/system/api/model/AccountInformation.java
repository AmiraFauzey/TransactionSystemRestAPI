package com.banking.system.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ACCOUNT_INFORMATION")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInformation {
	
	@Id
	@Column(name="ACCOUNT_ID")
	private Integer accountId;
	
	@Column(name="ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name="TRX_AMOUNT")
	private Double trxAmount;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="TRX_DATE")
	private String date;
	
	@Column(name="TRX_TIME")
	private String trxTime;
	
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
}
