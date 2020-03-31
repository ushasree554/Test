package com.molcon.escalex.microbiology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molcon.escalex.microbiology.pojo.LoginInfo;
import com.molcon.escalex.microbiology.pojo.OauthUser;
import com.molcon.escalex.microbiology.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@PostMapping(value = "/getToken",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> getToken(LoginInfo loginInfo) throws Exception {

		OauthUser user = loginService.getToken(loginInfo);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping(value = "/checkToken",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> checkToken(LoginInfo loginInfo) throws Exception {

		boolean isValid = loginService.checkToken(loginInfo.getToken());
		return new ResponseEntity<>(isValid, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getAccessToken",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> getAccessToken(LoginInfo loginInfo) throws Exception{

		OauthUser user = loginService.getAccessToken(loginInfo);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/invalidateToken")
	public ResponseEntity<Object> invalidateToken(@RequestHeader("token") String token ) throws Exception{

		boolean user = loginService.invalidateToken(token);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
