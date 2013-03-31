<%@page import="openflashcards.service.FlashcardsService"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="openflashcards.entity.Language"%>
<% { %>
<div class="accordion-group">

	<div class="accordion-heading">
		<button class="btn btn-block" type="button" data-toggle="collapse"
			data-parent="#accordion1" data-target="#collapseTwo">Filter
			view</button>
	</div>
	<div id="collapseTwo" class="accordion-body collapse in">
		<div class="accordion-inner">

			<form class="form-vertical" action="/flashcards" method="get">
				<div class="control-group">
					<label class="control-label" for="filterWord">Word</label>
					<div class="controls">
						<% String filterWord = request.getParameter("filterWord"); %>
						<input type="text" name="filterWord" id="filterWord" placeholder="Word"
							class="input-xlarge" value="<%=filterWord != null ? filterWord : "" %>" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="filterWordLanguage">Language</label>
					<div class="controls">
						<select name="filterWordLanguage" id="filterWordLanguage"
							multiple="multiple">
							<%
								@SuppressWarnings("unchecked")
								List<String> filterWordLanguage = (List<String>)request.getAttribute("filterWordLanguage");
								for (Language lang : FlashcardsService.getLanguages()) {
							%>
							<option value="<%=lang.getId()%>"
								<%= filterWordLanguage != null && filterWordLanguage.contains(lang.getId()) ? "selected=\"selected\"" : "" %>><%=lang.getName()%></option>
							<%
								}
							%>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="filterTranslationLanguage">Translation</label>
					<div class="controls">
						<select name="filterTranslationLanguage" id="filterTranslationLanguage"
							multiple="multiple">
							<%
								@SuppressWarnings("unchecked")
								List<String> filterTranslationLanguage =  (List<String>)request.getAttribute("filterTranslationLanguage");
								for (Language lang : FlashcardsService.getLanguages()) {
							%>
							<option value="<%=lang.getId()%>"
								<%=filterTranslationLanguage != null && filterTranslationLanguage.contains(lang.getId()) ? "selected=\"selected\"" : ""%>><%=lang.getName()%></option>
							<%
								}
							%>
						</select>
					</div>
				</div>
				<button type="reset" class="btn">Reset</button>
				<button type="submit" class="btn btn-primary">Search</button>
			</form>

		</div>
	</div>
</div>
<% } %>