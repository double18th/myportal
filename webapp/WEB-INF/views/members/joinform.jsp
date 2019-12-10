<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원 가입폼</title>
	<!-- jQuery -->
	<script src="<c:url value="/assets/jquery/jquery-3.4.1.min.js"/>">
	</script>
</head>
<body>
	<h1>회원 가입</h1>
	<!-- TODO: 가입 버튼을 누르면 registerForm 안에 있는 값을 검증 -->
	
	<c:url value="/members/join" var="joinUrl" />
	<form:form
		modelAttribute="memberVo"
		id="join-form"
		name="registerForm" 
		action="${joinUrl}"
		method="POST"
		>		
		
		<label for="name">이름</label>
		<form:input path="name" type="text" placeholder="이름을 입력하십시오" />
		<!-- MemberVo 객체 내 에러 여부 확인 -->
		<spring:hasBindErrors name="memberVo">
			<!-- 필드 에러 확인 -->
			<c:if test="${errors.hasFieldErrors('name') }">
				<span style="color:red">
				<!-- 에러 중 첫번째 것만 출력 -->
				<spring:message 
					code="${errors.getFieldError('name').codes[0] }"					
					text="${errors.getFieldError('name').defaultMessage}" />				
				</span>
			</c:if>
		</spring:hasBindErrors>
		<br>
	
		<label for="password">비밀번호</label>
		<form:input path="password" type="password" 
					placeholder="비밀번호를 입력하십시오"/>
		<spring:hasBindErrors name="memberVo">
			<!-- 필드 에러 확인 -->
			<c:if test="${errors.hasFieldErrors('password') }">
				<span style="color:red">
				<!-- 에러 중 첫번째 것만 출력 -->
				<spring:message 
					code="${errors.getFieldError('password').codes[0] }"					
					text="${errors.getFieldError('password').defaultMessage}" />				
				</span>
			</c:if>
		</spring:hasBindErrors>
		<br>
	
		<label for="email">이메일</label>
		<form:input type="text" class="form-control mb-2" path="email" placeholder="이메일을 입력하십시오."/>
		<button id="btnCheckEmail" type="button">이메일 중복 체크</button>
		<spring:hasBindErrors name="memberVo">
			<!-- 필드 에러 확인 -->
			<c:if test="${errors.hasFieldErrors('email') }">
				<span style="color:red">
				<!-- 에러 중 첫번째 것만 출력 -->
				<spring:message 
					code="${errors.getFieldError('email').codes[0] }"					
					text="${errors.getFieldError('email').defaultMessage}" />				
				</span>
			</c:if>
		</spring:hasBindErrors>
		<br>
	
		<label for="gender">성별</label>
		<input type="radio" name="gender" value="M" checked>남성</radio>
		<input type="radio" name="gender" value="F">여성</radio><br>
		<input type="submit" value="전송"> 
	
	</form:form>
	
</body>
<script>
$(document).ready(function() {
	// jQuery 초기화
	// #btnCheckEmail에 click 이벤트 부착
	$("#btnCheckEmail").on("click", function(event){
		var email = document.registerForm.email.value.trim();
		//alert("Email:" + email);
		// 1차 검증 (비어있는지 확인)
		if (email.length == 0) {
			alert("이메일을 입력해주세요");
			return;
		}
		//Ajax Call
		$.ajax({
			url: "<c:url value="/members/checkEmail"/>",
			type: "GET",
			data: {email: email},
			dataType: "json",
			success: function(result) {
				console.log("Result:", result);
				// result.exists를 검증, 메시지를 출력
				if (result.exists) {
					// 중복된 이메일
					alert("이미 가입된 이메일입니다");
				} else {
					alert("사용 가능한 이메일입니다");
				}
			},
			error: function(request, status, error) {
				console.error("Error:", error);
			}
		});
	});
});

</script>
</html>