package com.banking.system.api.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.system.api.config.JwtService;
import com.banking.system.api.model.Customer;
import com.banking.system.api.model.Role;
import com.banking.system.api.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final CustomerRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtServ;
	private final AuthenticationManager authmanager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		// TODO Auto-generated method stub
		var user =Customer.builder()
		        .firstName(request.getFirstname())
		        .lastName(request.getLastname())
		        .email(request.getEmail())
		        .password(passwordEncoder.encode(request.getPassword()))
		        .role(Role.USER)
		        .build();
		repository.save(user);
		var jwtToken = jwtServ.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticateRequest request) {
		// TODO Auto-generated method stub
		authmanager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		var user = repository.findByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken = jwtServ.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

}
