<%@page import="openflashcards.service.FlashcardsService"%>
<%@page import="openflashcards.entity.User"%>
<%@page import="openflashcards.entity.Tag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% {
	User user = FlashcardsService.getCurrentUser();
	Tag tag = (Tag)request.getAttribute("tag");
%>
<div class="tag" data-url="/users/<%= user.getId() %>/tags/<%= tag.getId() %>"> <%= tag.getName() %> <div class="btn-tag-remove"></div></div>
<% } %>
