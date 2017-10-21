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
		
		<jsp:include page="header.jsp"></jsp:include>
				
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