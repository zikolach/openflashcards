<%@page import="openflashcards.entity.User"%>
<%@page import="openflashcards.service.FlashcardsService"%>
<%@page import="openflashcards.entity.Translation"%>
<%@page import="java.util.List"%>
<%@page import="openflashcards.entity.Flashcard"%>
<%
{
	@SuppressWarnings("unchecked")
	List<String> filterTranslationLanguage =  (List<String>)request.getAttribute("filterTranslationLanguage");
	User user = FlashcardsService.getCurrentUser();
%>
<div class="accordion-group">
	<div class="accordion-heading">
		<button class="btn btn-block" type="button" data-toggle="collapse"
			data-parent="#accordion2" data-target="#collapseFour">Results
		</button>
	</div>
	<div id="collapseFour" class="accordion-body collapse in">
<%
	@SuppressWarnings("unchecked")
	List<Flashcard> flashcards = (List<Flashcard>) request.getAttribute("flashcards");
	if (flashcards == null) {
		%>Empty result<%
	}
	else
	{
%>
		<table class="table table-striped">
	<%
		
		for (Flashcard flashcard : flashcards) {
			List<Translation> translations = flashcard.getTranslations(filterTranslationLanguage);
			if (translations != null && translations.size() > 0) {

	%>
			<tr>
				<td>
					<b>
						<a href="/languages/<%=flashcard.getLanguage().getId() %>/flashcards/<%=flashcard.getId()%>">
							<%=flashcard.getId()%>
						</a>
					</b>
					<span class="language" title="<%=flashcard.getLanguage().getName()%>">
						<%=flashcard.getLanguage().getId()%>
					</span>
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
				</td>
				<td>
					<ol>
				<%
					for (Translation translation : translations) {
				%>
						<li><%=translation.getText()%> 
							<span class="language" title="<%=translation.getLanguage().getName()%>"><%=translation.getLanguage().getId()%></span>
						</li>
				<%
					}
				%>
					</ol>
				</td>
			</tr>
	<%
			}
		}
	%>
		</table>
<%
	}
%>
	</div>
</div>
<%
}
%>