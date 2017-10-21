<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="style.css">
		<title>SISound</title>
	</head>
	<body>
		<c:if test="${ sessionScope.user == null }">
				<c:redirect url="login.jsp"></c:redirect>
		</c:if>
		
		<c:set value="${ sessionScope.user}" var="user"></c:set>
		
		<header>
			<input id="searchBar" type="text" placeholder="search artists, songs, playlists" placeholder-style: font>
			
			<a href="main.jsp" class="homeButton_headerLink">
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
			<form action="LogOutServlet" method="GET">
				<input class="logoutButton" type="submit" value="Log out">
			</form>
		</header>
		
		<table class="songs">
			<tr>
				<th>Id</th>
				<th>Title</th>
				<th>uploadDate</th>
				<th>timesListened</th>
			</tr>
			<c:forEach items="${applicationScope.songs}" var="song">
				<tr>
					<td>${ song.id }</td>
					<td><c:out value="${ song.title }"></c:out></td>
					<td><c:out value="${ song.uploadDate }"></c:out></td>
					<td><c:out value="${ song.timesListened }"></c:out></td>
				</tr>
			</c:forEach>
		</table>
         
	</body>
</html>