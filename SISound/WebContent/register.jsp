<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="style.css">
		<title>SISound</title>
	</head>
	
	<body>
		<header id="loginHeader">
			<h1>LOGO</h1>
		</header>
		
		<div id="registerDiv">
			<form action="register" method="POST">
				<div id="regDiv">
					<c:if test="${ requestScope.error == null }">
						<a class=regMessage>Please register an account</a>
					</c:if>
					<c:if test="${ requestScope.error != null }">
						<a class=regMessage>Sorry, registration unsuccessfull. Reason: ${ requestScope.error }</a>
					</c:if>
					<input class="login" type="text" placeholder="Username" name="username"><br>
					<input class="login" type="password" placeholder="Password" name="password"><br>
					<input class="login" type="password" placeholder="Repeat password" name="password2"><br>
					<input class="login" type="email" name="email" placeholder="e-mail"><br>
					<input id="rb" type="submit" value="" style="background-image: url('signup.png'); border:none; background-repeat:no-repeat;background-size:100% 100%;">
				</div>
			</form>	
			<a class="logLink" href="login.jsp" name="regLink">Already have an account? Please login here.</a>
		</div>
	</body>
</html>