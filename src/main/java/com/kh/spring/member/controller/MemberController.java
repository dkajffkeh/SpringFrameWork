package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller() //빈으로 등록 까먹으면 안됨  //빈 스캐닝을 통해 자동으로 빈으로 등록이됨.
public class MemberController {
	
	@Autowired
	private MemberService mService;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
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
	/*
	 * @RequestMapping("login.me") public void loginMember(Member m, HttpSession
	 * session) {
	 * 
	 * //MemberService mService = new MemberServiceImpl(); //직접 객체 생성하는 순간 => 결합도가
	 * 높아지게됨. //결합도가 높을때 발생할 수 있는 문제 //==> 유지보수가 어려워짐, //==> 클래스 명이 변경됐을경우 ==> 에러발생
	 * //==>** 10000번 실행됐을시 10000번의 객체가 생성됨
	 * 
	 * //위의 문제점을 해소하고자 한다면 결합도를 낮춰야함!
	 * 
	 * //스프링컨테이너가 해당 객체를 관리할 수 있더록 빈으로 등록 //1.xml 등록 2.어노테이션 등록 //DI(의존성 주입을 통해서 생성된
	 * 객체 받아다 쓸것.) Member loginUser = mService.loginMember(m);
	 * 
	 * if(loginUser!=null) { session.setAttribute("loginUser", loginUser); } else {
	 * 
	 * }
	 * 
	 * // 포워딩방법.
	 * 
	 * }
	 */
	
	//1. Model 이라는 객체를 사용 Default Scope => request 응답 데이터를 맵 형식으로 담게됨. (addAttribute("key",value)
	//2. ModelAndView 객체 사용 포워딩할 뷰에 대한 정보를 담을 수 있는 공간.
	//   Model 과 View 객체를 합쳐놓은것. 응답페이지에 대한 정보도 담을 수 있는 공간
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session , ModelAndView mv) {
		
		
		Member loginUser = mService.loginMember(m); //db로 부터 아이디를 가지고 조회 했을때에 암호문이 담겨있음.
		
		// loginUser.getUserPwd() == 디비로부터 아이디를 가지고 조회 했을떄 암호문.
		//m.getUserPwd() == 로그인 요청시 입력했던 비밀번호 평문.
				
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { //성공
			
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
			
		}else { //실패
			
			mv.addObject("errorMsg", "로그인실패");
			mv.setViewName("common/errorPage");
			
		}
		//여기서 리턴된 문자열의 앞에서 /WEB-INF/views 가 붙고 뒤에는 .jsp가 붙어서 리턴됨
		return mv;
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		
		session.invalidate();

		return "redirect:/";
	}
	
	@RequestMapping("insert.me")
	public ModelAndView insertMember(Member m , ModelAndView mv, HttpSession session ) {
		
		m.setUserPwd(bcryptPasswordEncoder.encode(m.getUserPwd()));
		
		int result = mService.insertMember(m);
		
		if(result>0) {
			
			//포워딩으로 처리하는것은 url 을 바꿔치기 하기 위함 update 가 끝났으면 myPage.me 로 바껴야함.
			session.setAttribute("alertMsg", "회원가입에 성공하였습니다");
			mv.setViewName("redirect:/");
			
		} else {
			mv.addObject("errorMsg", "회원가입 실패");
			mv.setViewName("common/errorPage");
			
		}
		return mv;
	}
	@RequestMapping("update.me")
	public ModelAndView updateMember(Member m, ModelAndView mv , HttpSession session) {
		
		int result = mService.updateMember(m);
		
		if(result>0) {

			session.setAttribute("loginUser", mService.loginMember(m));
			mv.setViewName("redirect:myPage.me");
			
		} else {
			
			mv.addObject("errorMsg", "오류가 발생하였습니다");
			mv.setViewName("common/errorPage");
			
		}

		return mv;
	}
	
	@RequestMapping("delete.me")
	public String deleteMember(String userPwd , Model model ,HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");

		String encPwd = loginUser.getUserPwd(); //로그인한 회원의 암호문이 담겨있음
		
		if(bcryptPasswordEncoder.matches(userPwd, encPwd)){ //본인이 맞음
			
			int result = mService.deleteMember(loginUser);
			
			if(result>0) {
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "이용해주셔서 감사합니다");
				return "redirect:/";
				
			} else {
				model.addAttribute("errorMsg", "오류가 발생하였습니다.");
				return "common/errorPage";
			}
			
			
		} else { //본인이 아님			
			model.addAttribute("errorMsg","비밀번호가 틀렸습니다.");	
			return "common/errorPage";
		}
		
	}
	
	
	@RequestMapping("myPage.me")
	public String sendToMypage() {return "member/myPage";}
	@RequestMapping("enrollForm.me")
	public String sentToEnrollForm() {return "member/memberEnrollForm";}
	
}
