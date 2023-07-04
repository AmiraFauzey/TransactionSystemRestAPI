package com.banking.system.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.banking.system.api.model.AccountInformation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountInformationSearchDao {
	
	private EntityManager em;
	
	public List<AccountInformation> findAllBySimpleQuery(
			Integer customerId,
			String accountNumber,
			String description){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<AccountInformation> criteriaQuery = criteriaBuilder.createQuery(AccountInformation.class);
		
		//Select * from accountInformation
		Root<AccountInformation> root = criteriaQuery.from(AccountInformation.class);
		
		//prepare WHERE clause (predicate)
		//WHERE customerId like '%0123%'
		Predicate customerIdPredicate = criteriaBuilder
				.equal(root.get("customerId"), customerId);
		Predicate accountNumberPredicate = criteriaBuilder
				.like(root.get("accountNumber"), "%" + accountNumber + "%");
		Predicate descriptionPredicate = criteriaBuilder
				.like(root.get("description"), "%" + description + "%");
		
		Predicate customerIdOrAccountNumberPredicate = criteriaBuilder.or(
				customerIdPredicate,
				accountNumberPredicate,
				descriptionPredicate
				);
		//=> final query ==> select * from accountInformation where accountNumber like '%12312312%'
		var andDescriptionPredicate = criteriaBuilder.and(customerIdOrAccountNumberPredicate,customerIdOrAccountNumberPredicate);
		criteriaQuery.where(andDescriptionPredicate);
		
		TypedQuery<AccountInformation> query = em.createQuery(criteriaQuery);
		return query.getResultList();	
	}
	
	
	public List<AccountInformation> findAllByCriteria(SearchRequest request){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<AccountInformation> criteriaQuery = criteriaBuilder.createQuery(AccountInformation.class);
		List<Predicate> predicates = new ArrayList<>();
		
		//Select * from AccountInformation
		Root<AccountInformation> root = criteriaQuery.from(AccountInformation.class);
		if(request.getCustomerId() != null) {
			Predicate customerIdPredicate = criteriaBuilder.equal(root.get("customerId"), request.getCustomerId());
			predicates.add(customerIdPredicate);
		}
		if(request.getAccountNumber() != null) {
			Predicate accountNumberPredicate = criteriaBuilder.like(root.get("accountNumber"), "%" + request.getAccountNumber() + "%");
			predicates.add(accountNumberPredicate);
		}
		if(request.getDescription() != null) {
			Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + request.getDescription() + "%");
			predicates.add(descriptionPredicate);
		}
		
		criteriaQuery.where(
				criteriaBuilder.or(predicates.toArray(new Predicate[0])));
		TypedQuery<AccountInformation> query = em.createQuery(criteriaQuery);
		return query.getResultList();
		}
}
