package com.molcon.escalex.microbiology.services;

import org.springframework.stereotype.Component;

import com.molcon.escalex.microbiology.pojo.LoginInfo;
import com.molcon.escalex.microbiology.pojo.OauthUser;


@Component
public interface LoginService {

	public OauthUser getToken(LoginInfo loginInfo) throws Exception;
	public boolean checkToken(String token) throws Exception;
	public OauthUser getAccessToken(LoginInfo loginInfo) throws Exception;
	public boolean invalidateToken(String token) throws Exception;
	
}
