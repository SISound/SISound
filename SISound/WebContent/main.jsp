<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="style.css" media="screen">
		<title>SISound</title>
	</head>
	
	<body id="mainBody">
	
		<c:if test="${ sessionScope.user == null }">
				<c:redirect url="login.jsp"></c:redirect>
		</c:if>
		
		<c:set value="${ sessionScope.user}" var="user"></c:set>
		 
		<jsp:include page="header.jsp"></jsp:include>
		
		<h2 id="topH">TOP10 CHART</h2>
		
		<div id="sortHeader"><p>SortBy:|</p></div>
		
		<a style="display:block" href="SortingServlet?sorter=likes"><div id="sortByLikes">Likes|</div></a>
		<a style="display:block" href="SortingServlet?sorter=shares"><div id="sortByShares">Shares|</div></a>
		<a style="display:block" href="SortingServlet?sorter=listenings"><div id="sortByListenings">Listenings|</div></a>
		
		<table class="songs">
			<c:forEach items="${applicationScope.songs}" var="song">
				<tr class="songTr">
					<!-- SONG'S USER -->
					<td class="songTd">
						<c:if test="${song.user.profilPicture != null}">
							<img class="songsPRPic" alt="profilePic" src="${user.profilPicture }">
						</c:if>
						<c:if test="${song.user.profilPicture == null}">
							<img class="songsPRPic" alt="defaultProfilPic" src="defaultProfile.png">
						</c:if>
						<c:out value="${ song.user.username }"></c:out>
					</td>
					
					<!-- SONG TITLE -->
					<td class="songTd"><c:out value="${ song.title }"></c:out></td>
					
					<!--FOLLOW BUTTON -->
					<td class="songTd"><c:out value="follow"></c:out></td>
					
					<!-- PLAYER -->
					<td id="playerTd">
					    <audio id="player" controls>
						    <source src="SongServlet?songName=${song.title}" type="audio/mpeg">
						</audio>
					</td>
					
					<!-- ACTIONS -->
				</tr>
				<tr class="actions">
					<td>LIKE</td>
					<td>DISLIKE</td>
					<td>SHARE</td>
					<td>COMMENT</td>
				</tr>
			</c:forEach>
		</table>	
		
		<footer id="homeFooter">
			<p>Footerche</p>
		</footer>
	</body>
</html>