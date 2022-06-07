package com.nur.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.bindings.LoginForm;
import com.nur.entity.CaseWorkersAcctEntity;
import com.nur.repository.CaseWorkersAcctRepository;
import com.nur.utils.EmailUtils;

@Service
public class CaseWorkerServiceImpl implements CaseWorkerService {
	
	Logger logger = LoggerFactory.getLogger(CaseWorkerServiceImpl.class);
	
	@Autowired
	private CaseWorkersAcctRepository workerRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	 

	@Override
	public String login(LoginForm loginForm) {
		CaseWorkersAcctEntity entity = workerRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPazzword());
		
		if(entity == null) {
			return "Invalid Credentials..";
		}
		
		return "SUCCESS";
	}

	@Override
	public String forgotPazzword(String email) {
		
		CaseWorkersAcctEntity entity = workerRepo.findByEmail(email);
		
		if(entity == null) {
			return "Invalid Credentials..";
		}
		
		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		String mailBody = readMailBodyContent(fileName, entity);
		String subjects = "Recover Password";
		
		String toEmail = entity.getEmail();
		
		boolean isSent = emailUtils.sendEmail(toEmail, subjects, mailBody);
		
		if(isSent) {
			return "Pazzword Sent to your registered email";
		}
		
		return "FAIL";
	}

	@Override
	public String updateProfile(CaseWorkersAcctEntity entity) {
		
		if(entity.getAcctId() == null) {
			return "Can't update without acctId";
		}		
		workerRepo.save(entity);
		return "Updated Successfully";
	}

	@Override
	public String dashboard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	//Reading file for mailBody content
	private String readMailBodyContent(String fileName, CaseWorkersAcctEntity entity) {

		String mailBody = null;

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String line = null;

		try {

			br = new BufferedReader(new FileReader(fileName));
			line = br.readLine(); 

			while (line != null) {
				sb.append(line); 
				line = br.readLine(); 
			}

			mailBody = sb.toString();

			mailBody = mailBody.replace("{NAME}", entity.getFullName());
			mailBody = mailBody.replace("{PWD}", entity.getPwd());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return mailBody;
	}

}
