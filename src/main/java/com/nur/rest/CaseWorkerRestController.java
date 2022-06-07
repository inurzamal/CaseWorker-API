package com.nur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.LoginForm;
import com.nur.entity.CaseWorkersAcctEntity;
import com.nur.services.CaseWorkerService;

@RestController
public class CaseWorkerRestController {
	
	@Autowired
	private CaseWorkerService service;
	
	@PostMapping("/login")
	public ResponseEntity<String> signin(@RequestBody LoginForm loginForm){
		String status = service.login(loginForm);
		return new ResponseEntity<>(status, HttpStatus.OK);		
	}
	
	@GetMapping("/forgotPwd/{email}")
	public ResponseEntity<String> forgotPazz(@PathVariable String email){
		String status = service.forgotPazzword(email);
		return new ResponseEntity<>(status, HttpStatus.OK);		
	}
	
	@PostMapping("/updateAccount")
	public ResponseEntity<String> update(@RequestBody CaseWorkersAcctEntity entity){
		String status = service.updateProfile(entity);
		return new ResponseEntity<>(status, HttpStatus.OK);			
	}
}
