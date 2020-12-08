package com.kh.spring.board.model.vo;

import lombok.Data;

@Data
public class Reply {

	private int replyNo;
	private String replyContent;
	private int regBno;
	private String replyWriter;
	private String createDate;
	private String status;
	
}
