<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="openflashcards.service.FlashcardsService"%>
<%@page import="openflashcards.entity.User"%>
<%@page import="openflashcards.entity.Translation"%>
<%@page import="java.util.List"%>
<%@page import="openflashcards.entity.Flashcard"%>
<%
{
	@SuppressWarnings("unchecked")
	List<String> filterTranslationLanguage =  (List<String>)request.getAttribute("filterTranslationLanguage");
	User user = FlashcardsService.getCurrentUser();
	
%>

	<%
		Flashcard flashcard = (Flashcard)request.getAttribute("flashcard");
		if (flashcard != null) {
			List<Translation> translations = flashcard.getTranslations(filterTranslationLanguage);
	%>
	<div class="flashcard">
		<div class="controls">
		</div>
		<div class="word">
			<%=flashcard.getId()%>
			<div class="language"><%=flashcard.getLanguage().getId()%></div>
			
		</div>
		<% for (Translation translation : translations) { %>
		<div class="translation">
			<%=translation.getText()%>
			<span class="language"><%=translation.getLanguage().getId()%></span>
		</div>
		<% } %>
	</div>
	<% } else { %>
	Empty
	<% } %>

<%
}
%>
