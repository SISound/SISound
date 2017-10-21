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


	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="login.jsp"></c:redirect>
	</c:if>
	
	<c:set value="${ sessionScope.user}" var="user"></c:set>
		
	<jsp:include page="header.jsp"></jsp:include>
	
		<c:if test="${user.coverPhoto != null}">
			<h1> "${ user.CoverPhoto } </h1>
		</c:if>
		<c:if test="${user.coverPhoto == null}">
			<img class="coverPhoto" alt="defaultCoverPic" src="cover.jpg">
		</c:if>
		<c:if test="${user.profilPicture != null}">
			<img class="profilePicProfile" alt="profilePic" src="${ user.profilPicture }">
		</c:if>
		<c:if test="${user.profilPicture == null}">
			<img class="profilePicProfile" alt="defaultProfilPic" src="defaultProfile.png">
		</c:if>

		<p>		
			<h1 id="profileProperties">${ user.username }</h1>
			
			<br>
			<h1 id="profileProperties">${ user.firstName } ${ user.lastName }</h1>
			<br>
			${ user.country }
			${ user.city }
			<br>
			${ user.bio }
		</p>	

	<form class="navi" action="edit_profile.jsp">
		<input type="submit" value="Edit Profile">
	</form>
			
</body>
</html>