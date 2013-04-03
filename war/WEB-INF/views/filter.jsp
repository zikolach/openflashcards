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

			<form class="form-vertical" id="search-form" action="/flashcards" method="get">
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
					<div id="filterWordLanguage" class="controls">
					<% for (Language lang : FlashcardsService.getLanguages()) { %>
						<label class="checkbox"><input type="checkbox" name="filterWordLanguage" value="<%= lang.getId() %>"> <%= lang.getName() %></label>
					<% } %>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="filterTranslationLanguage">Translation</label>
					<div id="filterTranslationLanguage" class="controls">
					<% for (Language lang : FlashcardsService.getLanguages()) { %>
						<label class="checkbox"><input type="checkbox" name="filterTranslationLanguage" value="<%= lang.getId() %>"> <%= lang.getName() %></label>
					<% } %>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="filterLimit">Limit</label>
					<div class="controls">
						<select name="filterLimit" id="filterLimit">
							<option selected="selected">10</option>
							<option>50</option>
							<option>100</option>
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