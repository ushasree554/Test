/*package com.molcon.escalex.microbiology;

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
import org.springframework.web.client.RestTemplate;

import com.molcon.escalex.microbiology.exception.TestErrorHandler;

@Component
public class CheckToken {
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Value("${oauthresource.clientId}")
	  private String clientId;

	  @Value("${oauthresource.clientSecret}")
	  private String clientSecret;

	  @Value("${oauthresource.checkTokenUrl}")
	  private String checkTokenUrl;
	
	private HttpHeaders httpHeader;
	
	private URI url;
	
	@PostConstruct
	public void init() throws URISyntaxException {	
		httpHeader = new HttpHeaders();
		String plainCreds = this.clientId+":"+this.clientSecret;
		String baseUrl = this.checkTokenUrl;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);	
		httpHeader.add("Authorization", "Basic " + base64Creds);
		httpHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeader.add("Accept", MediaType.APPLICATION_JSON.toString());		
		url = new URI(baseUrl);		
	  }

	public boolean testClient(String token) {	
		restTemplate.setErrorHandler(new TestErrorHandler());
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("token", token);		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeader);
		restTemplate.exchange(url, HttpMethod.POST, request, Object.class);	
		return true;
	}
	
}
*/