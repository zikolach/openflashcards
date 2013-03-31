<%@page import="openflashcards.entity.UserFlashcard"%>
<%@page import="openflashcards.entity.UserLanguage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<div class="span12">
				<div class="accordion" id="accordionUser">
					<div class="accordion-heading">
						<button class="btn btn-block" type="button" data-toggle="collapse"
							data-parent="#accordionUser" data-target="#collapseUser">User Profile</button>
					</div>
					<div id="collapseUser" class="accordion-body collapse in">
						<div class="accordion-inner">
							<%=user.getName() %>
							<hr/>
							<%=user.getEmail() %>
							<hr/>
							
							<nl>
							<%
							for (UserLanguage ul : user.getLanguages()) {
							%>
							<li>
								<b><%= ul.getLanguage().getName() %></b>
								<%
								for (UserFlashcard uf : ul.getFlashcards()) {
								%>
									<span>
										<%=uf.getFlashcard().getId() %>
										<span class="btn btn-mini like">
											<a class="icon-star-empty" href="/users/<%= user.getId() %>/languages/<%= ul.getLanguage().getId() %>/flashcards/<%= uf.getFlashcard().getId() %>">
											</a>
										</span>
									</span>
								<%
								}
								%>
							</li>
							<%
							}
							%>
							</nl>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<%
}
%>
</body>
</html>

