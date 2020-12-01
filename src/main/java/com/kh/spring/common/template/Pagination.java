package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {
	
	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit , int boardLimit) {
		
		//* maxPage   : listCount , boardLimit 의 영향을 받음
		int maxPage = (int)Math.ceil((double)listCount/boardLimit);
		//* startPage : currentPage , pageLimit 의 영향을 받는다
		int startPage = (currentPage -1) / pageLimit * pageLimit + 1;
		//* endPage   : startPage , pageLimit .. ? 
		int endPage  = startPage + pageLimit - 1;
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		return new PageInfo(listCount,currentPage,startPage,endPage,maxPage,pageLimit,boardLimit);
	}

}
