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
		<header>
		
			<input id="searchBar" type="text" placeholder="search artists, songs, playlists" placeholder-style: font>
			
			<form action="login.jsp" method="GET">
				<input id="loginButton" type="submit" value="" style="background-image: url('login.png'); border:none; background-repeat:no-repeat;background-size:100% 100%;">
			</form>
			
			<form action="signup.jsp" method="GET">
				<input id="signupButton" type="submit" value="" style="background-image: url('signup.png'); border:none; background-repeat:no-repeat;background-size:100% 100%;">
			</form>
			
		</header>
		
		<div>
			<img id="djDiv" src="djImg.jpg" alt="Mountain View">
		</div>
		
		<footer id="homeFooter">
			<p>Footerche</p>
		</footer>
	</body>
</html>