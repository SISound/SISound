<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<header>
		<input id="searchBar" type="text" placeholder="search artists, songs, playlists" placeholder-style: font>
			
			<a href="login" class="homeButton_headerLink">
				<span class="homeButton">Home</span>
			</a>
			
			<table class="profilTable">
				<td>
					<c:if test="${user.profilPicture != null}">
						<img class="profilePic" alt="profilePic" src="${user.profilPicture }">
					</c:if>
					<c:if test="${user.profilPicture == null}">
						<img class="profilePic" alt="defaultProfilPic" src="defaultProfile.png">
					</c:if>
				</td>
				<td>
					<a href="profile.jsp" class="profileButton_headerLink">
						<span class="profileButton"><c:out value="${ user.username }"></c:out></span>
					</a>
				</td>
			</table>
			<form action="LogOutServlet" method="post">
				<input class="logoutButton" type="submit" value="Logout">
			</form>
	</header>		
</body>
</html>