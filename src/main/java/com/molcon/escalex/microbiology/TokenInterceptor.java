package com.molcon.escalex.microbiology;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.molcon.escalex.microbiology.services.LoginService;

@Component
public class TokenInterceptor implements HandlerInterceptor{

	
	
	@Autowired
	LoginService loginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//return checkToken.testClient(request.getHeader("token"));
		return loginService.checkToken(request.getHeader("token"));
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		/*
		 * 
		 */
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	/*
	 * 
	 */
		
	}

	

}
