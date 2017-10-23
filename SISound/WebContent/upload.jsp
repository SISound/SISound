<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="style.css" media="screen">
	<title>SISound</title>
</head>
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		
		<c:if test="${ sessionScope.user == null }">
			<c:redirect url="login.jsp"></c:redirect>
		</c:if>
		
		<div id="uploadDiv">
			<form id="uploadForm" action="UploadSongServlet" method="post" enctype="multipart/form-data">
			    <input class="uploader" type="file" name="song" /></br>
			    <input class="uploader" type="submit" /></br>
			</form>
		</div>
	</body>
</html>