<%@page import="openflashcards.service.FlashcardsService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="openflashcards.entity.User"%>
<%
{
%>
<div class="navbar navbar-static-top">
	<div class="navbar-inner">

		<a class="brand" href="/">openflashcards</a>

		<%
			User user = FlashcardsService.getCurrentUser();
			if (user != null) {
		%>
		<ul class="nav pull-right">

			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"><%=user.getName()%> <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="/users/<%=user.getId()%>">Edit profile</a></li>
					<li class="divider"></li>
					<li><a
						href="<%=UserServiceFactory.getUserService().createLogoutURL(
						"/users/logout")%>">Sign
							out</a></li>
				</ul></li>

		</ul>
		<%
			} else {
		%>
		<ul class="nav pull-right">

			<li><a
				href="<%=UserServiceFactory.getUserService().createLoginURL(
						"/")%>">Sign
					in</a></li>

		</ul>
		<%
			}
		%>


	</div>
</div>
<%
}
%>
