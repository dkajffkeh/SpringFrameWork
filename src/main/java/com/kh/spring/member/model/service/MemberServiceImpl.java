package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service()
public class MemberServiceImpl implements MemberService {

	//의손성 주입 ==> 어노테이션으로 서비스단에 메소드가 실행 되는순간 spring 에서 실행함.
	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private MemberDao mDao;

	@Override
	public Member loginMember(Member m) {return mDao.loginMember(sqlSession,m);}

	@Override
	public int insertMember(Member m)   {return mDao.insertMember(sqlSession,m);}

	@Override
	public int updateMember(Member m) {return mDao.updateMember(sqlSession,m);}

	@Override
	public int deleteMember(Member m) {return mDao.deleteMember(sqlSession,m);}

	@Override
	public int idCheck(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
