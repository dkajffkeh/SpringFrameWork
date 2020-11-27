package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller() //빈으로 등록 까먹으면 안됨  //빈 스캐닝을 통해 자동으로 빈으로 등록이됨.
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	// *요청시 전달값(파라미터) 를 뽑는방법 처'리방법
	// 1. HttpServletRequest 객체를 통해서 전송받기 (기존의 jsp/servlet 때의 방식) 
	// 2. 어노테이션 이용 RequestParam 어노테이션
	// 3. @RequestParam 어노테이션 생략하는 방법  name 과 자바 변수명이 같게끔
	// 4. 들어오자마자 맴버객체에 담는방법.
   /*
	@RequestMapping("login.me") //HandlerMapping 으로 등록
	public void loginMember(HttpServletRequest request, HttpServletResponse response) {		
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");	
	}
	*/
	/*
	 * @RequestMapping("login.me") public void
	 * loginMember(@RequestParam(value="id")String userId,
	 * @RequestParam(value="pwd")String userPwd) {
	 * System.out.println(userId + " " + userPwd);
	 * }
	 */

	/*
	 * @RequestMapping("login.me") public void loginMember(String id, String pwd) {
	 * 
	 * Member m = new Member(); m.setUserId(id); m.setUserPwd(pwd);
	 * 
	 * }
	 */
	
	/*
	 * 4.바로 맴버객체의 필드에 담는방법
	 *   커멘드 객체 방식이라고 얘기함
	 *   setter 메소드로 값을 주입해줌
	 *   (반드시 name 속성값을 필드명으로 제시해야함)
	 */	 
	@RequestMapping("login.me")
	public void loginMember(Member m) {
		
		//MemberService mService = new MemberServiceImpl();
		//직접 객체 생성하는 순간  => 결합도가 높아지게됨.
		//결합도가 높을때 발생할 수 있는 문제 
		//==> 유지보수가 어려워짐,
		//==> 클래스 명이 변경됐을경우 ==> 에러발생
		//==>** 10000번 실행됐을시 10000번의 객체가 생성됨
		
		//위의 문제점을 해소하고자 한다면 결합도를 낮춰야함!
		
		//스프링컨테이너가 해당 객체를 관리할 수 있더록 빈으로 등록
		//1.xml 등록 2.어노테이션 등록
		//DI(의존성 주입을 통해서 생성된 객체 받아다 쓸것.)
		Member loginUser = mService.loginMember(m);
		
		
		
	}
	
	
}
