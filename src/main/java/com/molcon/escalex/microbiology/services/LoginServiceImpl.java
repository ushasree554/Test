package com.molcon.escalex.microbiology.services;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.molcon.escalex.microbiology.exception.TokenNotFoundException;
import com.molcon.escalex.microbiology.exception.UserNotFoundException;
import com.molcon.escalex.microbiology.pojo.LoginInfo;
import com.molcon.escalex.microbiology.pojo.OauthUser;


@Component
public class LoginServiceImpl implements LoginService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${oauthresource.clientId}")
	private String clientId;

	@Value("${oauthresource.clientSecret}")
	private String clientSecret;

	@Value("${oauthresource.checkTokenUrl}")
	private String checkTokenUrl;

	@Value("${oauthresource.getTokenUrl}")
	private String getTokenUrl;

	HttpHeaders httpHeader;
	private URI url;
	
	@Autowired
	OauthUser user;
	
	@PostConstruct
	public void init() throws URISyntaxException {	
		httpHeader = new HttpHeaders();
		String plainCreds = this.clientId+":"+this.clientSecret;
		String baseUrl = this.getTokenUrl;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);	
		httpHeader.add("Authorization", "Basic " + base64Creds);
		httpHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeader.add("Accept", MediaType.APPLICATION_JSON.toString());		
		url = new URI(baseUrl);		
	  }


	@Override
	public OauthUser getToken(LoginInfo loginInfo) throws Exception {
		
		try {
			url = new URI(getTokenUrl);
			httpHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("username",loginInfo.getUsername());
			map.add("password",loginInfo.getPassword());
			map.add("grant_type",loginInfo.getGrant_type());
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeader);   
			user = restTemplate.postForObject(url,entity,OauthUser.class); 
			if(user == null) {
				throw new UserNotFoundException("User Not Found");
			}
			
		}catch (HttpStatusCodeException exception) {
			int statusCode = exception.getStatusCode().value();
			if(statusCode == 401) {
				throw new UserNotFoundException("User Not Found");
			}else if(statusCode == 400) {
				throw new UserNotFoundException("User Not Found");
			}else {
				throw new Exception("Internal Server Error");
			}
		}
		return user;
	}


	@Override
	public boolean checkToken(String token) throws Exception {
		
		boolean isValid = false;
		try {
			url = new URI(checkTokenUrl);	
			System.out.println("checkToken token "+token);
			System.out.println(url);
			httpHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("token",token);
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeader);   
			user = restTemplate.postForObject(url,entity,OauthUser.class); 
			System.out.println(user);
			if(user == null) {
				throw new TokenNotFoundException("Invalid Token: "+token);
			}
			isValid = true;
			
		}catch (HttpStatusCodeException exception) {
			int statusCode = exception.getStatusCode().value();
			System.out.println(statusCode);
			if(statusCode == 400) {
				throw new TokenNotFoundException("Invalid Token: "+token);
			}else {
				throw new Exception("Internal Server Error");
			}
		}
		return isValid;
	
	}


	@Override
	public OauthUser getAccessToken(LoginInfo loginInfo) throws Exception {

		try {
			httpHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			url = new URI(getTokenUrl);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("access_token",loginInfo.getAccess_token());
			map.add("refresh_token",loginInfo.getRefresh_token());
			map.add("grant_type",loginInfo.getGrant_type());
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeader);   
			user = restTemplate.postForObject(url,entity,OauthUser.class); 
			if(user == null) {
				throw new UserNotFoundException("Invalid Access token: "+loginInfo.getAccess_token());
			}
			
		}catch (HttpStatusCodeException exception) {
			int statusCode = exception.getStatusCode().value();
			if(statusCode == 401) {
				throw new UserNotFoundException("Invalid Access token: "+loginInfo.getAccess_token());
			}else {
				throw new Exception("Internal Server Error");
			}
		}
		return user;
	
	
	}


	@Override
	public boolean invalidateToken(String token) throws Exception {

		boolean response = false;
		try {
			url = new URI(getTokenUrl);
			httpHeader.add("token", token);
			HttpEntity<?> entity = new HttpEntity<Object>(httpHeader);
			response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Boolean.class).getBody();
		}catch (HttpStatusCodeException exception) {
			int statusCode = exception.getStatusCode().value();
			if(statusCode == 401) {
				throw new UserNotFoundException("Invalid Access token: "+token);
			}else {
				throw new Exception("Internal Server Error");
			}
		}
		return response;	
	
	}

}
