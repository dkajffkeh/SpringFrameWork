package com.kh.spring.common.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 * Interceptor 사용 예 => 로그인 기능 구현할때 사용
 * 로그인 기능 구현시 사용 (회원만 요청가능한 페이지일 경우 해당 사용자가 회원인지/비회원인지 파악 후 제어)
 * 로그인한 사용자의 권한을 체크
 * 
 */

public class LoginIntercepter extends HandlerInterceptorAdapter {

	@Override //실행 시점 => url 요청시 Handler매핑을 통해 컨트롤러로 가기 직전에 실행됨.
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		//리턴값에 따라 기존의 그 요청 흐름대로 Controller 구동시키겠다라는걸 의미
		//false 리턴될 경우 컨트롤러로 리턴을 막겠다.
		//true => 컨트롤러 구동 시킴.
		
		//로그인 되어있지 않을경우 => 서비스 진행을 막음. 메인페이지 요청
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser")==null){
			
			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스 입니다.");
			response.sendRedirect(request.getContextPath());
			
			return false;
		} else {
			return true;
		}
		// 현재 로스인한 사용자만 => 기존의 흐름대로 진행되게끔함.
		
		//이렇게 작성이 완료 됐으면 Intercepter를 등록을 해야함. ==> servlet-context.xml 에 등록함.
	}
	
}
