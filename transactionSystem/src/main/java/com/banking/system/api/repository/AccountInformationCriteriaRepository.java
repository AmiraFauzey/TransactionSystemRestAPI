package com.banking.system.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.banking.system.api.model.AccountInformation;
import com.banking.system.api.model.AccountInformationPage;
import com.banking.system.api.model.AccountInformationSearchCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class AccountInformationCriteriaRepository {
	
	private EntityManager entityManager;
	private CriteriaBuilder criteriaBuilder;
	
	public AccountInformationCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

	public Page<AccountInformation> findAllWithFilters(AccountInformationPage accountInformationPage,
			AccountInformationSearchCriteria accountInformationSearchCriteria) {

		CriteriaQuery<AccountInformation> criteriaQuery = criteriaBuilder.createQuery(AccountInformation.class);
		Root<AccountInformation> accountRoot = criteriaQuery.from(AccountInformation.class);
		Predicate predicate = getPredicate(accountInformationSearchCriteria, accountRoot);
		criteriaQuery.where(predicate);
		setOrder(accountInformationPage, criteriaQuery, accountRoot);

		TypedQuery<AccountInformation> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(accountInformationPage.getPageNumber() * accountInformationPage.getPageSize());
		typedQuery.setMaxResults(accountInformationPage.getPageSize());

		Pageable pageable = getPageable(accountInformationPage);
		long accountCount = getAccountInfoCount(predicate);
		//long accountCount  = 1;

		return new PageImpl<>(typedQuery.getResultList(), pageable, accountCount);
	}

	private Predicate getPredicate(AccountInformationSearchCriteria accountInformationSearchCriteria,
			Root<AccountInformation> accountRoot) {

		List<Predicate> predicates = new ArrayList<>();

		if (Objects.nonNull(accountInformationSearchCriteria.getCustomerId())) {
			predicates.add(criteriaBuilder.equal(accountRoot.get("customerId"),
					accountInformationSearchCriteria.getCustomerId()));
		}
		if (Objects.nonNull(accountInformationSearchCriteria.getAccountNumber())) {
			predicates.add(criteriaBuilder.equal(accountRoot.get("accountNumber"),
					accountInformationSearchCriteria.getAccountNumber()));
		}
		if (Objects.nonNull(accountInformationSearchCriteria.getDescription())) {
			predicates.add(criteriaBuilder.like(accountRoot.get("description"),
					"%" + accountInformationSearchCriteria.getDescription() + "%"));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private void setOrder(AccountInformationPage accountInformationPage, CriteriaQuery<AccountInformation> criteriaQuery,
			Root<AccountInformation> accountRoot) {
		if (accountInformationPage.getSortDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(accountRoot.get(accountInformationPage.getSortBy())));
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(accountRoot.get(accountInformationPage.getSortBy())));
		}
	}
	
	private Pageable getPageable(AccountInformationPage accountInformationPage) {
        Sort sort = Sort.by(accountInformationPage.getSortDirection(), accountInformationPage.getSortBy());
        return PageRequest.of(accountInformationPage.getPageNumber(),accountInformationPage.getPageSize(), sort);
    }
	
	private long getAccountInfoCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<AccountInformation> countRoot = countQuery.from(AccountInformation.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
