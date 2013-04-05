<%@page import="openflashcards.entity.Tag"%>
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
		<div class="word">
			<%=flashcard.getId()%>
			<div class="language"><%=flashcard.getLanguage().getId()%></div>
			
		</div>
		<% for (Translation translation : translations) { %>
		<div class="translation closed">
			<%=translation.getText()%>
			<span class="language"><%=translation.getLanguage().getId()%></span>

			<div class="panel-translation-edit">
				<div class="tags">
				<% for (Tag tag : translation.getUserTags(user)) { %>
					<div class="tag" data-url="/users/<%= user.getId() %>/tags/<%= tag.getId() %>"> <%= tag.getTagName() %> <div class="btn-tag-remove"></div></div>
			 	<% } %>
			 	</div>
			 	<form class="form-inline" method="post" action="/users/<%= user.getId() %>/tags">
			 		<input type="hidden" name="language" value="<%= flashcard.getLanguage().getId() %>">
			 		<input type="hidden" name="flashcard" value="<%= flashcard.getId() %>">
				 	<input type="hidden" name="translation" value="<%= translation.getId() %>">
				 	<input type="text" name="tagName" class="input-large" placeholder="Enter new tag...">
					<button type="submit" class="btn btn-primary">Add tag</button>
				</form>
			</div>

		</div>
		<% } %>
		
		<div class="controls horizontal">
			<div class="action tags"></div>
		</div>
		<div id="tags-form">
		</div>
	</div>
	<% } else { %>
	Empty
	<% } %>

<%
}
%>
