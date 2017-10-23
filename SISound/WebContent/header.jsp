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
		
			<a href="main.jsp" id="homeLink">
				<div id="homeButton">Home</div>
			</a>
			
			<div class="dropdown">
			  <button class="dropbtn">
			  	<c:if test="${user.profilPicture != null}">
					<img class="profilePic" alt="profilePic" src="${user.profilPicture }">
					<span class="profileButton"><c:out value="${ user.username }"></c:out></span>
				</c:if>
				<c:if test="${user.profilPicture == null}">
					<img class="profilePic" alt="defaultProfilPic" src="defaultProfile.png">
					<span class="profileButton"><c:out value="${ user.username }"></c:out></span>
				</c:if>
			  </button>
			  <div class="dropdown-content">
			    <a href="profile.jsp">Profile</a>
			    <a href="upload.jsp">Upload</a>
			    <form action="LogOutServlet" method="post">
			    	<div id="signOutDiv"><input id="signOut" type="submit" value="Sign Out"></div>
			    </form>
			  </div>
			</div>
	</header>		
</body>
</html>