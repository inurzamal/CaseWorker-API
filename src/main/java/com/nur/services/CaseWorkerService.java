package com.nur.services;

import com.nur.bindings.LoginForm;
import com.nur.entity.CaseWorkersAcctEntity;

public interface CaseWorkerService {
	
	public String login(LoginForm loginForm);
	public String forgotPazzword(String email);
	public String updateProfile(CaseWorkersAcctEntity entity);
	public String dashboard();

}
