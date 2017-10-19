<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		<div id="loginDiv">
			<form action="loginServlet" method="POST">
				<div id="liDiv">
					<input class="login" type="text" placeholder="Username" name="username"><br>
					<input class="login" type="password" placeholder="Password" name="password"><br>
					<input id="lb" type="submit" value="" style="background-image: url('login.png'); border:none; background-repeat:no-repeat;background-size:100% 100%;">
				</div>
			</form>
			<a class="regLink" href="register.jsp" name="regLink">You don't have account? Register here!</a>
		</div>
	</body>
</html>