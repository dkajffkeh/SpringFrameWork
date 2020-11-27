package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	// 1. 로그인용 서비스
	Member loginMember(Member m);
	
	// 2. 회원가입용 서비스
	int insertMember(Member m);
	
	// 3. 회원정보 수정용 서비스
	int updateMember(Member m);
	
	// 4.회원 탈퇴용 서비스
	int deleteMember(Member m);
	
	// 5.아이디 중복체크용 서비스 (ajax 를 통한 검사.)
	int idCheck(String userId);
	
	
}
