<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/menubar.jsp"/>
    
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="insert.me" method="post" onsubmit="" id="enrollForm">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userId" name="userId" placeholder="Please Enter ID" required>
                    <div id="check-result" style="font-size:0.8rem; display:none;"></div>
                    <br>
                    
                    <label for="userPwd">* Password :</label>
                    <input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="Please Enter Password" required>
                    <br>
                    
                    <label for="checkPwd">* Password Check :</label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required>
                    <br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="Please Enter Name" required>
                    <br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Please Enter Email"><br>
                    
                    <label for="age"> &nbsp; Age :</label>
                    <input type="number" class="form-control" id="age" name="age" placeholder="Please Enter Age"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Please Enter Phone (-없이)"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Please Enter Address"><br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button id="enroll-btn" type="submit" class="btn btn-primary" disabled>회원가입</button>
                    <button type="reset" class="btn btn-danger"> 초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>
    
    <script>
    	$(function(){
    		
    		let idInput = $("#enrollForm input[name=userId]"); //아이디 입력하는 input 요소 객체
    		
    		idInput.keyup(function(){
    			
    			if(idInput.val().length>=5){
    				
    				$.ajax({
    					url:"idCheck.me",
    					type:"post",
    					data:{
    						userId:idInput.val()
    					},
    					success:function(result){
    						
    						if(result == 1 ){
    							
    							$("#check-result").show();
    							$("#check-result").css('color','red').text("중복된 아이디가 존재합니다.");
    							$("#enroll-btn").attr('disabled',true);							
    						} else {
    							//사용 가능					
    							$("#check-result").show();
    							$("#check-result").css('color','lightgreen').text("그게 아이디냐 이 새끼야?");
    							$("#enroll-btn").removeAttr('disabled');
    						}					
    					},
    				});
    				
    			} else {
    				
    				$("#check-result").hide();
    				$("#enroll-btn").attr('disabled',true);
    				
    			};
    			
    			
    		});
    	});
    </script>
    
</body>
</html>
