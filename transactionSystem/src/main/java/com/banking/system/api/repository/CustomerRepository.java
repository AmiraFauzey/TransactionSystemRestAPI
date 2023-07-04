package com.banking.system.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.system.api.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	Optional<Customer> findByEmail(String Email);
}
