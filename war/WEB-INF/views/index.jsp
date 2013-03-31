<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<%@include file="head.jsp"%>
<body>
	<%@include file="header.jsp"%>


	<div class="container">
		<div class="row">
			<div class="span4">
				<!--Sidebar content-->
				<div class="accordion" id="accordion1">
					<%@include file="filter.jsp"%>
					<%@include file="add.jsp"%>
					<!-- %@include file="batch.jsp"% -->
				</div>
			</div>
			<div class="span8">
				<!--Body content-->
				<div class="accordion" id="accordion2">
					<%@include file="list.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>