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
		<c:if test="${ requestScope.error != null }">
			<div id="loginErr">
				<h1>Sorry, username or password is incorrect. Reason: ${ requestScope.error }</h1>
			</div>
		</c:if>
		
		<header>
			
		</header>
		
		<div id="loginDiv">
			<form action="login" method="POST">
				<div id="liDiv">
					<h1 id="loginHead">Login</h1>
					<input class="login" type="text" placeholder="Username" name="username"><br>
					<input class="login" type="password" placeholder="Password" name="password"><br>
					<input id="lb" type="submit" value="" style="background-image: url('login.png'); border:none; background-repeat:no-repeat;background-size:100% 100%;">
				</div>
			</form>
			<a class="forgotPass" href="forgotPass.jsp" name="forgotPass">Forgot password?</a>
			<a class="regLink" href="register.jsp" name="regLink">You don't have account? Register here!</a>
		</div>
	</body>
</html>