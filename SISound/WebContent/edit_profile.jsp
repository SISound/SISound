<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="styles.css">
		<title>SISound</title>
	</head>
	<body>
		<c:if test="${ sessionScope.user == null }">
		<c:redirect url="login.jsp"></c:redirect>
	</c:if>
	
	<c:set value="${ sessionScope.user}" var="user"></c:set>
		
	<jsp:include page="header.jsp"></jsp:include>
		
		<div id="editProfileDiv">
			<form action="profile/edit_profile" method="POST">
				<div id="editDiv">
					<c:if test="${ requestScope.error == null }">
						<a class=editMessage>Edit profile</a><br>
					</c:if>
					
					<c:if test="${ requestScope.error != null }">
						<a class=editMessage>Sorry, editing profile unsuccessfull. Reason: ${ requestScope.error }</a>
					</c:if>
				
					<input class="edit" type="Text"  placeholder="First name" name="firstName"><br>
					<input class="edit" type="Text"  placeholder="Last name" name="lastName"><br>
					<input class="edit" type="Text"  placeholder="Your description" name="description"><br>
	<!-- 				country -->
					<input class="edit" type="Text"  placeholder="City" name="city"><br>
					
					<a class="editText">Change profile picture.</a>
					<input type="file" name="profilePic"><br>
					<a class="editText">Change cover photo.</a>
					<input type="file" name="coverPhoto"><br>
					
					<a class="editText">Change password.</a><br>
					<input class="edit" type="password" placeholder="Current password" name="oldPassword"><br>
					<input class="edit" type="password" placeholder="New password" name="newPassword"><br>
					<input class="edit" type="password" placeholder="Repeat new password" name="newPassword2"><br>
					<a class="editText">Change e-mail.</a><br>						
					<input class="edit" type="email"  placeholder="Current e-mail" name="oldEmail"><br>
					<input class="edit" type="email"  placeholder="New e-mail" name="newEmail"><br>
					<input class="edit" type="email"  placeholder="Repeat new e-mail" name="newEmail2"><br>
					
					
					<input id="rb" type="submit" value="" style="background-image: url('signup.png'); border:none; background-repeat:no-repeat;background-size:100% 100%;">
				</div>
			</form>	
		</div>
		
		
		
	</body>
</html>