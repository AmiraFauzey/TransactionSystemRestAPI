package com.banking.system.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banking.system.api.model.AccountInformation;

@Repository
public interface AccountInformationRepository extends CrudRepository<AccountInformation, Integer>{
	
	@Query("select u from AccountInformation u where u.description = :description")
	public AccountInformation findByDescription(@Param("description") String description);
	
	@Query("select u from AccountInformation u where u.accountId = :accountId")
	public AccountInformation findByAccountId(@Param("accountId") Integer accountId);
}
