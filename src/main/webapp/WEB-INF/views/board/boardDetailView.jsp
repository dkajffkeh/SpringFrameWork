<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <style>
  	    table *{margin:5px;}
        table{width:100%;}
    </style>
</head>
<body>
    <!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/menubar.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>
            
            
            <a class="btn btn-secondary" style="float:right" href="list.bo">목록으로</a>
            <br><br>
            <table id="contentArea" align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ b.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    <c:choose>
                    	<c:when test="${ empty b.originName }">
                    	첨부파일이 없습니다.
                    	</c:when>
                    	<c:otherwise>
                        <a href="${ b.changeName }" download="${ b.originName }">${ b.originName }</a>
                        </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px">${ b.boardContent }</p></td>
                </tr>
            </table>
            <br>

            <div align="center">
                <!-- 수정하기, 삭제하기 버튼은 이글이 본인글일 경우만 보여져야됨 -->
               <c:if test="${ b.boardWriter eq loginUser.userId }"> 
                <a class="btn btn-primary" onclick="postFormSubmit(1);">수정하기</a>
                <a class="btn btn-danger" onclick="postFormSubmit(2);">삭제하기</a>
               </c:if> 
               
               <form action="" method="post" id="postForm">
               		<input type="hidden" name="bno" value="${ b.boardNo }">
               		<input type="hidden" name="fileName" value="${ b.changeName }"> 
               </form>
            </div><br><br>
            
            <script>
            	function postFormSubmit(num){
         		
            		let url = "";
            		if(num==1){ //수정하기 클릭
            			url="updateForm.bo";
            		} else if(num==2) { //삭제하기 클릭
            			url="delete.bo";
            		}
            		
            		$("#postForm").attr("action",url).submit();   		
            	}
            </script>

            <!-- 댓글 기능은 나중에 ajax 배우고 접목시킬예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%"></textarea>
                        </th>
                        <th style="vertical-align: middle"><button class="btn btn-secondary">등록하기</button></th>
                    </tr>
                    <tr>
                       <td colspan="3">댓글 (<span id="rcount">3</span>) </td> 
                    </tr>
                </thead>
                <tbody>
                
                </tbody>
            </table>
        </div>
        <br><br>
    </div>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>
    
    <script>
    	$(function(){
    		
    		selectComments();
    		
    	});
    	
		
		function selectComments(){
			
			$.ajax({
				url:"selectComment.bo",
				data:{bno:${b.boardNo}},
				success:function(cList){				
					let result = "";
					
					for(let i = 0; i<cList.length; i++){
						result+=`
								<tr>
									<td>\${cList[i].replyWriter}</td>
									<td>\${cList[i].replyContent}</td>
									<td>\${cList[i].createDate}</td>
							   </tr>
						`
					}
					$("#replyArea > tbody").html(result);
					
				}    				
				
			});
			
		}
    </script>
    
</body>
</html>