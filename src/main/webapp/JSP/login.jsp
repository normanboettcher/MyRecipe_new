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
	<header> <!-- Sticky Navbar with Logo --> <nav
	class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
<div class="container-fluid">
	<a class="navbar-brand" href="index.jsp"><img src="../IMG/logo.png"
		alt="" width="50" height="45"> MyRecipe</a>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
		data-bs-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">
			<li><a class="nav-link" href="#"><i class="fas fa-utensils"></i>
					Rezepte</a></li>


			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i
					class="fas fa-store-alt"></i> Händler
			</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><a class="dropdown-item" href="#">Lidl</a></li>
					<li><hr class="dropdown-divider"></li>
					<li><a class="dropdown-item" href="#">Penny</a></li>
					<li><hr class="dropdown-divider"></li>
					<li><a class="dropdown-item" href="#">Rewe</a></li>
				</ul></li>
			<li><a class="nav-link" href="test.jsp"><i
					class="fas fa-shopping-basket"></i> Einkaufskorb</a></li>

			<li><a class="nav-link" href="login.jsp"><i
					class="fa fa-user"></i> Mein Konto</a></li>
		</ul>
	</div>
</div>
</nav> </header>

	<!-- Login Bereich-->
	<form class="box" action="../LoginServlet" method="post">
		<h1>Login</h1>
		<input type="text" name="username" placeholder="Username"> 
		<input
			type="password" name="pw" placeholder="Password"> 
			
		<input
			type="submit" name="" value="Login"> <a href="kontakt.jsp">Lost
			your password?</a><br> 
			<a href="register.jsp">Don't have an
			account?</a>
	</form>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
		crossorigin="anonymous"></script>

	
</body>
</html>