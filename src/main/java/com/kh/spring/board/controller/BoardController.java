package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;
	
	@RequestMapping("list.bo")
	public String sendToBoardDetail(@RequestParam(value="currentPage",defaultValue="1") int currentPage,Model model) 
	{
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectBoardList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("bList", list);
		
		return "board/boardListView";
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile , HttpSession session , Model model) {
		
		//전달된 파일이 있을 경우 => 서버에 업로드 해야함.
		if(!upfile.getOriginalFilename().equals("")) {
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName(saveFile(upfile , session));
		
		}	
		int result = bService.insertBoard(b);
		
		if(result>0) {
			session.setAttribute("alertMsg", "게시글이 등록되었습니다");
			return "redirect:list.bo";
		} else {
			model.addAttribute("errorMsg", "작성에 실패하였습니다.");
			return "common/errorPage";
		}
	
	}
	
	@RequestMapping("detail.bo")
	public String sendToDetailView(int bno , Model model) {
		
	int result = bService.increaseCount(bno);	
	
	if(result>0) {//진행함.
		
		Board b = bService.selectBoard(bno);
		
		model.addAttribute("b", b);
		return "board/boardDetailView";
		
	} else {//조회가 불가한 게시글
		model.addAttribute("errorMsg", "잘못된 게시글입니다");
		return "common/errorPage";
	}
	
	}
	@RequestMapping("delete.bo")
	public String delteBoard(int bno , String fileName,HttpSession session , Model model) {
		
		int result = bService.deleteBoard(bno);
		if(result>0) {//기존 파일 찾아서 삭제 해아함. => 게시글 리스트 페이지 재요청
			
			if(!fileName.equals("")) {//파일 존재
				new File(session.getServletContext().getRealPath(fileName)).delete();	
			}
			
			session.setAttribute("alertMsg", "삭제 완료되었습니다");
			return "redirect:list.bo";
		} else {
			model.addAttribute("errorMsg", "오류가 발생하였습니다");
			return "common/errorPage";
		}	
		
	}
	@RequestMapping("updateForm.bo")
	public String updateBoard(int bno, Model model) {
			
		model.addAttribute("b", bService.selectBoard(bno));
		
		return "board/boardModifyForm";
	}
	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session , Model model) {
		
		
		if(!reupfile.getOriginalFilename().equals("")) { //새로 전돨된 첨부파일이 있는경우
			
			//만약에 기존에 첨부파일이 또 있는경우 => 바꿔치기 & 삭제
			if(b.getOriginName()!=null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}	
		 //새로 전달된 첨부파일 => 업로드
			
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName(saveFile(reupfile,session));
	
		}
		
		int result = bService.updateBoard(b);
		if(result>0) { //게시글 수정 성공 => 상세보기로 포워딩 
			
			session.setAttribute("alertMsg", "등록 성공");
			return "redirect:detail.bo?bno="+b.getBoardNo();
		} else { //게시글 수정 실패=>
			model.addAttribute("errorMsg", "게시글 수정 실패");
			return "common/errorPage";
			
		}
		
		
	}
	@ResponseBody
	@RequestMapping(value="selectComment.bo", produces="application/json; charset=utf-8")
	public String selectComment(int bno) {
		
		return new Gson().toJson(bService.selectComments(bno));
	}
	
		
	//fileRename
	public String saveFile(MultipartFile upfile , HttpSession session) {
	
	//파일 업로드 시킬 폴더의 물리적인 경로(savePath)
	String savePath = session.getServletContext().getRealPath("resources/uploadFiles/");
	//어떤 이름으로 업로드 할건지의 수정명 작업.
	String originName = upfile.getOriginalFilename();
	
	String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	
	int random = (int)(Math.random()*90000+10000);
	String ext = originName.substring(originName.lastIndexOf("."));
	
	String changeName = currentTime + random + ext;
	
	try {
	
		upfile.transferTo(new File(savePath + changeName));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return "resources/uploadFiles/"+changeName;

	}
	
	//게시판 등록 포워딩 페이지
	@RequestMapping("enrollForm.bo")
	public String sendToEnrollForm() {return "board/boardEnrollForm";}
	//게시판 상세보기 페이지로 이동
	
	
}
