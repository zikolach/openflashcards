<%@page import="openflashcards.entity.Translation"%>
<%@page import="openflashcards.entity.Flashcard"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<%@include file="head.jsp"%>
<body>
	<%@include file="header.jsp"%>
	<%
	{
		User user = FlashcardsService.getCurrentUser();
	%>
	<div class="container">
		<div class="row">
			<div class="span12 flashcard">
			<%
				Flashcard flashcard = (Flashcard)request.getAttribute("flashcard");
			%>
				<div class="word">
					<%= flashcard.getId() %>
					<span class="language"><%= flashcard.getLanguage().getId() %></span>
				</div>
			<% 
				if (user != null && user.findFlashcard(flashcard.getId(), flashcard.getLanguage().getId()) == null) { 
			%>
			<span class="btn btn-mini like">
				<a class="icon-star" href="/users/<%= FlashcardsService.getCurrentUser().getId() %>/languages/<%=flashcard.getLanguage().getId() %>/flashcards/<%=flashcard.getId() %>">
					&nbsp;
				</a>
			</span>
			<%
			}
			%>	
			
			<%
				for (Translation translation : flashcard.getTranslations()) {
			%>
				<div class="translation">
					<%= translation.getText() %>
					<span class="language"><%= translation.getLanguage().getId() %></span>
				</div>
			<%
				}
			%>
			</div>
		</div>
	</div>
	<%
	}
	%>
	<script type="text/javascript" src="/js/flashcards.js"></script>
</body>
</html>