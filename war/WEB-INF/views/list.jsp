<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="openflashcards.entity.User"%>
<%@page import="openflashcards.service.FlashcardsService"%>
<%@page import="openflashcards.entity.Translation"%>
<%@page import="java.util.List"%>
<%@page import="openflashcards.entity.Flashcard"%>
<%
{
	@SuppressWarnings("unchecked")
	List<String> filterTranslationLanguage =  (List<String>)request.getAttribute("filterTranslationLanguage");
	@SuppressWarnings("unchecked")
	List<String> filterTranslationTag =  (List<String>)request.getAttribute("filterTranslationTag");
	Integer filterLimit = (Integer)request.getAttribute("filterLimit");
	User user = FlashcardsService.getCurrentUser();
%>

<%
	@SuppressWarnings("unchecked")
	List<Flashcard> flashcards = (List<Flashcard>) request.getAttribute("flashcards");
	if (flashcards == null) {
		%>Empty result<%
	}
	else
	{
		int count = 0;
		for (Flashcard flashcard : flashcards) {
			if (count >= filterLimit) break;
			List<Translation> translations = flashcard.getTranslations(filterTranslationLanguage, filterTranslationTag);
			if (translations != null && translations.size() > 0) {
				count++; %>
			<div class="flashcard">
				<div class="controls vertical">
					<% if (user != null) {
						String sel = (user.findFlashcard(flashcard.getId(), flashcard.getLanguage().getId()) == null) ? "like" : "dislike"; %>
					<div class="action <%= sel %>" data-url="/users/<%= FlashcardsService.getCurrentUser().getId() %>/languages/<%=flashcard.getLanguage().getId() %>/flashcards/<%=flashcard.getId() %>"></div>
					<% } %>
					<div class="action view" data-url="/languages/<%= flashcard.getLanguage().getId() %>/flashcards/<%= flashcard.getId() %>"></div>
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
	<% } } } %>

<%
}
%>
