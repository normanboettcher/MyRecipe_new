<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="/WebContent/IMG/logo.png">
<script src="https://kit.fontawesome.com/e7e1f4a24e.js"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<link rel="stylesheet" href="../CSS/KontaktStyle.css">
<title>MyRecipe</title>
</head>
<header>
<!-- Sticky Navbar with Logo --> <nav
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
</nav> 
</header>
<body>

<div class="kontakt">
		<form action="KontaktForm" method="post">
			<table>
				<tr>
					<td><h3 class="abschnitt">Vorname:</h3></td>
					<td><input type="text" name="vorname" class="eingabe"
						placeholder="Vorname" maxlength="50" required
						pattern="[A-Za-zÖöÜüÄä]{2,50}"></td>
				</tr>
				<tr>
					<td><h3 class="abschnitt">Nachname:</h3></td>
					<td><input type="text" name="nachname" class="eingabe"
						placeholder="Nachname" maxlength="50" required
						pattern="[A-Za-zÖöÜüÄä]{2,50}"></td>
				</tr>
				<tr>
					<td><h3 class="abschnitt">E-Mail:</h3></td>
					<td><input type="text" class="eingabe" name="eingabe"
						placeholder="E-Mail Adresse" maxlength="70" required
						pattern="^[A-Za-z0-9\.\+_-]+@[A-Za-z0-9\._-]+\.[a-uA-Z]+$"></td>
				</tr>
				<tr>
					<td><h3 class="abschnitt">Kommentar:</h3></td>
					<td><textarea class="beschreibung" placeholder="Beschreibung"
							rows="5" cols="40" maxlength="500" required></textarea></td>
				</tr>
			</table>
			<button class="button" type="submit">Abschicken</button>
		</form>
		
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
		crossorigin="anonymous"></script>


</body>
</html>