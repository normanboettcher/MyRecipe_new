<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="src/main/webapp/IMG/logo.png">
<script src="https://kit.fontawesome.com/e7e1f4a24e.js"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/LoginStyle.css">
<title>Login</title>
</head>
<body>
	<header> <!-- Navbar with Logo --> <nav
class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
<div class="container-fluid">
	<a class="navbar-brand" href="login.jsp"><img
		src="<%=request.getContextPath()%>/IMG/logo.png" alt="" width="50"
		height="45"> MyRecipe</a>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
		data-bs-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
</div>
</nav></header>

	<!-- Login Bereich-->
	<form class="box" action="../LoginServlet" method="post">
	<h1><img
		src="<%=request.getContextPath()%>/IMG/logo.png" alt="" width="125"
		height="120"></h1>
		<h2 style="color:white;">MyRecipe</h2>
		<input type="text" name="username" placeholder="Username"> 
		<input
			type="password" name="pw" placeholder="Password"> 
			
		<input
			type="submit" name="" value="Login"> <a style="color:white" href="kontakt.jsp">Lost
			your password?</a><br> 
			<a style="color:white" href="register.jsp">Don't have an
			account?</a>
	</form>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
		crossorigin="anonymous"></script>

	
</body>
</html>