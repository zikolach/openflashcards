<%@page import="openflashcards.service.FlashcardsService"%>
<%@page import="openflashcards.entity.Language"%>
<%@page import="openflashcards.entity.User"%>


<div class="accordion-group">
	<div class="accordion-heading">
		<button class="btn btn-block" type="button" data-toggle="collapse"
			data-parent="#accordion1" data-target="#collapseOne">Add new word</button>
	</div>
	<div id="collapseOne" class="accordion-body collapse">
		<div class="accordion-inner">

			<form action="/flashcards" method="post" class="form-vertical">
				<fieldset>
					<div>
						<%
							//User user = User.getCurrentUser();
						%>
						<div class="control-group">
							<label class="control-label" for="inputWordLanguage">Language</label>
							<div class="controls">
								<select name="wordLanguage" id="inputWordLanguage">
									<%
										for (Language lang : FlashcardsService.getLanguages()) {
									%>
									<option value="<%=lang.getId()%>"
										<%=FlashcardsService.getCurrentUser() != null
						&& lang.equals(FlashcardsService.getCurrentUser().getLastWordLanguage()) ? "selected='selected'"
						: ""%>><%=lang.getName()%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="inputWord">New word</label>
							<div class="controls">
								<input type="text" name="word" id="inputWord" placeholder="Word"
									class="input-xlarge" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="inputTranslationLanguage">Language</label>
							<div class="controls">
								<select name="translationLanguage" id="inputTranslationLanguage">
									<%
										for (Language lang : FlashcardsService.getLanguages()) {
									%>
									<option value="<%=lang.getId()%>"
										<%=FlashcardsService.getCurrentUser() != null
						&& lang.equals(FlashcardsService.getCurrentUser().getLastTranslationLanguage()) ? "selected='selected'"
						: ""%>><%=lang.getName()%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="inputTranslation">Translation</label>
							<div class="controls">
								<textarea rows="3" name="translation" id="inputTranslation"
									placeholder="Translation" class="input-xlarge"></textarea>
							</div>
						</div>

						<div class="control-group">
							<div class="controls">
								<button class="btn" data-dismiss="modal">Cancel</button>
								<button class="btn btn-primary" type="submit">Add</button>
							</div>
						</div>

					</div>

				</fieldset>
			</form>

		</div>
	</div>
</div>



