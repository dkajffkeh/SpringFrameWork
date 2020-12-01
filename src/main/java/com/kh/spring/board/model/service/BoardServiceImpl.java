package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private BoardDao bDao;
	
	@Override
	public int selectListCount() {return bDao.selectListCount(sqlSession);}

	@Override
	public ArrayList<Board> selectBoardList(PageInfo pi) {return bDao.selectBoardList(sqlSession,pi);}

	@Override
	public int insertBoard(Board b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int increaseCount(int bno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Board selectBoard(int bno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateBoard(Board b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoard(int bno) {
		// TODO Auto-generated method stub
		return 0;
	}

}